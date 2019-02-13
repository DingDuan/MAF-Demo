package com.tcsa.analysis;

import com.csvreader.CsvReader;
import com.tcsa.model.DiffModel;
import com.tcsa.model.PCLabelModel;
import com.tcsa.util.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

public class DifflibAnalysis {

    private final static String rootPath = "/Users/sunweisong/Desktop/all_data";

    private final static String sourceDataRootPath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/junit/Datalog_clean";

    private final static String[] headArray = {"CID1", "FileName", "CID2", "FileName", "SimValue", "IsMax"};

    private final static String diffResultFileName = "diff_result.csv";

    /**
     * 程序分析入口
     * @param
     * @return
     * @throws
     * @date 2018/8/5 下午12:29
     */
    public static void analysis() {
//        File rootDirectory = new File(rootPath);
//        if (rootDirectory.exists()) {
//            traverseRootDirectory(rootDirectory);
//        }

        // mapping cid to new cid
        String difflibRootPath = "/Users/sunweisong/Desktop/comparison_tool_difflib";
        String statisticResultRootPath = difflibRootPath + File.separator + "experiment/comparison_by_file/statistic_by_threshold";
        String cidMapFilePath = difflibRootPath + File.separator + "all_cid_mapping.xlsx";
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
        String filePath = statisticResultRootPath + File.separator + "statistic_result_total.csv";
        String targetFilePath = statisticResultRootPath + File.separator + "statistic_result_total_new_cid.csv";
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
     * 遍历根目录
     * @param
     * @return
     * @throws
     * @date 2018/8/5 下午12:28
     */
    private static void traverseRootDirectory(File rootDirectory) {
        File[] groupDirectoryArray = rootDirectory.listFiles();
        for (File groupDirectory : groupDirectoryArray) {
            String groupName = groupDirectory.getName();
            if ("1-10".equals(groupName)) {
                // 1-10的选手
                traverseGroupDirectory(groupDirectory);
            }
        }
    }

    /**
     * 遍历分组目录
     * @param
     * @return
     * @throws
     * @date 2018/8/5 下午12:31
     */
    private static void traverseGroupDirectory(File groupDirectory) {
        File[] cid1DirectoryArray = groupDirectory.listFiles();
        //遍历CID1目录
        for (File cid1Directory : cid1DirectoryArray) {
            if (!cid1Directory.isDirectory()) {
                continue;
            }
            String cid1 = cid1Directory.getName();
//            if (!"58".equals(cid1)) {
//                continue;
//            }
            File[] cid2DirectoryArray = cid1Directory.listFiles();
            if (cid2DirectoryArray == null) {
                System.out.println("不存在CID2");
                continue;
            }
            //遍历CDI2目录
            for (File cid2Directory : cid2DirectoryArray) {
                if (!cid2Directory.isDirectory()) {
                    continue;
                }
                String cid2 = cid2Directory.getName();
                File[] diffFileArray = cid2Directory.listFiles();
                if (diffFileArray == null) {
                    System.out.println("不存在diff文件");
                    continue;
                }
                //遍历CID1,CID2的所有diff文件
                int pointIndex = 0;
                StringBuffer buffer = null;
                List<DiffModel> diffModelList = new ArrayList<>();
                for (File diffFile : diffFileArray) {
                    String diffFileName = diffFile.getName();
                    if ("diff_result.csv".equals(diffFileName)) {
                        diffFile.delete();
                        continue;
                    }
                    String[] file1_fil2_array = diffFileName.split(" vs ");
                    String cid1FileName = file1_fil2_array[0].trim();
                    pointIndex = cid1FileName.indexOf(".");
                    cid1FileName = cid1FileName.substring(pointIndex + 1);
                    buffer = new StringBuffer(sourceDataRootPath + File.separator
                            + cid1 + File.separator + cid1FileName + ".java");
                    File targetFile = new File(buffer.toString());
                    System.out.println(targetFile);
                    // 读取cid1文件中的代码行数
                    int cid1FileLineNumber = 0;
                    if (targetFile.exists()) {
                        cid1FileLineNumber = FileUtil.calculateFileLineFromTargetFile(targetFile);
                    } else {
                        try {
                            throw new IOException(cid1 + "号选手文件不存在！");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    buffer.setLength(0);
                    String cid2FileName = file1_fil2_array[1].trim();
                    pointIndex = cid2FileName.indexOf(".");
                    cid2FileName = cid2FileName.substring(pointIndex + 1);
                    pointIndex = cid2FileName.indexOf(".");
                    cid2FileName = cid2FileName.substring(0, pointIndex);
                    buffer.append(sourceDataRootPath + File.separator
                            + cid2 + File.separator + cid2FileName + ".java");
                    // 读取cid2文件中的代码行数
                    int cid2FileLineNumber = 0;
                    targetFile = new File(buffer.toString());
                    if (targetFile.exists()) {
                        cid2FileLineNumber = FileUtil.calculateFileLineFromTargetFile(targetFile);
                    } else {
                        try {
                            throw new IOException(cid2 + "号选手文件不存在！");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // 读取cid1, cid2的Diff文件的行数
                    int changeLineNumber = FileUtil.calculateDiffLineFromDiffFile(diffFile);
                    double simValue = calculateSimValueByDiff(cid1FileLineNumber, cid2FileLineNumber
                            , changeLineNumber);
                    diffModelList.add(new DiffModel(Integer.parseInt(cid1), Integer.parseInt(cid2)
                            , cid1FileName, cid2FileName, simValue));
                    buffer.setLength(0);
                }
                buffer = null;
                // setIsMax
                setIsMaxForAllDiffModel(diffModelList);
                List<String[]> records = new ArrayList<>(diffModelList.size());
                for (DiffModel diffModel : diffModelList) {
                    records.add(new String[] {diffModel.getCID1() + ""
                            , diffModel.getCid1FileName()
                            , diffModel.getCID2() + ""
                            , diffModel.getCid2FileName()
                            , diffModel.getSimValue() + ""
                            , diffModel.getIsMax() + ""});
                }
                File csvFile = new File(cid2Directory + File.separator + diffResultFileName);
                if (csvFile.exists()) {
                    csvFile.delete();
                }
                FileUtil.writeMatrixToCSVFile(csvFile, headArray, null, true);
                FileUtil.writeMatrixToCSVFile(csvFile, null, records, true);
                csvFile = null;
                records = null;
                diffModelList = null;
            }
        }
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/8/5 下午2:01
     */
    public static void setIsMaxForAllDiffModel(List<DiffModel> diffModelList) {
        double maxSimValue = 0;
        for (DiffModel diffModel : diffModelList) {
            if (diffModel.getSimValue() > maxSimValue) {
                maxSimValue = diffModel.getSimValue();
            }
        }
        for (DiffModel diffModel : diffModelList) {
            if (diffModel.getSimValue() == maxSimValue) {
                diffModel.setIsMax(1);
                break;
            }
        }
    }

    /**
     * 计算两个similarity
     * @param
     * @return
     * @throws
     * @date 2018/8/5 下午1:44
     */
    private static double calculateSimValueByDiff(int cid1FileLineNumber
            , int cid2FileLineNumber, int changeLineNumber) {
        double simValueBasedCID1 = 0;
        if (cid1FileLineNumber != 0) {
            int y = cid1FileLineNumber;
            if (changeLineNumber < cid1FileLineNumber) {
                y = changeLineNumber;
            }
            simValueBasedCID1 = 1 - (double) y / cid1FileLineNumber;
        }
        double simValueBasedCID2 = 0;
        if (cid2FileLineNumber != 0) {
            int y = cid2FileLineNumber;
            if (changeLineNumber < cid2FileLineNumber) {
                y = changeLineNumber;
            }
            simValueBasedCID2 = 1- (double) y / cid2FileLineNumber;
        }
        if (simValueBasedCID1 > simValueBasedCID2) {
            return simValueBasedCID1;
        }
        return simValueBasedCID2;
    }
}
