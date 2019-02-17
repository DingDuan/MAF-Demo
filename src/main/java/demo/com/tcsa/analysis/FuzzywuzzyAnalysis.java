package demo.com.tcsa.analysis;

import com.csvreader.CsvReader;
import demo.com.tcsa.model.CIDFileCompareModel;
import demo.com.tcsa.model.SimValueModel;
import demo.com.tcsa.util.FileUtil;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
  * @Author sunweisong
  * @Date 2018/8/10 下午6:54
  */
public class FuzzywuzzyAnalysis {

    public static final String rootPath = "/Users/sunweisong/Desktop/our_tool_tcsa/experiment/comparison_by_file";

    public static void analyze() {

        File rooDirectory = new File(rootPath);
        if (rooDirectory.exists()) {
            long startTime = System.currentTimeMillis();
//            compareAllContestants();
            statisticsMaxSimValue();
            long endTime = System.currentTimeMillis();
            System.out.println("Begin time : " + startTime);
            System.out.println("End time : " + endTime);
            System.out.println("Cost Time : " + (endTime - startTime) + "ms");
        }

    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/11 上午12:26
      * @author sunweisong
      */
    private static void statisticsMaxSimValue() {
        String detectResultDirectoryPath = rootPath + File.separator + "detect_result";
        File detectResultDirectory = new File(detectResultDirectoryPath);
        if (!detectResultDirectory.exists()) {
            return;
        }
        String statisticResultFilePath = rootPath + File.separator + "statistic_result.csv";
        File statisticResultFile = new File(statisticResultFilePath);
        if (statisticResultFile.exists()) {
            statisticResultFile.delete();
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue"};
        FileUtil.writeMatrixToCSVFile(statisticResultFile, headArray, null, true);
        File[] cid1DirectoryArray = detectResultDirectory.listFiles();
        List<SimValueModel> simValueModelList = new ArrayList<>();
        for (File cid1Directory : cid1DirectoryArray) {
            String cid1 = cid1Directory.getName();
            if (cid1.charAt(0) == '.') {
                continue;
            }
            File[] cid2DirectoryArray = cid1Directory.listFiles();
            for (File cid2Directory : cid2DirectoryArray) {
                String cid2 = cid2Directory.getName();
                if (cid2.charAt(0) == '.') {
                    continue;
                }
                File[] files = cid2Directory.listFiles();
                if (files.length == 0) {
                    continue;
                }
                for (File file : files) {
                    String fileName = file.getName();
                    if (!"compare_result.csv".equals(fileName)) {
                        continue;
                    }
                    int maxSimValue = extractMaxSimValueRecordFromCSVFile(file);
                    simValueModelList.add(new SimValueModel(Integer.parseInt(cid1)
                            , Integer.parseInt(cid2), maxSimValue));
                    break;
                }
            }
        }
        if (simValueModelList.size() > 0) {
            List<String[]> records = new ArrayList<>(simValueModelList.size());
            for (SimValueModel model : simValueModelList) {
                records.add(new String[]{model.getCid1()
                        + "", model.getCid2() + "", model.getSimValue() + ""});
            }
            FileUtil.writeMatrixToCSVFile(statisticResultFile, null
                    , records, true);
            records = null;
        }
        statisticResultFile = null;
        simValueModelList = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/11 上午12:14
      * @author sunweisong
      */
    private static int extractMaxSimValueRecordFromCSVFile(File file) {
        String sourceFilePath = file.getAbsolutePath();
        int maxSimValue = 0;
        if (file.exists()) {
            CsvReader reader = null;
            try {
                reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
                int index = 0;
                while(reader.readRecord()) {
                    index++;
                    if (index == 1) {
                        continue;
                    }
                    String[] rowContents = reader.getValues();
                    int length = rowContents.length;
                    String isMax = rowContents[length - 1];
                    if (!"1".equals(isMax)) {
                        continue;
                    }
                    maxSimValue = Integer.parseInt(rowContents[length - 2]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        return maxSimValue;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 下午11:56
      * @author sunweisong
      */
    private static void compareAllContestants() {
        String submissionDirectoryPath = rootPath + File.separator + "submission";
        File submissionDirectory = new File(submissionDirectoryPath);
        if (!submissionDirectory.exists()) {
            System.err.println("不存在submission目录！");
        }
        traverseSubmissionDirectory(submissionDirectory);
        submissionDirectory = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 下午7:57
      * @author sunweisong
      */
    private static void traverseSubmissionDirectory(File submissionDirectory) {
        String detectResultDirectoryPath = rootPath + File.separator + "detect_result";
        StringBuffer buffer = new StringBuffer(detectResultDirectoryPath);
        File[] cidDirectoryArray = submissionDirectory.listFiles();
        int cidDirectoryArrayLength = cidDirectoryArray.length;
        for (int i = 0; i < cidDirectoryArrayLength; i++) {
            File cid1Directory =  cidDirectoryArray[i];
            String cid1 = cid1Directory.getName();
            if (cid1.charAt(0) == '.') {
                continue;
            }
            buffer.append(File.separator + cid1);
            for (int j = i + 1; j < cidDirectoryArrayLength; j++) {
                File cid2Directory = cidDirectoryArray[j];
                String cid2 = cid2Directory.getName();
                if (cid2.charAt(0) == '.') {
                    continue;
                }
                buffer.append(File.separator + cid2 + File.separator);
                List<CIDFileCompareModel> compareModelList = compareTwoContestants(cid1Directory
                        , cid2Directory);
                if (compareModelList == null) {
                    continue;
                }
                System.out.println("<" + cid1 + ", " + cid2 + "> : " + compareModelList.size());
                saveCompareModelListToFile(compareModelList, buffer.toString());
                int start = buffer.indexOf(File.separator + cid2 + File.separator);
                buffer.replace(start, buffer.length(), "");
            }
            int start = buffer.indexOf(File.separator + cid1);
            buffer.replace(start, buffer.length(), "");
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 下午7:56
      * @author sunweisong
      */
    private static void saveCompareModelListToFile(List<CIDFileCompareModel> compareModelList
            , String targetDirectoryPath) {
        targetDirectoryPath = targetDirectoryPath.substring(0, targetDirectoryPath.length() -1);
        StringBuffer buffer = new StringBuffer(targetDirectoryPath);
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        buffer.append(File.separator + "compare_result.csv");
        File targetFile = new File(buffer.toString());
        if (targetFile.exists()) {
            targetFile.delete();
        }
        String[] headArray = {"CID1", "testFileName", "CID2", "testFileName", "SimValue", "IsMax"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
        int maxSimValue = 0;
        for (CIDFileCompareModel model : compareModelList) {
            if (model.getSimValue() > maxSimValue) {
                maxSimValue = model.getSimValue();
            }
        }
        for (CIDFileCompareModel model : compareModelList) {
            if (model.getSimValue() == maxSimValue) {
                model.setIsMax(1);
                break;
            }
        }
        List<String[]> records = new ArrayList<>(compareModelList.size());
        for (CIDFileCompareModel model : compareModelList) {
            records.add(new String[]{model.getCid1() + "", model.getCid1FileName()
                    , model.getCid2() + "", model.getCid2FileName()
                    , model.getSimValue() + "", model.getIsMax() + ""});
        }
        FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
        records = null;
        targetFile = null;
        buffer = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/10 下午7:33
      * @author sunweisong
      */
    private static List<CIDFileCompareModel> compareTwoContestants(File cid1Directory
            , File cid2Directory) {
        File[] testFile1Array = cid1Directory.listFiles();
        File[] testFile2Array = cid2Directory.listFiles();
        if (testFile1Array.length == 0 || testFile2Array.length == 0) {
            return null;
        }
        List<CIDFileCompareModel> compareModelList = new ArrayList<>();
        for (File testFile1 : testFile1Array) {
            String testFile1Name = testFile1.getName();
            if (testFile1Name.charAt(0) == '.') {
                continue;
            }
            String testFile1Contents = FileUtil.readResultContentsToString(testFile1);
            for (File testFile2 : testFile2Array) {
                String testFile2Name = testFile2.getName();
                if (testFile2Name.charAt(0) == '.') {
                    continue;
                }
                String testFile2Contents = FileUtil.readFileContentToString(testFile2);
                int simValue = SimAnalysis.fuzzyRatio(testFile1Contents, testFile2Contents);
                compareModelList.add(new CIDFileCompareModel(Integer.parseInt(cid1Directory.getName())
                        , Integer.parseInt(cid2Directory.getName())
                        , testFile1Name, testFile2Name, simValue));
            }
        }
        if (compareModelList.size() == 0) {
            compareModelList = null;
            return null;
        }
        return compareModelList;
    }
}
