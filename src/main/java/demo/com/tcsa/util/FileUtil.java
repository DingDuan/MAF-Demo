package demo.com.tcsa.util;

import com.csvreader.CsvWriter;
import demo.com.tcsa.model.*;
import demo.entity.MUTModel;
import demo.entity.TFModel;

import java.io.*;
import java.util.*;

public class FileUtil {

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/30 下午11:01
      * @author sunweisong
      */
    public static void copyFileToTargetFilePath(String sourceFilePath, String targetFilePath) {
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        if (!sourceFile.canRead()) {
            System.err.println("源文件" + sourceFile.getAbsolutePath()
                    + "不可读，无法复制！");
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                if (targetFile.exists()) {
                    String absolutePath = targetFile.getAbsolutePath();
                    int lastPointIndex = absolutePath.lastIndexOf(".");
                    StringBuffer buffer = new StringBuffer(absolutePath.substring(0, lastPointIndex));
                    String fileName = targetFile.getName();
                    lastPointIndex = fileName.lastIndexOf(".");
                    buffer.append("_1" + fileName.substring(lastPointIndex));
                    targetFile = null;
                    targetFile = new File(buffer.toString());
                } else {
                    targetFile.createNewFile();
                }
                fis = new FileInputStream(sourceFile);
                bis = new BufferedInputStream(fis);
                fos = new FileOutputStream(targetFile);
                bos = new BufferedOutputStream(fos);
                int len = 0;
                while ((len = bis.read()) != -1) {
                    bos.write(len);
                }
                bos.flush();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Read file content to string.
     *
     * @param file
     * @return String
     * @throws
     * @date 2018/4/9 下午8:33
     * @author sunweisong
     */
    public static String readFileContentToString(File file) {
        StringBuffer stringBuffer = new StringBuffer();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(" " + line + "\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (fileReader != null) {
                    fileReader.close();
                    fileReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String content = stringBuffer.toString();
        stringBuffer = null;
        // Remove notes with regex.
        content = content.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
        // Remove redundant spaces.
        content = content.replaceAll("\\s{2,}", " ");
        return content.trim();
    }

    /**
     * 遍历根目录
     *
     * @param rootDirectory
     * @date 2018/4/4 上午9:17
     */
    public static List<File> traverseRootDirectory(File rootDirectory, String judgeName) {
        File[] contestIdDirectories = rootDirectory.listFiles();
        List<File> directories = new ArrayList<File>();
        if (contestIdDirectories != null && contestIdDirectories.length > 0) {
            for (File contestIdDirectory : contestIdDirectories) {
                if (contestIdDirectory.isFile()) {
                    continue;
                }
                if ("666".equals(contestIdDirectory.getName())) {
                    directories = traverseContestIdDirectory(contestIdDirectory, judgeName);
                }
            }
        } else {
            System.err.println("The directory \"" + rootDirectory.getAbsolutePath() + "\" is empty.");
        }

        return directories;
    }

    /**
     * 遍历竞赛ID目录
     *
     * @param contestIdDirectory
     * @date 2018/4/4 上午9:17
     * @author sunweisong
     */
    private static List<File> traverseContestIdDirectory(File contestIdDirectory, String judgeName) {
        File[] directories = contestIdDirectory.listFiles();
        List<File> resDirectories = new ArrayList<File>();
        if (directories != null) {
            for (File directory : directories) {
                String directoryName = directory.getName();
                if (".DS_Store".equals(directoryName) || !judgeName.equals(directoryName)) {
                    continue;
                }
                resDirectories.add(directory);
            }
        }

        return resDirectories;
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/5/3 下午5:07
     * @author sunweisong
     */
    public static void writeTestFragmentsToTargetFile(String testFileName
            , File targetFile, List<InvokeMethodModel> invokeMethodModelList) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Test File Name : " + testFileName);
            bufferedWriter.newLine();
            bufferedWriter.write("The total number of Test Method(MUT) : " + invokeMethodModelList.size());
            bufferedWriter.newLine();
            bufferedWriter.write("Details : ");
            bufferedWriter.newLine();
            for (InvokeMethodModel invokeMethodModel : invokeMethodModelList) {
                List<StatementModel> relatedInstantiateStatementList = invokeMethodModel.getRelatedInstantiateStatementList();
                List<StatementModel> relatedParameterizedStatementList = invokeMethodModel.getRelatedParameterizedStatementList();
                List<StatementModel> relatedInvokedStatementList = invokeMethodModel.getRelatedInvokedStatementList();
                bufferedWriter.write("########################################################################");
                bufferedWriter.newLine();
                bufferedWriter.write("The MID of MUT : " + invokeMethodModel.getmId());
                bufferedWriter.newLine();
                bufferedWriter.write("The name of MUT : " + invokeMethodModel.getMethodName());
                bufferedWriter.newLine();
                bufferedWriter.write("The class's name that MUT belongs to : " + invokeMethodModel.getCutName());
                bufferedWriter.newLine();
                if (relatedInstantiateStatementList != null) {
                    bufferedWriter.write("The related instantiate statements : ");
                    bufferedWriter.newLine();
                    for (int i = relatedInstantiateStatementList.size() - 1; i >= 0; i--) {
                        bufferedWriter.write("\t" + relatedInstantiateStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedParameterizedStatementList != null) {
                    bufferedWriter.write("The related parameterized statements : ");
                    bufferedWriter.newLine();
                    int relatedParameterizedStatementListSize = relatedParameterizedStatementList.size();
                    for (int i = relatedParameterizedStatementListSize - 1; i >= 0; i--) {
                        bufferedWriter.write("\t" + relatedParameterizedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedInvokedStatementList != null) {
                    bufferedWriter.write("The related invoked statements : ");
                    bufferedWriter.newLine();
                    for (int i = 0; i < relatedInvokedStatementList.size(); i++) {
                        bufferedWriter.write("\t" + relatedInvokedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.write("########################################################################");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/6/19 下午11:02
     * @author sunweisong
     */
    public static void writeContestantTFMapToTargetFile(File targetFile
            , Map<Integer, InvokeMethodModel> allTFMap) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("The total number of Test Method(MUT) : " + allTFMap.size());
            bufferedWriter.newLine();
            bufferedWriter.write("Details : ");
            bufferedWriter.newLine();
            Iterator<Map.Entry<Integer, InvokeMethodModel>> entries = allTFMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, InvokeMethodModel> entry = entries.next();
                int mid = entry.getKey();
                InvokeMethodModel invokeMethodModel = entry.getValue();
                List<StatementModel> relatedInstantiateStatementList = invokeMethodModel.getRelatedInstantiateStatementList();
                List<StatementModel> relatedParameterizedStatementList = invokeMethodModel.getRelatedParameterizedStatementList();
                List<StatementModel> relatedInvokedStatementList = invokeMethodModel.getRelatedInvokedStatementList();
                bufferedWriter.write("########################################################################");
                bufferedWriter.newLine();
                bufferedWriter.write("The MID of MUT : " + invokeMethodModel.getmId());
                bufferedWriter.newLine();
                bufferedWriter.write("The name of MUT : " + invokeMethodModel.getMethodName());
                bufferedWriter.newLine();
                bufferedWriter.write("The class's name that MUT belongs to : " + invokeMethodModel.getCutName());
                bufferedWriter.newLine();
                bufferedWriter.write("The invoked times of MUT : " + invokeMethodModel.getInvokeTimes());
                bufferedWriter.newLine();
                if (relatedInstantiateStatementList != null) {
                    bufferedWriter.write("The related instantiate statements : ");
                    bufferedWriter.newLine();
                    for (int i = relatedInstantiateStatementList.size() - 1; i >= 0; i--) {
                        bufferedWriter.write("\t" + relatedInstantiateStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedParameterizedStatementList != null) {
                    bufferedWriter.write("The related parameterized statements : ");
                    bufferedWriter.newLine();
                    int relatedParameterizedStatementListSize = relatedParameterizedStatementList.size();
                    for (int i = relatedParameterizedStatementListSize - 1; i >= 0; i--) {
                        bufferedWriter.write("\t" + relatedParameterizedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedInvokedStatementList != null) {
                    bufferedWriter.write("The related invoked statements : ");
                    bufferedWriter.newLine();
                    for (int i = 0; i < relatedInvokedStatementList.size(); i++) {
                        bufferedWriter.write("\t" + relatedInvokedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.write("########################################################################");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/6/21 下午5:09
     * @author sunweisong
     */
    public static void writeTFsByMIDToTargetFile(File targetFile
            , List<ContestantTFModel> contestantTFModelList
            , MUTModel mutModel) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("The detail information of MUT:");
            bufferedWriter.newLine();
            bufferedWriter.write("------------------------------------------------------------------------");
            bufferedWriter.newLine();
            bufferedWriter.write("\tThe id of MUT: " + mutModel.getMethodId());
            bufferedWriter.newLine();
            bufferedWriter.write("\tThe name of MUT : " + mutModel.getMethodName());
            bufferedWriter.newLine();
            bufferedWriter.write("\tThe class where the MUT belongs to : " + mutModel.getClassName());
            bufferedWriter.newLine();
            bufferedWriter.write("\tThe arguments of MUT : " + mutModel.getArguments());
            bufferedWriter.newLine();
            String temp = mutModel.getIsConstructor();
            if (temp.equals("constructor")) {
                bufferedWriter.write("\tWhether this MUT is a constructor? : yes");
            } else {
                bufferedWriter.write("\tWhether this MUT is a constructor? : no");
            }
            bufferedWriter.newLine();

            if (contestantTFModelList == null) {
                bufferedWriter.write("\tNo contestant testing this MUT.");
                bufferedWriter.newLine();
                bufferedWriter.write("------------------------------------------------------------------------");
                bufferedWriter.newLine();
            } else {
                int totalNumber = contestantTFModelList.size();
                bufferedWriter.write("\tThe total number of TF : " + totalNumber);
                bufferedWriter.newLine();
                bufferedWriter.write("------------------------------------------------------------------------");
                bufferedWriter.newLine();
                bufferedWriter.newLine();
                bufferedWriter.write("All test fragments: ");
                bufferedWriter.newLine();
                for (ContestantTFModel contestantTFModel : contestantTFModelList) {
                    int CID = contestantTFModel.getCID();
                    InvokeMethodModel invokeMethodModel = contestantTFModel.getInvokeMethodModel();
                    List<StatementModel> relatedInstantiateStatementList = invokeMethodModel.getRelatedInstantiateStatementList();
                    List<StatementModel> relatedParameterizedStatementList = invokeMethodModel.getRelatedParameterizedStatementList();
                    List<StatementModel> relatedInvokedStatementList = invokeMethodModel.getRelatedInvokedStatementList();
                    bufferedWriter.write("########################################################################");
                    bufferedWriter.newLine();
                    bufferedWriter.write("The CID of contestant : " + CID);
                    bufferedWriter.newLine();
                    bufferedWriter.write("The details of test fragment:");
                    bufferedWriter.newLine();
                    bufferedWriter.write("{");
                    bufferedWriter.newLine();
                    if (relatedInstantiateStatementList != null) {
                        bufferedWriter.write("    The related instantiate statements : ");
                        bufferedWriter.newLine();
                        for (int i = relatedInstantiateStatementList.size() - 1; i >= 0; i--) {
                            bufferedWriter.write("\t" + relatedInstantiateStatementList.get(i).getContent());
                            bufferedWriter.newLine();
                        }
                    }
                    if (relatedParameterizedStatementList != null) {
                        bufferedWriter.write("    The related parameterized statements : ");
                        bufferedWriter.newLine();
                        int relatedParameterizedStatementListSize = relatedParameterizedStatementList.size();
                        for (int i = relatedParameterizedStatementListSize - 1; i >= 0; i--) {
                            bufferedWriter.write("\t" + relatedParameterizedStatementList.get(i).getContent());
                            bufferedWriter.newLine();
                        }
                    }
                    if (relatedInvokedStatementList != null) {
                        bufferedWriter.write("    The related invoked statements : ");
                        bufferedWriter.newLine();
                        for (int i = 0; i < relatedInvokedStatementList.size(); i++) {
                            bufferedWriter.write("\t" + relatedInvokedStatementList.get(i).getContent());
                            bufferedWriter.newLine();
                        }
                    }
                    bufferedWriter.write("}");
                    bufferedWriter.newLine();
                    bufferedWriter.write("########################################################################");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/6/26 上午11:12
     * @author sunweisong
     */
    public static void writeTFsSimValueToTargetFile(File targetFile
            , List<ContestantSimilarity> contestantSimilarityList) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("CID1\tCID2\tSimValue");
            bufferedWriter.newLine();
            for (ContestantSimilarity contestantSimilarity :
                    contestantSimilarityList) {
                int CID1 = contestantSimilarity.getCID();
                List<TFSimilarityModel> tfSimilarityModelList = contestantSimilarity.getTfSimilarityModelList();
                if (tfSimilarityModelList == null) {
                    // no more than one contestant testing this MUT.
                    continue;
                }
                for (TFSimilarityModel tFSimilarityModel :
                        tfSimilarityModelList) {
                    int CID2 = tFSimilarityModel.getCID2();
                    int simValue = tFSimilarityModel.getSimValue();
                    bufferedWriter.write(CID1 + "\t");
                    bufferedWriter.write(CID2 + "\t");
                    bufferedWriter.write(simValue + "");
                    bufferedWriter.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeTFStatisticResultToTargetFile(File targetFile
            , Map<Integer, Integer> mIdTFNumberMap) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("MID\tCNum\t");
            bufferedWriter.newLine();
            Iterator<Map.Entry<Integer, Integer>> entries = mIdTFNumberMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, Integer> entry = entries.next();
                bufferedWriter.write(entry.getKey() + "\t");
                bufferedWriter.write(entry.getValue() + "\t");
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/7/18 上午10:00
     * @author sunweisong
     */
    public static void writeSimValueMatrixToTargetFile(int[] midArray
            , List<SimValueMatrixModel> simValueMatrixModelList
            , File targetFile) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile, true)
                    , "UTF-8"), 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CsvWriter csvWriter = new CsvWriter(bufferedWriter, ',');
        int arrayLength = midArray.length;
        String[] headers = new String[arrayLength + 1];
        headers[0] = "<CID1, CID2>";
        for (int index = 0; index < arrayLength; index++) {
            headers[index + 1] = midArray[index] + "";
        }
        try {
            if (simValueMatrixModelList == null) {
                csvWriter.writeRecord(headers);
                return;
            }
            for (int index = 0; index < simValueMatrixModelList.size(); index++) {
                SimValueMatrixModel model = simValueMatrixModelList.get(index);
                double[] simValueArray = model.getSimValueArray();
                String[] contents = new String[arrayLength + 1];
                contents[0] = "<" + model.getCid1() + ", " + model.getCid2() + ">";
                for (int index1 = 0; index1 < arrayLength; index1++) {
                    contents[index1 + 1] = simValueArray[index1] + "";
                }
                csvWriter.writeRecord(contents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/7/19 下午1:45
     * @author sunweisong
     */
    public static void writePCLabelModelListToTargetFile(List<PCLabelModel> modelList, File targetFile) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("<CID1, CID2, Label>");
            bufferedWriter.newLine();
            for (PCLabelModel pcLabelModel : modelList) {
                bufferedWriter.write(pcLabelModel.getCid1()
                        + ", " + pcLabelModel.getCid2()
                        + ", " + pcLabelModel.getLabel());
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @date 2018/7/25 下午2:24
     * @author sunweisong
     */
    public static void writeMatrixToCSVFile(File targetFile0
            , File targetFile1
            , String[] headerArray
            , List<String[]> contentBeLabeledList) {
        BufferedWriter bufferedWriter0 = null;
        BufferedWriter bufferedWriter1 = null;
        CsvWriter csvWriter0 = null;
        CsvWriter csvWriter1 = null;
        if (headerArray != null) {
            // write header
            if (!targetFile0.exists()) {
                try {
                    targetFile0.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!targetFile1.exists()) {
                try {
                    targetFile1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bufferedWriter0 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile0
                        , true)
                        , "UTF-8"), 1024);
                bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile1
                        , true)
                        , "UTF-8"), 1024);
                csvWriter0 = new CsvWriter(bufferedWriter0, ',');
                csvWriter1 = new CsvWriter(bufferedWriter1, ',');
                csvWriter0.writeRecord(headerArray);
                csvWriter1.writeRecord(headerArray);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (contentBeLabeledList != null) {
                try {
                    if (targetFile0 != null && targetFile1 == null) {
                        bufferedWriter0 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile0
                                , true)
                                , "UTF-8"), 1024);
                    }
                    if (targetFile0 == null && targetFile1 != null) {
                        bufferedWriter0 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile1
                                , true)
                                , "UTF-8"), 1024);
                    }
                    csvWriter0 = new CsvWriter(bufferedWriter0, ',');
                    for (String[] contents : contentBeLabeledList) {
                        csvWriter0.writeRecord(contents);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bufferedWriter0 != null) {
            try {
                bufferedWriter0.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bufferedWriter1 != null) {
            try {
                bufferedWriter1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (csvWriter0 != null) {
            csvWriter0.close();
        }
        if (csvWriter1 != null) {
            csvWriter1.close();
        }
    }

    public static void writeMatrixToCSVFile(File targetFile
            , String[] headerArray
            , List<String[]> contentBeLabeledList, boolean isAppend) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        FileOutputStream outputStream = null;
        try {
            if (isAppend) {
                outputStream = new FileOutputStream(targetFile, true);
            } else{
                outputStream = new FileOutputStream(targetFile);
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"), 1024);

        } catch (IOException e) {
            e.printStackTrace();
        }
        CsvWriter csvWriter = new CsvWriter(bufferedWriter, ',');
        try {
            if (headerArray != null) {
                csvWriter.writeRecord(headerArray);
            } else {
                if (contentBeLabeledList != null) {
                    for (String[] contents : contentBeLabeledList) {
                        csvWriter.writeRecord(contents);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }


    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/25 下午4:12
      * @author sunweisong
      */
    public static void writeFormatTrainDataToTargetFile(File targetFile
            , List<String> trainDataRecordList) {
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String record : trainDataRecordList) {
                bufferedWriter.write(record + System.lineSeparator());
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
      * 从diff文件中读取change line
      * @param
      * @return
      * @throws
      * @date 2018/8/5 下午12:48
      * @author sunweisong
      */
    public static int calculateDiffLineFromDiffFile(File diffFile) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int changLineNumber = 0;
        try {
            fileReader = new FileReader(diffFile);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if ("".equals(line.trim())) {
                    continue;
                }
                if (line.trim().length() < 2) {
                    continue;
                }
                char firstChar = line.charAt(0);
                if (firstChar == '-' || firstChar == '+') {
                    changLineNumber++;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (fileReader != null) {
                    fileReader.close();
                    fileReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return changLineNumber;
    }

    /**
      * 读取目标文件中的代码行数
      * @param
      * @return
      * @throws
      * @date 2018/8/5 下午1:12
      * @author sunweisong
      */
    public static int calculateFileLineFromTargetFile(File targetFile) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int lineNumber = 0;
        try {
            fileReader = new FileReader(targetFile);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    lineNumber++;
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (fileReader != null) {
                    fileReader.close();
                    fileReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineNumber;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午3:05
      * @author sunweisong
      */
    public static void writeDetectResultByPlaggieToTargetFile(Queue<String> resultQueue
            , File targetFile) {
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            targetFile.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            while (resultQueue.peek() != null) {
                bufferedWriter.write(resultQueue.poll());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                bufferedWriter = null;
                fileWriter.close();
                fileWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午5:09
      * @author sunweisong
      */
    public static String readResultContentsToString(File resultFile) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int lineNumber = 0;
        StringBuffer buffer = new StringBuffer();
        try {
            fileReader = new FileReader(resultFile);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line.trim() + " ");
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                    bufferedReader = null;
                }
                if (fileReader != null) {
                    fileReader.close();
                    fileReader = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/6 下午6:30
      * @author sunweisong
      */
    public static void writeRecordsToTargetFile(File targetFile
            , String[] headArray, String record, boolean isAppend) {
        try {
            targetFile.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            if (isAppend) {
                fileWriter = new FileWriter(targetFile, true);
            } else {
                fileWriter = new FileWriter(targetFile);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            if (headArray != null) {
                for (String head : headArray) {
                    bufferedWriter.write(head + "\t");
                }
                bufferedWriter.newLine();
            } else {
                if (record != null) {
                    bufferedWriter.write(record);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                bufferedWriter = null;
                fileWriter.close();
                fileWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/6 下午7:48
      * @author sunweisong
      */
    public static void writeContestantTFToTargetFile(File targetDirectory
            , Map<Integer,InvokeMethodModel> allTFMap) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        File targetFile = null;
        StringBuffer buffer = new StringBuffer(targetDirectory.toString() + File.separator);
        try {
            Iterator<Map.Entry<Integer, InvokeMethodModel>> entries = allTFMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, InvokeMethodModel> entry = entries.next();
                int mid = entry.getKey();
                String fileName = mid + ".txt";
                buffer.append(fileName);
                targetFile = new File(buffer.toString());
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                targetFile.createNewFile();
                fileWriter = new FileWriter(targetFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("class TF {{");
                bufferedWriter.newLine();
                InvokeMethodModel invokeMethodModel = entry.getValue();
                List<StatementModel> relatedInstantiateStatementList = invokeMethodModel.getRelatedInstantiateStatementList();
                List<StatementModel> relatedParameterizedStatementList = invokeMethodModel.getRelatedParameterizedStatementList();
                List<StatementModel> relatedInvokedStatementList = invokeMethodModel.getRelatedInvokedStatementList();
                if (relatedInstantiateStatementList != null) {
                    for (int i = relatedInstantiateStatementList.size() - 1; i >= 0; i--) {
                        bufferedWriter.write(relatedInstantiateStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedParameterizedStatementList != null) {
                    int relatedParameterizedStatementListSize = relatedParameterizedStatementList.size();
                    for (int i = relatedParameterizedStatementListSize - 1; i >= 0; i--) {
                        bufferedWriter.write(relatedParameterizedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                if (relatedInvokedStatementList != null) {
                    for (int i = 0; i < relatedInvokedStatementList.size(); i++) {
                        bufferedWriter.write(relatedInvokedStatementList.get(i).getContent());
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.write("}}");
                bufferedWriter.newLine();
                int start = buffer.indexOf(fileName);
                buffer.replace(start, buffer.length(), "");
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer = null;
            targetFile = null;
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                    bufferedWriter = null;
                }
                if (fileWriter != null) {
                    fileWriter.close();
                    fileWriter = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeTFsByCIDToTargetFile(int cid1, int cid2
            , List<SVTFModel> svtfModelList, File targetFile) {
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(targetFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("<" +cid1 + " , " + cid2 + ">");
            bufferedWriter.newLine();
            bufferedWriter.write("————————————————————————————————————");
            bufferedWriter.newLine();
            for (SVTFModel svtfModel : svtfModelList) {
                int simValue = svtfModel.getSimValue();
                bufferedWriter.write("[MID: " + svtfModel.getMID() + "，TF Similarity：" + simValue + "%]");
                bufferedWriter.newLine();
                bufferedWriter.write("####################################");
                bufferedWriter.newLine();
                List<TFModel> tfModelList = svtfModel.getTfModelList();
                for (TFModel model : tfModelList) {
                    bufferedWriter.write(model.getCid() + ": " +model.getFragment());
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("####################################");
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                bufferedWriter = null;
                fileWriter.close();
                fileWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
