package demo.com.tcsa.analysis;

import com.csvreader.CsvReader;
import demo.dao.MUTModelDao;
import demo.dao.PCLabelModelDao;
import demo.dao.SimValueModelDao;
import demo.dao.TFModelDao;
import demo.com.tcsa.daoImpl.MUTModelDaoImpl;
import demo.com.tcsa.daoImpl.PCLabelModelDaoImpl;
import demo.com.tcsa.daoImpl.SimValueModelDaoImpl;
import demo.com.tcsa.daoImpl.TFModelDaoImpl;
import demo.com.tcsa.model.CIDPairModel;
import demo.com.tcsa.model.PCLabelModel;
import demo.com.tcsa.model.SimValueMatrixModel;
import demo.com.tcsa.model.SimValueModel;
import demo.com.tcsa.util.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;

/**
  * @Author sunweisong
  * @Date 2018/7/11 下午3:12
  */
public class SimMatrixAnalysis {

    private static MUTModelDao mutModelDao = MUTModelDaoImpl.getInstance();
    private static SimValueModelDao simValueModelDao = SimValueModelDaoImpl.getInstance();
    private static PCLabelModelDao pcLabelModelDao = PCLabelModelDaoImpl.getInstance();
    private static TFModelDao tfModelDao = TFModelDaoImpl.getInstance();

    private static final HashSet<Integer> exceptCIDSet = new HashSet<Integer>() {{
        add(82); add(163); add(182); add(248);
        add(267); add(274); add(306); add(308);
        add(410); add(461); add(542); add(548);
        add(568); add(574); add(579); add(589);
    }};

    private static final String usefulDataRootPath = "/Users/sunweisong/Desktop/useful data for paper";
    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/11 下午3:12
      * @author sunweisong
      */
    public static void analysis(String rootPath) {

//        statisticHumanReviewResult();


        // static all Plagiarism contestants
//        List<PCLabelModel> modelList = statisticAllPlagiarism(rootPath);
//        System.out.println(modelList.size());
//        if (modelList != null) {
//            savePlagiarismContestantModelList(modelList, rootPath);
//            savePlagiarismContestantModelListToData(modelList);
//        }

        // construct similarity matrix
        List<Integer> cidList = getAllContestantIDFromIDDirectory(rootPath);
        int[] midArray = getAllMUTIDFromDatabase();
        if (cidList != null) {
//            constructSimilarityMatrix(cidList, midArray, rootPath, 0);
            constructSimilarityMatrix(cidList, midArray, rootPath, 1);
        }
//
        // all data set contains label = 1 and label = 0
//        constructPlagiarismMatrix(rootPath);
//
        // divide class data set from PlagiarismMatrix
//        divideClassDataSetT(rootPath);

//        // format data set for svm training
//        formatTrainDataSetForSVM(rootPath);

    }

    /**
      *  Statistic all the CID pairs of contestants which were labeled '0' or labeled '1'.
      * @param
      * @return
      * @throws
      * @date 2018/8/11 下午7:36
      * @author sunweisong
      */
    private static void statisticHumanReviewResult() {
        StringBuffer buffer = new StringBuffer(usefulDataRootPath);
        buffer.append(File.separator + "human review");

        String humanReviewFilePath = buffer.toString()
                + File.separator + "Datalog_human_label_matrix_0811_02_619.xlsx";
        String[] headArray = {"CID1", "CID2", "Label"};

        // label = 0 or 1
        String allStaticFilePath = buffer.toString()
                + File.separator + "human_review_statistic_all.csv";
        List<PCLabelModel> cidPairModelList = statisticCIDPairs(humanReviewFilePath);
        saveStatisticResultToCSVFile(cidPairModelList, allStaticFilePath, headArray);
        System.out.println("all : " + cidPairModelList.size());

        // label = 0
//        String plagiarismStaticFilePath = buffer.toString()
//                + File.separator + "human_review_statistic_plagiarism.csv";
//        List<CIDPairModel> cidPairModelList = statisticCIDPairsByLabel(humanReviewFilePath, 0);
//        saveStatisticResultToCSVFile(cidPairModelList, plagiarismStaticFilePath, headArray, 0);
//        System.out.println("label = 0 : " + cidPairModelList.size());

        // label = 1
//        String nonPlagiarismStaticFilePath = buffer.toString()
//                + File.separator + "human_review_statistic_non_plagiarism.csv";
//        List<CIDPairModel> cidPairModelList = statisticCIDPairsByLabel(humanReviewFilePath, 1);
//        saveStatisticResultToCSVFile(cidPairModelList, nonPlagiarismStaticFilePath, headArray, 1);
//        System.out.println("label = 1 : " + cidPairModelList.size());

        cidPairModelList.clear();
        cidPairModelList = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/11 下午7:28
      * @author sunweisong
      */
    private static void saveStatisticResultToCSVFile(List<CIDPairModel> cidPairModelList
            , String plagiarismStaticFilePath, String[] headArray, int label) {
        File targetFile = new File(plagiarismStaticFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
        List<String[]> records = new ArrayList<>(cidPairModelList.size());
        for (CIDPairModel model : cidPairModelList) {
            records.add(new String[]{model.getCID1(), model.getCID2(), label + ""});
        }
        FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
    }

    private static void saveStatisticResultToCSVFile(List<PCLabelModel> cidPairModelList
            , String plagiarismStaticFilePath, String[] headArray) {
        File targetFile = new File(plagiarismStaticFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
        List<String[]> records = new ArrayList<>(cidPairModelList.size());
        for (PCLabelModel model : cidPairModelList) {
            records.add(new String[]{model.getCid1() + ""
                    , model.getCid2() + "", model.getLabel() + ""});
        }
        FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
    }

    private static List<PCLabelModel> statisticCIDPairs(String humanReviewFilePath) {
        File humanReviewFile = new File(humanReviewFilePath);
        if (!humanReviewFile.exists()) {
            System.out.println("文件：" + humanReviewFilePath + "不存在！");
            return null;
        }
        List<PCLabelModel> cidPairModelList = readExcelToCIDPairModel(humanReviewFile);
        return cidPairModelList;
    }

    private static List<CIDPairModel> statisticCIDPairsByLabel(String humanReviewFilePath
            , int label) {
        File humanReviewFile = new File(humanReviewFilePath);
        if (!humanReviewFile.exists()) {
            System.out.println("文件：" + humanReviewFilePath + "不存在！");
            return null;
        }
        List<CIDPairModel> cidPairModelList = readExcelToCIDPairModel(humanReviewFile, label);
        return cidPairModelList;
    }

    private static List<CIDPairModel> readExcelToCIDPairModel(File humanReviewFile
            , int label) {
        Workbook wb = null;
        List<CIDPairModel> cidPairModelList = null;
        try {
            wb = WorkbookFactory.create(humanReviewFile);
            cidPairModelList = readExcelForCIDPair(wb, 0, 1, 0, label);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cidPairModelList;
    }

    private static List<PCLabelModel> readExcelToCIDPairModel(File humanReviewFile) {
        Workbook wb = null;
        List<PCLabelModel> cidPairModelList = null;
        try {
            wb = WorkbookFactory.create(humanReviewFile);
            cidPairModelList = readExcelForCIDPair(wb, 0, 1, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cidPairModelList;
    }

    private static List<CIDPairModel> readExcelForCIDPair(Workbook wb
            , int sheetIndex, int startReadLine, int tailLine, int label) {
        List<CIDPairModel> cidPairModelList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row firstRow = sheet.getRow(0);
        int cellNumber = firstRow.getLastCellNum();
        int[] cidArray = new int[cellNumber - 1];
        for (int index = 1; index < cellNumber; index++) {
            Cell cell = firstRow.getCell(index);
            if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                cidArray[index - 1] = (int)cell.getNumericCellValue();
            } else {
                String cellValue = cell.getStringCellValue();
                cidArray[index - 1] = Integer.parseInt(cellValue);
            }
        }
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            int cid1 = 0;
            if (firstCell.getCellType() == CELL_TYPE_NUMERIC) {
                cid1 = (int)firstCell.getNumericCellValue();
            } else {
                break;
            }
            for (int j = 1; j < cellNumber; j++) {
                Cell cell = row.getCell(j);
                int cid2 = cidArray[j - 1];
                int type = 0;
                if (cell.getCellType() == CELL_TYPE_BLANK) {
                    break;
                }
                if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                    type = (int)cell.getNumericCellValue();
                }
                if (type != label) {
                    continue;
                }
                if (cid1 <= cid2) {
                    cidPairModelList.add(new CIDPairModel(cid1 + "", cid2 + ""));
                } else {
                    cidPairModelList.add(new CIDPairModel(cid2 + "", cid1 + ""));
                }
            }
        }
        return cidPairModelList;
    }

    private static List<PCLabelModel> readExcelForCIDPair(Workbook wb
            , int sheetIndex, int startReadLine, int tailLine) {
        List<PCLabelModel> cidPairModelList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row firstRow = sheet.getRow(0);
        int cellNumber = firstRow.getLastCellNum();
        int[] cidArray = new int[cellNumber - 1];
        for (int index = 1; index < cellNumber; index++) {
            Cell cell = firstRow.getCell(index);
            if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                cidArray[index - 1] = (int)cell.getNumericCellValue();
            } else {
                String cellValue = cell.getStringCellValue();
                cidArray[index - 1] = Integer.parseInt(cellValue);
            }
        }
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            int cid1 = 0;
            if (firstCell.getCellType() == CELL_TYPE_NUMERIC) {
                cid1 = (int)firstCell.getNumericCellValue();
            } else {
                break;
            }
            for (int j = 1; j < cellNumber; j++) {
                Cell cell = row.getCell(j);
                int cid2 = cidArray[j - 1];
                int type = 0;
                if (cell.getCellType() == CELL_TYPE_BLANK) {
                    break;
                }
                if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                    type = (int)cell.getNumericCellValue();
                }
                if (cid1 <= cid2) {
                    cidPairModelList.add(new PCLabelModel(cid1, cid2, type));
                } else {
                    cidPairModelList.add(new PCLabelModel(cid2, cid1, type));
                }
            }
        }
        return cidPairModelList;
    }


    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/9 下午11:59
      * @author sunweisong
      */
    private static void savePlagiarismContestantModelListToData(List<PCLabelModel> modelList) {
        System.out.println(modelList.size());
        for (PCLabelModel model : modelList) {
            pcLabelModelDao.savePCLabelModel(model);
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/25 下午4:20
      * @author sunweisong
      */
    private static void formatTrainDataSetForSVM(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis");
        String targetDirectoryPath = buffer.toString()
                + File.separator + "svm try data";
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        String class0FilePath = buffer.toString() + File.separator + "pc_class_0_matrix.csv";
        File class0FileFile = new File(class0FilePath);
        if (class0FileFile.exists()) {
//            formatAndSaveTrainData(class0FilePath, targetFilePath);
            formatDataSet(class0FilePath, targetDirectoryPath);
        }
        String class1FilePath = buffer.toString() + File.separator + "pc_class_1_matrix.csv";
        File class1FileFile = new File(class1FilePath);
        if (class1FileFile.exists()) {
            formatDataSet(class1FilePath, targetDirectoryPath);
//            formatAndSaveTrainData(class1FilePath, targetDirectory);
        }
        buffer = null;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/25 下午10:00
      * @author sunweisong
      */
    private static void formatDataSet(String classFilePath
            , String targetDirectory) {
//        int totalRowNumber = getRecordRowNumberFromCSVFile(classFilePath);
//        int splitLine = totalRowNumber / 4 * 3;
//        System.out.println("4分之3分割线：" + splitLine);
        StringBuffer buffer = new StringBuffer(targetDirectory + File.separator);
        CsvReader reader = null;
        try {
            reader = new CsvReader(classFilePath, ',', Charset.forName("UTF-8"));
            List<String> trainDataRecordList = new ArrayList<>();
            int index = 0;
            StringBuffer recordBuffer = new StringBuffer();
            while(reader.readRecord()) {
                index++;
                if (index == 1) {
                    continue;
                }
                if (index == 4001) {
                    break;
                }
                String[] rowContents = reader.getValues();
                int length = rowContents.length;
                String labelString = rowContents[length - 1];
                int label = Integer.parseInt(labelString);
                recordBuffer.append(label);
                for (int i = 1; i < length; i++) {
                    recordBuffer.append(" " + i + ":" + rowContents[i]);
                }
                trainDataRecordList.add(recordBuffer.toString());
                recordBuffer.setLength(0);
                if (trainDataRecordList.size() % 100 == 0) {
                    int start = 0;
//                    if (index < splitLine) {
                    if (index < 3000) {
                        buffer.append("train data sets");
                        start = buffer.indexOf("train data sets");
                    } else {
                        buffer.append("test data sets");
                        start = buffer.indexOf("test data sets");
                    }
                    String directoryPath = buffer.toString();
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String filePath = "";
//                    if (index < splitLine) {
                    if (index < 3000) {
                        filePath = directoryPath + File.separator + "train_data_8000.txt";
                    } else {
                        filePath = directoryPath + File.separator + "test_data_8000.txt";
                    }
                    File targetFile = new File(filePath);
                    FileUtil.writeFormatTrainDataToTargetFile(targetFile, trainDataRecordList);
                    buffer.replace(start, buffer.length(), "");
                    trainDataRecordList.clear();
                }
            }
            System.out.println("记录行数：" + index);
            if (trainDataRecordList.size() > 0) {
                buffer.append("test data sets");
                String directoryPath = buffer.toString();
                String filePath = directoryPath + File.separator + "test_data_8000.txt";
                File targetFile = new File(filePath);
                FileUtil.writeFormatTrainDataToTargetFile(targetFile, trainDataRecordList);
                trainDataRecordList.clear();
            }
            buffer = null;
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
      * @date 2018/7/25 下午10:07
      * @author sunweisong
      */
    private static int getRecordRowNumberFromCSVFile(String csvFilePath) {
        CsvReader reader = null;
        int index = 0;
        try {
            reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            while (reader.readRecord()) {
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return index;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/25 下午4:19
      * @author sunweisong
      */
    private static void formatAndSaveTrainData(String classFilePath
            , String targetFilePath) {
        CsvReader reader = null;
        try {
            reader = new CsvReader(classFilePath, ',', Charset.forName("UTF-8"));
            File targetFile = new File(targetFilePath);
            List<String> trainDataRecordList = new ArrayList<>();
            int index = 0;
            StringBuffer recordBuffer = new StringBuffer();
            while(reader.readRecord()) {
                index++;
                if (index == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                int length = rowContents.length;
                String labelString = rowContents[length - 1];
                int label = Integer.parseInt(labelString);
                recordBuffer.append(label);
                for (int i = 1; i < length; i++) {
                    recordBuffer.append(" " + i + ":" + rowContents[i]);
                }
                trainDataRecordList.add(recordBuffer.toString());
                if (trainDataRecordList.size() % 100 == 0) {
                    FileUtil.writeFormatTrainDataToTargetFile(targetFile, trainDataRecordList);
                    trainDataRecordList.clear();
                }
                if (index == 4079) {
                    break;
                }
            }
            if (trainDataRecordList.size() > 0) {
                FileUtil.writeFormatTrainDataToTargetFile(targetFile, trainDataRecordList);
                trainDataRecordList.clear();
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
      * @date 2018/7/25 下午2:55
      * @author sunweisong
      */
    private static void divideClassDataSetT(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis");
        String sourceFilePath = buffer.toString() + File.separator + "pc_class_matrix.csv";
        File sourceFile = new File(sourceFilePath);
        if (sourceFile.exists()) {
            CsvReader reader = null;
            try {
                reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
                String targetFilePath0 = buffer.toString() + File.separator + "pc_class_0_matrix.csv";
                String targetFilePath1 = buffer.toString() + File.separator + "pc_class_1_matrix.csv";
                File targetFile0 = new File(targetFilePath0);
                File targetFile1 = new File(targetFilePath1);
                List<String[]> contentBeLabeled0List = new ArrayList<>();
                List<String[]> contentBeLabeled1List = new ArrayList<>();
                int index = 0;
                while(reader.readRecord()) {
                    index++;
                    if (index == 1) {
                        String[] headerArray = reader.getValues();
                        FileUtil.writeMatrixToCSVFile(targetFile0
                                , targetFile1, headerArray, null);
                        continue;
                    }
                    String[] rowContents = reader.getValues();
                    int length = rowContents.length;
                    String labelString = rowContents[length - 1];
                    int label = Integer.parseInt(labelString);
                    if (label == 0) {
                        contentBeLabeled0List.add(rowContents);
                    } else {
                        contentBeLabeled1List.add(rowContents);
                    }
                    if (contentBeLabeled0List.size() % 100 == 0) {
                        FileUtil.writeMatrixToCSVFile(targetFile0
                                , null, null, contentBeLabeled0List);
                        contentBeLabeled0List.clear();
                    }
                    if (contentBeLabeled1List.size() % 100 == 0) {
                        FileUtil.writeMatrixToCSVFile(null
                                , targetFile1, null, contentBeLabeled1List);
                        contentBeLabeled1List.clear();
                    }
                }
                if (contentBeLabeled0List.size() > 0) {
                    FileUtil.writeMatrixToCSVFile(targetFile0
                            , null, null, contentBeLabeled0List);
                    contentBeLabeled0List.clear();
                    contentBeLabeled0List = null;
                }
                if (contentBeLabeled1List.size() > 0) {
                    FileUtil.writeMatrixToCSVFile(null
                            , targetFile1, null, contentBeLabeled1List);
                    contentBeLabeled1List.clear();
                    contentBeLabeled1List = null;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        }
        buffer = null;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/25 下午3:36
      * @author sunweisong
      */
    private static void constructPlagiarismMatrix(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis");
        String sourceFilePath = buffer.toString() + File.separator + "sim_matrix.csv";
        File sourceFile = new File(sourceFilePath);
        if (sourceFile.exists()) {
            CsvReader reader = null;
            try {
                reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
                String targetFilePath = buffer.toString() + File.separator + "sim_matrix_with_label.csv";
                File targetFile = new File(targetFilePath);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                List<String[]> contentsList = new ArrayList<>();
                List<String[]> contentBeLabeledList = null;
                int index = 0;
                while(reader.readRecord()) {
                    index++;
                    if (index == 1) {
                        String[] rowContents = reader.getValues();
                        int length = rowContents.length;
                        String[] headerArray = new String[length + 1];
                        for (int count = 0; count < length; count++) {
                            headerArray[count] = rowContents[count];
                        }
                        headerArray[length] = "label";
                        FileUtil.writeMatrixToCSVFile(targetFile
                                , headerArray, contentBeLabeledList, true);
                        continue;
                    }
                    contentsList.add(reader.getValues());
                    if (contentsList.size() % 100 == 0) {
                        contentBeLabeledList= labelingRecord(contentsList);
                        FileUtil.writeMatrixToCSVFile(targetFile
                                , null, contentBeLabeledList, true);
                        contentsList.clear();
                    }
                }
                if (contentsList.size() > 0) {
                    contentBeLabeledList= labelingRecord(contentsList);
                    FileUtil.writeMatrixToCSVFile(targetFile
                            , null, contentBeLabeledList, true);
                    contentsList.clear();
                    contentsList = null;
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

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/19 下午5:02
      * @author sunweisong
      */
    private static List<String[]> labelingRecord(List<String[]> contentsList) {
        List<String[]> contentBeLabeledList = new ArrayList<>(contentsList.size());
        for (String[] contents : contentsList) {
            String cidString = contents[0];
            int leftIndex = cidString.indexOf("<");
            int rightIndex = cidString.indexOf(">");
            cidString = cidString.substring(leftIndex + 1, rightIndex);
            String[] cidArray = cidString.split(",");
            int cid1 = Integer.parseInt(cidArray[0].trim());
            int cid2 = Integer.parseInt(cidArray[1].trim());
            PCLabelModel pcLabelModel =  pcLabelModelDao.queryByCID(cid1, cid2);
            int length = contents.length;
            String[] contentBeLabeled = new String[length + 1];
            for (int index = 0; index < length; index++) {
                contentBeLabeled[index] = contents[index];
            }
            if (pcLabelModel == null) {
                contentBeLabeled[length] = "0";
            } else {
                contentBeLabeled[length] = "1";
            }
            contentBeLabeledList.add(contentBeLabeled);
        }
        return contentBeLabeledList;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/19 下午2:11
      * @author sunweisong
      */
    private static void savePlagiarismContestantModelList(List<PCLabelModel> modelList
            , String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis/cid_pair_with_label_1_matrix.csv");
        String filePath = buffer.toString();
        File targetFile = new File(filePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        List<String[]> contents = new ArrayList<>(modelList.size());
        String[] headArray = {"CID1", "CID2", "Label"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray
                , null, true);
        for (PCLabelModel model : modelList) {
            int cid1 = model.getCid1();
            int cid2 = model.getCid2();
            int label = model.getLabel();
            contents.add(new String[]{cid1 + "", cid2 + "", label + ""});
        }
        FileUtil.writeMatrixToCSVFile(targetFile, null
                , contents, true);
        targetFile = null;
        buffer = null;
    }

    /**
     * Read excel sheet and write a line recode to a object.
     * @param file
     * @return
     */
    private static List<PCLabelModel> readExcelToObject(File file) {
        Workbook wb = null;
        List<PCLabelModel> modelList = null;
        try {
            wb = WorkbookFactory.create(file);
            modelList = readExcel(wb, 0
                    , 1, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/19 下午2:12
      * @author sunweisong
      */
    private static List<PCLabelModel> readExcel(Workbook wb, int sheetIndex,
                                                             int startReadLine, int tailLine) {
        List<PCLabelModel> modelList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row firstRow = sheet.getRow(0);
        int cellNumber = firstRow.getLastCellNum();
        int[] cidArray = new int[cellNumber - 1];
        for (int index = 1; index < cellNumber; index++) {
            Cell cell = firstRow.getCell(index);
            if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                cidArray[index - 1] = (int)cell.getNumericCellValue();
            } else {
                String cellValue = cell.getStringCellValue();
                cidArray[index - 1] = Integer.parseInt(cellValue);
            }
        }
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            int cid1 = 0;
            if (firstCell.getCellType() == CELL_TYPE_NUMERIC) {
                cid1 = (int)firstCell.getNumericCellValue();
            } else {
                break;
            }
            for (int j = 1; j < cellNumber; j++) {
                Cell cell = row.getCell(j);
                int cid2 = cidArray[j - 1];
                int label = 0;
                if (cell.getCellType() == CELL_TYPE_BLANK) {
                    break;
                }
                if (cell.getCellType() == CELL_TYPE_NUMERIC) {
                    label = (int)cell.getNumericCellValue();
                }
                if (label != 1) {
                    continue;
                }
                PCLabelModel model = new PCLabelModel(cid1, cid2, label);
                modelList.add(model);
            }
        }
        return modelList;
    }
    
    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/19 下午1:37
      * @author sunweisong
      */
    private static List<PCLabelModel> statisticAllPlagiarism(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis/Datalog_human_label_matrix_0808.xlsx");
        String filePath = buffer.toString();
        File excelFile = new File(filePath);
        List<PCLabelModel> modelList = null;
        if (excelFile.exists()) {
            modelList = readExcelToObject(excelFile);
        }
        buffer = null;
        excelFile = null;
        return modelList;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/11 下午3:53
      * @author sunweisong
      */
    private static int[] getAllMUTIDFromDatabase() {
        List<Integer> midList = mutModelDao.getAllMUTIDList();
        int[] midArray = new int[midList.size()];
        int index = 0;
        for (int mid : midList) {
            midArray[index] = mid;
            index++;
        }
        return midArray;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/11 下午3:13
      * @author sunweisong
      */
    private static void constructSimilarityMatrix(List<Integer> cidList
            , int[] midArray, String rootPath, int category) {
        int cidListSize = cidList.size();
        List<SimValueMatrixModel> simValueMatrixModelList = new ArrayList<>();
        int count = 0;
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        writeSimValueToCSVFile(midArray, null, rootPath, category);
        for (int index1 = 0; index1 < cidListSize; index1++) {
            int cid1 = cidList.get(index1);
//            if (exceptCIDSet.contains(cid1)) {
//                continue;
//            }
            for (int index2 = index1 + 1; index2 < cidListSize; index2++) {
                int cid2 = cidList.get(index2);
//                if (exceptCIDSet.contains(cid2)) {
//                    continue;
//                }
                List<SimValueModel> simValueModelList = simValueModelDao.searchSimValueByParameterByCategory(cid1
                        , cid2, category);
                if (simValueModelList.size() == 0) {
                    // cid1与cid2没有相似的方法
                    continue;
                }
                SimValueMatrixModel simValueMatrixModel = new SimValueMatrixModel(cid1, cid2);
                int midNumber = midArray.length;
                double[] simValueArray = new double[midNumber];
                for (int index3 = 0; index3 < midNumber; index3++) {
                    int mid = midArray[index3];
                    double simValue = 0;
                    for (SimValueModel model : simValueModelList) {
                        if (model.getMid() == mid) {
                            simValue = model.getSimValue();
                        }
                    }
                    simValueArray[index3] = simValue;
                }
                simValueMatrixModel.setSimValueArray(simValueArray);
                simValueMatrixModelList.add(simValueMatrixModel);
                if (simValueMatrixModelList.size() % 100 == 0) {
                    count++;
                    System.out.println("第" + count + "组");
                    final List<SimValueMatrixModel> list = new ArrayList<>(simValueMatrixModelList);
                    singleThreadExecutor.execute(new Runnable() {
                        public void run() {
                            writeSimValueToCSVFile(midArray, list, rootPath, category);
                            list.clear();
                        }
                    });
                    simValueMatrixModelList.clear();
                }
            }
        }
        if (simValueMatrixModelList.size() > 0) {
            writeSimValueToCSVFile(midArray, simValueMatrixModelList, rootPath, category);
        }
        System.out.println("已经开启所有的子线程");
        singleThreadExecutor.shutdown();
        System.out.println("shutdown()：启动一次顺序关闭，执行以前提交的任务，但不接受新任务。");
        while(true){
            if(singleThreadExecutor.isTerminated()){
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        singleThreadExecutor = null;
        simValueMatrixModelList = null;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/7/18 上午10:00
      * @author sunweisong
      */
    private static void writeSimValueToCSVFile(int[] midArray
            , List<SimValueMatrixModel> simValueMatrixModelList
            , String rootPath, int category) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis");
        if (category == 0) {
            buffer.append(File.separator + "sim_matrix_ratio.csv");
        }
        if (category == 1) {
            buffer.append(File.separator + "sim_matrix_partial_ratio.csv");
        }
        String targetFilePath = buffer.toString();
        File targetFile = new File(targetFilePath);
        FileUtil.writeSimValueMatrixToTargetFile(midArray, simValueMatrixModelList
                , targetFile);
        buffer = null;
    }

    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/7/11 下午3:12
      * @author sunweisong
      */
    private static List<Integer> getAllContestantIDFromIDDirectory(String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        buffer.append(File.separator + "666/analysis/Datalog/sim_analysis/tfs_classified_by_cid");
        File directory = new File(buffer.toString());
        List<Integer> contestantIDList = null;
        if (directory.exists()) {
            File[] subDirectoryArray = directory.listFiles();
            if (subDirectoryArray != null) {
                contestantIDList = new ArrayList<>();
                for (File subDirectory : subDirectoryArray) {
                    if (subDirectory.isFile()) {
                        continue;
                    }
                    String fileName = subDirectory.getName();
                    contestantIDList.add(Integer.parseInt(fileName));
                }
            }
        }
        buffer = null;
        return contestantIDList;
    }


    
}
