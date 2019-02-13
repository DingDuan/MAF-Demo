package com.tcsa.service.plaggie;

import com.csvreader.CsvReader;
import com.tcsa.analysis.DifflibAnalysis;
import com.tcsa.dao.SimValueModelDao;
import com.tcsa.model.DiffModel;
import com.tcsa.model.SimValueModel;
import com.tcsa.util.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static plag.parser.plaggie.Plaggie.*;

/**
  * @Author sunweisong
  * @Date 2018/8/6 上午9:45
  */
public class Plaggie {

    private final static String dataRootPath = "/Users/sunweisong/Desktop/comparison_tool_plaggie/test_data";
    private final static String detectResultFileName = "result.txt";
    private final static String statisticResultFileName = "statistic_result.csv";
    private final static String exceptionParseResultFileName = "exception_parse_result.txt";

    private final static String TF_dataRootPath = "/Users/sunweisong/Desktop/comparison_tool_plaggie/experiment/comparison_by_tf";
    private final static String TF_detectResultFileName = "result.txt";
    private final static String TF_statisticResultFileName = "statistic_result_by_mid.csv";
    private final static String TF_noSimTFResultFileName = "no_sim_tf_result.txt";

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/8/6 上午11:29
     * @author sunweisong
     */
    public static void analyze() {


        compareAllContestants();
        parseDetectResultFile();

        compareAllContestantsByTF();
//        statisticAllSimilarityInResultFile();
//        parseDetectResultFileByTF();

        // mapping cid to new cid
        String plaggieRootPath = "/Users/sunweisong/Desktop/comparison_tool_plaggie";
        String statisticResultRootPath = plaggieRootPath + File.separator + "experiment/comparison_by_file";
        String cidMapFilePath = plaggieRootPath + File.separator + "all_cid_mapping.xlsx";
        File cidMapFile = new File(cidMapFilePath);
        Map<String, String> cidMap = extractCIDMapFromFile(cidMapFile);
        System.out.println(cidMap.size());
        mappingCID(statisticResultRootPath, cidMap);

    }

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/8/11 上午10:11
     * @author sunweisong
     */
    private static void mappingCID(String statisticResultRootPath
            , Map<String, String> cidMap) {
        File statisticResultRoot = new File(statisticResultRootPath);
        if (!statisticResultRoot.exists()) {
            return;
        }
        String filePath = statisticResultRootPath + File.separator + "all_statistic_result_old_cid.csv";
        String targetFilePath = statisticResultRootPath + File.separator + "all_statistic_result_new_cid.csv";
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        CsvReader reader = null;
        try {
            reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
            int index = 0;
            List<String[]> records = new ArrayList<>();
            while(reader.readRecord()) {
                index++;
                if (index == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                int length = rowContents.length;
                String CID1 = rowContents[0];
                String CID2 = rowContents[1];
                String newCID1 = cidMap.get(CID1);
                String newCID2 = cidMap.get(CID2);
                records.add(new String[] {newCID1, newCID2, rowContents[2]});
                if (records.size() % 1000 == 0) {
                    FileUtil.writeMatrixToCSVFile(targetFile, null
                            , records, true);
                    records.clear();
                }
            }
            if (records.size() > 0) {
                FileUtil.writeMatrixToCSVFile(targetFile, null
                        , records, true);
                records.clear();
                records = null;
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

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/8/11 上午10:12
     * @author sunweisong
     */
    private static Map<String,String> extractCIDMapFromFile(File cidMapFile) {
        if (!cidMapFile.exists()) {
            return null;
        }
        Map<String, String> cidMap = readExcelToObject(cidMapFile);
        return cidMap;
    }

    /**
     * Read excel sheet and write a line recode to a object.
     * @param file
     * @return
     */
    private static Map<String, String> readExcelToObject(File file) {
        Workbook wb = null;
        Map<String, String> cidMap = null;
        try {
            wb = WorkbookFactory.create(file);
            cidMap = readExcel(wb, 0
                    , 1, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cidMap;
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/7/19 下午2:12
     * @author sunweisong
     */
    private static Map<String, String> readExcel(Workbook wb
            , int sheetIndex, int startReadLine, int tailLine) throws Exception {
        Map<String, String> cidMap = new HashMap<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            int index = 0;
            String key = "";
            String value = "";
            for (Cell cell : row) {
                if (index == 0) {
                    if (cell.getCellType() == CELL_TYPE_STRING) {
                        key = cell.getStringCellValue();
                    } else if(cell.getCellType() == CELL_TYPE_NUMERIC) {
                        key = String.valueOf((int)cell.getNumericCellValue());
                    } else {
                        throw new Exception("表格内容异常！");
                    }

                }
                if (index == 1) {
                    if (cell.getCellType() == CELL_TYPE_STRING) {
                        value = cell.getStringCellValue();
                    } else if(cell.getCellType() == CELL_TYPE_NUMERIC) {
                        value = String.valueOf((int)cell.getNumericCellValue());
                    } else {
                        throw new Exception("表格内容异常！");
                    }
                    break;
                }
                index++;
            }
            if ("".equals(key)) {
                continue;
            }
            System.out.println("<" + key + ", " + value + ">");
            cidMap.put(key, value);
        }
        return cidMap;
    }


    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/11 上午10:13
      * @author sunweisong
      */
    private static void statisticAllSimilarityInResultFile() {
        String detectResultRootPath = TF_dataRootPath + File.separator + "detection_result";
        File detectResultRoot = new File(detectResultRootPath);
        if (!detectResultRoot.exists()) {
            System.out.println("目录下无检测结果！");
        }

        String[] headArray = {"CID1", "CID2", "MID", "MaxSimValue", "IsMax"};

        String noSimTFResultFilePath = TF_dataRootPath + File.separator + TF_noSimTFResultFileName;
        File noSimTFResultFile = new File(noSimTFResultFilePath);
        if (noSimTFResultFile.exists()) {
            noSimTFResultFile.delete();
        }
        FileUtil.writeRecordsToTargetFile(noSimTFResultFile, new String[]{"CID1", "CID2"}
                , null, true);

        String exceptionParseResultFilePath = TF_dataRootPath + File.separator + exceptionParseResultFileName;
        File exceptionParseResultFile = new File(exceptionParseResultFilePath);
        if (exceptionParseResultFile.exists()) {
            exceptionParseResultFile.delete();
        }
        FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, new String[]{"CID1", "CID2"}
                , null, true);
        File[] cid1DirectoryArray = detectResultRoot.listFiles();
        for (File cid1Directory : cid1DirectoryArray) {
            String cid1 = cid1Directory.getName();
            if (cid1.charAt(0) == '.') {
                continue;
            }
//            if (!"58".equals(cid1)) {
//                continue;
//            }
            File[] cid2DirectoryArray = cid1Directory.listFiles();
            for (File cid2Directory : cid2DirectoryArray) {
                String cid2 = cid2Directory.getName();
                if (cid2.charAt(0) == '.') {
                    continue;
                }
//                if (!"2942".equals(cid2)) {
//                    continue;
//                }
                File[] resultFileArray = cid2Directory.listFiles();
                if (resultFileArray == null || resultFileArray.length == 0) {
                    String record = cid1 + "\t" + cid2;
                    FileUtil.writeRecordsToTargetFile(noSimTFResultFile, headArray
                            , record, true);

                    continue;
                }
                List<DiffModel> diffModelList = new ArrayList<>();
                for (File resultFile : resultFileArray) {
                    String resultFileName = resultFile.getName();
                    if ("statistic_result_by_mid.csv".equals(resultFileName)) {
                        continue;
                    }
                    if (resultFileName.contains("_result")) {
                        int suffixIndex = resultFileName.indexOf("_result");
                        String mid = resultFileName.substring(0, suffixIndex);
                        System.out.println(mid);
                        double maxSimValue = extractMaxSimValueFromResultFile(resultFile);
                        if (maxSimValue == -1) {
                            // can not parse result.txt
                            String record = cid1 + "\t" + cid2;
                            FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, null
                                    , record, true);
                            continue;
                        }
                        diffModelList.add(new DiffModel(Integer.parseInt(cid1), Integer.parseInt(cid2), mid
                                , null, maxSimValue));
                    }
                }
                if (diffModelList.size() == 0) {
                    diffModelList = null;
                    String record = cid1 + "\t" + cid2;
                    FileUtil.writeRecordsToTargetFile(noSimTFResultFile, headArray
                            , record, true);
                    continue;
                }
                File statisticResultFile = new File(cid2Directory + File.separator + TF_statisticResultFileName);
                if (statisticResultFile.exists()) {
                    statisticResultFile.delete();
                }
                FileUtil.writeMatrixToCSVFile(statisticResultFile, headArray, null
                        , true);
                if (diffModelList.size() == 1) {
                    DiffModel model = diffModelList.get(0);
                    List<String[]> rows = new ArrayList<>();
                    String[] record = {model.getCID1() + "", model.getCID2() + ""
                            , model.getCid1FileName(), model.getSimValue() + "", "1"};
                    rows.add(record);
                    FileUtil.writeMatrixToCSVFile(statisticResultFile, null, rows
                            , true);
                    statisticResultFile = null;
                    rows = null;
                    model = null;
                    diffModelList = null;
                    continue;
                }
                if (diffModelList.size() > 1) {
                    // setIsMax
                    DifflibAnalysis.setIsMaxForAllDiffModel(diffModelList);
                    List<String[]> records = new ArrayList<>(diffModelList.size());
                    for (DiffModel diffModel : diffModelList) {
                        records.add(new String[] {diffModel.getCID1() + ""
                                , diffModel.getCID2() + ""
                                , diffModel.getCid1FileName()
                                , diffModel.getSimValue() + ""
                                , diffModel.getIsMax() + ""});
                    }
                    FileUtil.writeMatrixToCSVFile(statisticResultFile, null, records
                            , true);
                    statisticResultFile = null;
                    records = null;
                    diffModelList = null;
                }
                resultFileArray = null;
            }
            cid2DirectoryArray = null;
        }
        cid1DirectoryArray = null;
        headArray = null;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/11 上午10:12
      * @author sunweisong
      */
    private static void parseDetectResultFileByTF() {
        String detectResultRootPath = dataRootPath + File.separator + "detection_result";
        String statisticResultFilePath = dataRootPath + File.separator + statisticResultFileName;
        File detectResultRoot = new File(detectResultRootPath);
        if (!detectResultRoot.exists()) {
            System.out.println("目录下无检测结果！");
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue"};
        File statisticResultFile = new File(statisticResultFilePath);
        if (statisticResultFile.exists()) {
            statisticResultFile.delete();
        }
        FileUtil.writeMatrixToCSVFile(statisticResultFile, headArray, null
                , true);
        String exceptionParseResultFilePath = dataRootPath + File.separator + exceptionParseResultFileName;
        File exceptionParseResultFile = new File(exceptionParseResultFilePath);
        if (exceptionParseResultFile.exists()) {
            exceptionParseResultFile.delete();
        }
        FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, headArray
                , null, true);
        File[] cid1DirectoryArray = detectResultRoot.listFiles();
        List<String[]> records = new ArrayList<>();
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
                File[] resultFileArray = cid2Directory.listFiles();
                for (File resultFile : resultFileArray) {
                    if ("result.txt".equals(resultFile.getName())) {
                        double maxSimValue = extractMaxSimValueFromResultFile(resultFile);
                        if (maxSimValue == -1) {
                            // can not parse result.txt
                            String record = cid1 + "\t" + cid2;
                            FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, null
                                    , record, true);
                            continue;
                        }
                        records.add(new String[]{cid1, cid2, maxSimValue + ""});
                        if (records.size() % 100 == 0) {
                            FileUtil.writeMatrixToCSVFile(statisticResultFile, null
                                    , records, true);
                            records.clear();
                        }
                    }
                }
                resultFileArray = null;
            }
            cid2DirectoryArray = null;
        }
        cid1DirectoryArray = null;
        if (records.size() > 0) {
            FileUtil.writeMatrixToCSVFile(statisticResultFile, null
                    , records, true);
        }
        records = null;
        statisticResultFile = null;
        headArray = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/7 下午1:25
      * @author sunweisong
      */
    private static void compareAllContestantsByTF() {
        String sourceDataDirectory = TF_dataRootPath + File.separator + "submission";
        StringBuffer resultDirectoryBuffer = new StringBuffer(TF_dataRootPath + File.separator + "detection_result");
        File dataRoot = new File(sourceDataDirectory);
        if (!dataRoot.exists()) {
            System.err.println("数据根目录不存在！");
            return;
        }
        File[] cidDirectoryArray = dataRoot.listFiles();
        int cidNumber = cidDirectoryArray.length;
        for (int i = 0; i < cidNumber; i++) {
            String cid1 = cidDirectoryArray[i].getName();
            if (cid1.charAt(0) == '.') {
                continue;
            }
            resultDirectoryBuffer.append(File.separator + cid1);
            File cid1Directory = cidDirectoryArray[i];
            for (int j = i + 1; j < cidNumber; j++) {
                String cid2 = cidDirectoryArray[j].getName();
                if (cid2.charAt(0) == '.') {
                    continue;
                }
                resultDirectoryBuffer.append(File.separator + cid2 + File.separator);
//                File resultDirectory = new File(resultDirectoryBuffer.toString());
//                if (!resultDirectory.exists()) {
//                    resultDirectory.mkdirs();
//                }
//                resultDirectory = null;
                File cid2Directory = cidDirectoryArray[j];
                compareTwoContestantsByTF(cid1Directory, cid2Directory, resultDirectoryBuffer.toString());
                int start = resultDirectoryBuffer.indexOf(File.separator + cid2 + File.separator);
                resultDirectoryBuffer.replace(start, resultDirectoryBuffer.length(), "");
            }
            int start = resultDirectoryBuffer.indexOf(File.separator + cid1);
            resultDirectoryBuffer.replace(start, resultDirectoryBuffer.length(), "");
        }
        resultDirectoryBuffer = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/7 下午1:25
      * @author sunweisong
      */
    private static void compareTwoContestantsByTF(File cid1Directory, File cid2Directory
            , String detectResultDirectoryPath) {
        detectResultDirectoryPath = detectResultDirectoryPath.substring(0, detectResultDirectoryPath.length() -1);
        StringBuffer buffer = new StringBuffer(detectResultDirectoryPath);
        File[] cid1TestFileArray = cid1Directory.listFiles();
        File[] cid2TestFileArray = cid2Directory.listFiles();
        File targetFile = null;
        for (File cid1TestFile : cid1TestFileArray) {
            String cid1TestFileName = cid1TestFile.getName();
            if (cid1TestFileName.charAt(0) == '.') {
                continue;
            }
            int index = cid1TestFileName.lastIndexOf(".");
            String mid = cid1TestFileName.substring(0, index);
            String fileName = File.separator + mid + "_" + TF_detectResultFileName;
            buffer.append(fileName);
            // find sim mid.txt file in cid2 directory
            for (File cid2TestFile : cid2TestFileArray) {
                String cid2TestFileName = cid2TestFile.getName();
                if (cid2TestFileName.charAt(0) == '.') {
                    continue;
                }
                if (!cid2TestFileName.equals(cid1TestFileName)) {
                    continue;
                }
                File resultDirectory = new File(detectResultDirectoryPath);
                if (!resultDirectory.exists()) {
                    resultDirectory.mkdirs();
                }
                resultDirectory = null;
                String[] args = new String[]{cid1TestFile.getAbsolutePath()
                        , cid2TestFile.getAbsolutePath()};
                Queue<String> resultQueue = compare(args);
                targetFile = new File(buffer.toString());
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                FileUtil.writeDetectResultByPlaggieToTargetFile(resultQueue, targetFile);
                break;
            }
            int fileNameIndex = buffer.indexOf(fileName);
            buffer.replace(fileNameIndex, buffer.length(), "");
        }
        buffer = null;
        targetFile = null;
    }


    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午5:14
      * @author sunweisong
      */
    private static void parseDetectResultFile() {
        String detectResultRootPath = dataRootPath + File.separator + "detection_result";
        String statisticResultFilePath = dataRootPath + File.separator + statisticResultFileName;
        File detectResultRoot = new File(detectResultRootPath);
        if (!detectResultRoot.exists()) {
            System.out.println("目录下无检测结果！");
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue"};
        File statisticResultFile = new File(statisticResultFilePath);
        if (statisticResultFile.exists()) {
            statisticResultFile.delete();
        }
        FileUtil.writeMatrixToCSVFile(statisticResultFile, headArray, null
                , true);
        String exceptionParseResultFilePath = dataRootPath + File.separator + exceptionParseResultFileName;
        File exceptionParseResultFile = new File(exceptionParseResultFilePath);
        if (exceptionParseResultFile.exists()) {
            exceptionParseResultFile.delete();
        }
        FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, headArray
                , null, true);
        File[] cid1DirectoryArray = detectResultRoot.listFiles();
        List<String[]> records = new ArrayList<>();
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
                File[] resultFileArray = cid2Directory.listFiles();
                for (File resultFile : resultFileArray) {
                    if ("result.txt".equals(resultFile.getName())) {
                        double maxSimValue = extractMaxSimValueFromResultFile(resultFile);
                        if (maxSimValue == -1) {
                            // can not parse result.txt
                            String record = cid1 + "\t" + cid2;
                            FileUtil.writeRecordsToTargetFile(exceptionParseResultFile, null
                                    , record, true);
                            continue;
                        }
                        records.add(new String[]{cid1, cid2, maxSimValue + ""});
                        if (records.size() % 100 == 0) {
                            FileUtil.writeMatrixToCSVFile(statisticResultFile, null
                                    , records, true);
                            records.clear();
                        }
                    }
                }
                resultFileArray = null;
            }
            cid2DirectoryArray = null;
        }
        cid1DirectoryArray = null;
        if (records.size() > 0) {
            FileUtil.writeMatrixToCSVFile(statisticResultFile, null
                    , records, true);
        }
        records = null;
        statisticResultFile = null;
        headArray = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午5:13
      * @author sunweisong
      */
    private static double extractMaxSimValueFromResultFile(File resultFile) {
        String fileContents = FileUtil.readResultContentsToString(resultFile);
        StringBuffer buffer = new StringBuffer(fileContents);
        int index = buffer.indexOf("File detection results");
        buffer.replace(0, index, "");
        index = buffer.lastIndexOf("matches:");
        if (index == -1) {
            return -1;
        }
        buffer.replace(index, buffer.length(), "");
        String[] compareRecordArray = buffer.toString().split("[\\-]{2,}");
        buffer = null;
        double maxSimValue = 0;
        for (int i = 1; i < compareRecordArray.length; i++) {
            String comparedRecord = compareRecordArray[i].trim();
            System.out.println(comparedRecord);
            double simValue = getSimValueFormComparedRecord(comparedRecord);
            if (simValue > maxSimValue) {
                maxSimValue = simValue;
            }
        }
        return maxSimValue;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午5:52
      * @author sunweisong
      */
    private static double getSimValueFormComparedRecord(String comparedRecord) {
        StringBuffer buffer = new StringBuffer(comparedRecord);
        String seg = File.separator + "Users" + File.separator;
        int index = buffer.lastIndexOf(seg);
        String beforeIndex = buffer.substring(0, index).trim();
        String afterIndex = buffer.substring(index).trim();
        buffer = null;

        index = beforeIndex.lastIndexOf(":");
        String simValueString = beforeIndex.substring(index + 1).trim();
        System.out.println(beforeIndex);
        double maxSimValue = Double.parseDouble(simValueString);

        index = afterIndex.indexOf("matches:");
        if (index > 0) {
            afterIndex = afterIndex.substring(0, index);
        }
        index = afterIndex.lastIndexOf(":");
        simValueString = afterIndex.substring(index + 1).trim();
        double simValue = Double.parseDouble(simValueString);
        if (simValue > maxSimValue) {
            maxSimValue = simValue;
        }
        return maxSimValue;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午4:54
      * @author sunweisong
      */
    private static void compareAllContestants() {
        String sourceDataDirectory = dataRootPath + File.separator + "Datalog";
        StringBuffer resultDirectoryBuffer = new StringBuffer(dataRootPath + File.separator + "detection_result");
        File dataRoot = new File(sourceDataDirectory);
        if (!dataRoot.exists()) {
            System.err.println("数据根目录不存在！");
            return;
        }
        File[] cidDirectoryArray = dataRoot.listFiles();
        int cidNumber = cidDirectoryArray.length;
        for (int i = 0; i < cidNumber; i++) {
            String cid1 = cidDirectoryArray[i].getName();
            if (cid1.charAt(0) == '.') {
                continue;
            }
            resultDirectoryBuffer.append(File.separator + cid1);
            File cid1Directory = cidDirectoryArray[i];
            for (int j = i + 1; j < cidNumber; j++) {
                String cid2 = cidDirectoryArray[j].getName();
                if (cid2.charAt(0) == '.') {
                    continue;
                }
                resultDirectoryBuffer.append(File.separator + cid2);
                File resultDirectory = new File(resultDirectoryBuffer.toString());
                if (!resultDirectory.exists()) {
                    resultDirectory.mkdirs();
                }
                resultDirectory = null;
                resultDirectoryBuffer.append(File.separator + detectResultFileName);
                File cid2Directory = cidDirectoryArray[j];
                compareTwoContestants(cid1Directory, cid2Directory, resultDirectoryBuffer.toString());
                int start = resultDirectoryBuffer.indexOf(File.separator + cid2);
                resultDirectoryBuffer.replace(start, resultDirectoryBuffer.length(), "");
            }
            int start = resultDirectoryBuffer.indexOf(File.separator + cid1);
            resultDirectoryBuffer.replace(start, resultDirectoryBuffer.length(), "");
        }
        resultDirectoryBuffer = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 上午11:01
      * @author sunweisong
      */
    private static void compareTwoContestants(File cid1Directory, File cid2Directory
            , String detectResultFilePath) {
        File resultFile = new File(detectResultFilePath);
        if (resultFile.exists()) {
            resultFile.delete();
        }
        String[] args = new String[]{cid1Directory.getAbsolutePath(), cid2Directory.getAbsolutePath()};
        Queue<String> resultQueue = compare(args);
        File targetFile = new File(detectResultFilePath);
        FileUtil.writeDetectResultByPlaggieToTargetFile(resultQueue, targetFile);
        targetFile = null;
        resultQueue = null;
        args = null;
        resultFile = null;
    }
}
