package demo.com.tcsa.analysis;

import com.csvreader.CsvReader;
import demo.com.tcsa.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
  * @Author sunweisong
  * @Date 2018/7/27 上午9:53
  */
public class PlagiarismAnalysis {

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/27 上午10:05
      * @author sunweisong
      */
    public static void analysis(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append("/666/analysis/Datalog/sim_analysis");
        String simMatrixFilePath = buffer.toString() + File.separator + "sim_matrix.csv";
        String maxSimValueStatisticFilePath = buffer.toString() + File.separator + "max_sv_statistic_result.csv";
        File simMatrixFile = new File(simMatrixFilePath);
        if (simMatrixFile.exists()) {
            constructMaxSVMatrix(simMatrixFilePath, maxSimValueStatisticFilePath);
            return;
        }

        String simMatrixWithLabelFilePath = buffer.toString() + File.separator + "sim_matrix_with_label.csv";
        String maxSimValueWithLabelFilePath = buffer.toString() + File.separator + "max_similarity_with_label.csv";
        File simMatrixWithLabelFile = new File(simMatrixWithLabelFilePath);
        if (simMatrixWithLabelFile.exists()) {
//            constructMaxSVMatrixWithLabel(simMatrixWithLabelFilePath, maxSimValueWithLabelFilePath);

            //
//            int threshold = 100;
//            String resultOutputFilePath = buffer.toString()
//                    + File.separator + "search result by threshold"
//                    + File.separator + "search_result_by_" + threshold + ".csv";
//            List<String[]> records = searchRecordByThreshold(maxSVMatrixFilePath, threshold
//                    , resultOutputFilePath);
//            if (records == null) {
//                System.out.println("no search result");
//                return;
//            }
//
////            double accuracy = calculateAccuracy(records, threshold);
//
//            List<String[]> exceptionRecords = findExceptionRecords(records);
//            if (exceptionRecords != null) {
//                String exceptionRecordsOutPutFilePath = buffer.toString()
//                        + File.separator + "exception_records_from_pclabel_matrix.csv";
//                List<String[]> exceptionRecordsFromSimMatrix = findExceptionRecordsFrom(exceptionRecords
//                        , pcLabelMatrixFilePath, exceptionRecordsOutPutFilePath);
//                if (exceptionRecordsFromSimMatrix != null) {
//                    System.out.println(exceptionRecordsFromSimMatrix.size());
//                }
//            }
        }
        
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/11 上午10:58
      * @author sunweisong
      */
    private static void constructMaxSVMatrix(String simMatrixFilePath
            , String maxSimValueStatisticFilePath) {
        File targetFile = new File(maxSimValueStatisticFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray
                , null, true);
        CsvReader reader = null;
        try {
            reader = new CsvReader(simMatrixFilePath, ',', Charset.forName("UTF-8"));
            List<String[]> records = new ArrayList<>();
            int count = 0;
            while(reader.readRecord()) {
                count++;
                if (count == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                String cidString = rowContents[0];
                int start = cidString.indexOf("<");
                int end = cidString.indexOf(">");
                cidString = cidString.substring(start + 1, end);
                String[] cidArray = cidString.split(",");
                String CID1 = cidArray[0].trim();
                String CID2 = cidArray[1].trim();
                int length = rowContents.length;
                int maxSimValue = 0;
                for (int index = 1; index < length - 1; index++) {
                    int simValue = Integer.parseInt(rowContents[index]);
                    if (simValue > maxSimValue) {
                        maxSimValue = simValue;
                    }
                }
                records.add(new String[] {CID1, CID2, maxSimValue + ""});
                if (records.size() % 1000 == 0) {
                    FileUtil.writeMatrixToCSVFile(targetFile, null
                            , records, true);
                    records.clear();
                }
            }
            if (records.size() > 0) {
                FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
                records.clear();
                records = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/27 下午12:15
      * @author sunweisong
      */
    private static List<String[]> findExceptionRecordsFrom(List<String[]> exceptionRecords
            , String pcLabelMatrixFilePath, String exceptionRecordsOutPutFilePath) {
        List<String> cidGroupList = new ArrayList<>(exceptionRecords.size());
        for (String[] record : exceptionRecords) {
            String cidGroup = record[0].trim();
            cidGroupList.add(cidGroup);
        }
        List<String[]> exceptionRecordList = new ArrayList<>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(pcLabelMatrixFilePath, ',', Charset.forName("UTF-8"));
            File targetFile = new File(exceptionRecordsOutPutFilePath);
            int count = 0;
            while(reader.readRecord()) {
                count++;
                if (count == 1) {
                    String[] headerArray = reader.getValues();
                    FileUtil.writeMatrixToCSVFile(targetFile, headerArray
                            , null, true);
                    continue;
                }
                String[] rowContents = reader.getValues();
                String cidGroup = rowContents[0].trim();
                for (String cidString : cidGroupList) {
                    if (cidString.equals(cidGroup)) {
                        exceptionRecordList.add(rowContents);
                        break;
                    }
                }
            }
            if (exceptionRecordList.size() > 0) {
                FileUtil.writeMatrixToCSVFile(targetFile,null
                        , exceptionRecordList, true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        if (exceptionRecordList.size() == 0) {
            exceptionRecordList = null;
        }
        return exceptionRecordList;
    }


    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/27 上午11:50
      * @author sunweisong
      */
    private static List<String[]> findExceptionRecords(List<String[]> records) {
        List<String[]> exceptionRecords = new ArrayList<>();
        for (String[] record : records) {
            String labelString = record[record.length - 1];
            String simValueString = record[1];
            int simValue = Integer.parseInt(simValueString);
            if (simValue == 100 && "0".equals(labelString)) {
                exceptionRecords.add(record);
            }
        }
        if (exceptionRecords.size() == 0) {
            exceptionRecords = null;
        }
        return exceptionRecords;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/27 上午11:45
      * @author sunweisong
      */
    private static double calculateAccuracy(List<String[]> records, int threshold) {
        int totalRecordNumber = records.size();
        int positiveNumber = 0;
        int negativeNumber = 0;
        int effectiveRecordNumber;
        for (String[] record : records) {
            String labelString = record[record.length - 1];
            int label = Integer.parseInt(labelString);
            if (label == 1) {
                positiveNumber++;
            } else if (label == 0) {
                negativeNumber++;
            } else {
                System.err.println("以下记录存在错误！");
                for (String content : record) {
                    System.out.print(content + " ");
                    System.out.println();
                }
            }
        }
        effectiveRecordNumber = positiveNumber + negativeNumber;
        double accuracy = 0;
        if (effectiveRecordNumber != 0) {
            accuracy = (double) positiveNumber / effectiveRecordNumber;
        }
        System.out.println("Calculate Accuracy Log:");
        System.out.println("\tthreshold:" + threshold);
        System.out.println("\ttotalRecordNumber:" + totalRecordNumber);
        System.out.println("\teffectiveRecordNumber:" + effectiveRecordNumber);
        System.out.println("\tpositiveNumber:" + positiveNumber);
        System.out.println("\tnegativeNumber:" + negativeNumber);
        System.out.println("Accuracy:" + accuracy + "(" + positiveNumber + "/" + effectiveRecordNumber + ")");
        return accuracy;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/27 上午10:55
      * @author sunweisong
      */
    private static List<String[]> searchRecordByThreshold(String maxSVMatrixFilePath, int threshold
            , String resultOutputFilePath) {
        List<String[]> allSearchResultList = new ArrayList<>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(maxSVMatrixFilePath, ',', Charset.forName("UTF-8"));
            File targetFile = new File(resultOutputFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
//            String[] headerArray =  {"<CID1, CID2>", "MAX_Sim_Value", "Class(0 or 1)"};
//            FileUtil.writeMatrixToCSVFile(targetFile, headerArray, null, false);
            List<String[]> contentBeLabeledList = new ArrayList<>();
            int count = 0;
            while(reader.readRecord()) {
                count++;
                if (count == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                String simValueString = rowContents[1];
                int simValue = Integer.parseInt(simValueString);
                if (simValue < threshold) {
                    continue;
                }
                contentBeLabeledList.add(rowContents);
            }
            if (contentBeLabeledList.size() > 0) {
                FileUtil.writeMatrixToCSVFile(targetFile,null
                        , contentBeLabeledList, false);
                allSearchResultList.addAll(contentBeLabeledList);
                contentBeLabeledList.clear();
                contentBeLabeledList = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        if (allSearchResultList.size() == 0) {
            allSearchResultList = null;
        }
        return allSearchResultList;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/27 上午10:22
      * @author sunweisong
      */
    private static void constructMaxSVMatrixWithLabel(String simMatrixFilePath, String maxSVMatrixFilePath) {
        CsvReader reader = null;
        try {
            reader = new CsvReader(simMatrixFilePath, ',', Charset.forName("UTF-8"));
            File targetFile = new File(maxSVMatrixFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            String[] headerArray =  {"<CID1, CID2>", "MAX_Sim_Value", "Class(0 or 1)"};
            FileUtil.writeMatrixToCSVFile(targetFile, headerArray, null, true);
            List<String[]> contentBeLabeledList = new ArrayList<>();
            int count = 0;
            while(reader.readRecord()) {
                count++;
                if (count == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                String cidString = rowContents[0];
                int length = rowContents.length;
                String labelString = rowContents[length - 1];
                int maxSimValue = 0;
                for (int index = 1; index < length - 1; index++) {
                    int simValue = Integer.parseInt(rowContents[index]);
                    if (simValue > maxSimValue) {
                        maxSimValue = simValue;
                    }
                }
                contentBeLabeledList.add(new String[] {cidString, maxSimValue + "", labelString});
                if (contentBeLabeledList.size() % 100 == 0) {
                    FileUtil.writeMatrixToCSVFile(targetFile, null, contentBeLabeledList, true);
                    contentBeLabeledList.clear();
                }
            }
            if (contentBeLabeledList.size() > 0) {
                FileUtil.writeMatrixToCSVFile(targetFile,null, contentBeLabeledList, true);
                contentBeLabeledList.clear();
                contentBeLabeledList = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }
}
