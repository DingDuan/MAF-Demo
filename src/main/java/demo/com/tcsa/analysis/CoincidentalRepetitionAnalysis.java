package demo.com.tcsa.analysis;

import com.csvreader.CsvReader;
import demo.com.tcsa.model.PCLabelModel;
import demo.com.tcsa.util.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

/**
  *
  */
public class CoincidentalRepetitionAnalysis {



    public static void analyze() {

        // 统计相似度 90<= maxSimValue <= 99，并且被标记为未抄袭的选手的前 5 个相似度值
        String rootPath = "/Users/sunweisong/Desktop/our_tool_tcsa/CST2017/666/analysis/Datalog/sim_analysis";

        // 获取条件CID_Pair
        String conditionFilePath = "/Users/sunweisong/Desktop/cid_pair_by_condition/more_or_equal_90_less_or_equal_100_label_0.xlsx";
        Set<String> cidPairSet = extractCIDPairFromFile(conditionFilePath);
//        System.out.println(cidPairSet.size());
        statisticByCidPair(cidPairSet, rootPath);
    }

    private static void statisticByCidPair(Set<String> cidPairSet, String rootPath) {
        StringBuffer buffer = new StringBuffer(rootPath);
        String matrixFilePath = rootPath + File.separator + "sim_matrix_with_label.csv";
        File sourceFile = new File(matrixFilePath);
        String targetFilePath = buffer.toString() + File.separator + "matrix_label_0_order_by_simvalue_include_100.csv";
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        Map<String, int[]> recordMap = new HashMap<>();
        if (sourceFile.exists()) {
            CsvReader reader = null;
            try {
                reader = new CsvReader(matrixFilePath, ',', Charset.forName("UTF-8"));
                int index = 0;
                while(reader.readRecord()) {
                    index++;
                    if (index == 1) {
                        continue;
                    }
                    String[] rowContents = reader.getValues();
                    String cidPair = rowContents[0].trim();
                    if (!cidPairSet.contains(cidPair)) {
                        continue;
                    }
                    int length = rowContents.length;
                    int[] simValueArray = new int[length - 2];
                    for (int i = 1; i < length - 1; i++) {
                        simValueArray[i - 1] = Integer.parseInt(rowContents[i]);
                    }
                    // 升序排序
                    Arrays.sort(simValueArray);
                    recordMap.put(cidPair, simValueArray);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.close();
            }
        }
        if (recordMap.size() > 0) {
            List<String[]> rowRecords = new ArrayList<>(recordMap.size());
            for (Map.Entry<String, int[]> entry : recordMap.entrySet()) {
                String cidPair = entry.getKey();
                int[] simValueArray = entry.getValue();
                int simValueArrayLength = simValueArray.length;
                String[] record = new String[simValueArrayLength + 1];
                record[0] = cidPair;
                int index = 1;
                for (int i = simValueArrayLength - 1; i >= 0; i--) {
                    record[index] = simValueArray[i] + "";
                    index++;
                }
                rowRecords.add(record);
            }
            FileUtil.writeMatrixToCSVFile(targetFile, null, rowRecords, false);
        }
    }


    private static Set<String> extractCIDPairFromFile(String conditionFilePath) {
        File excelFile = new File(conditionFilePath);
        Set<String> cidPairSet = null;
        if (excelFile.exists()) {
            cidPairSet = readExcelToObject(excelFile);
        }
        excelFile = null;
        return cidPairSet;
    }

    private static Set<String> readExcelToObject(File file) {
        Workbook wb = null;
        Set<String> cidPairSet = null;
        try {
            wb = WorkbookFactory.create(file);
            cidPairSet = readExcel(wb, 0
                    , 1, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cidPairSet;
    }

    private static Set<String> readExcel(Workbook wb, int sheetIndex,
                                                int startReadLine, int tailLine) {
        Set<String> cidPairSet = new HashSet<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            if (firstCell.getCellType() == CELL_TYPE_STRING) {
                String temp = firstCell.getStringCellValue().trim();
                cidPairSet.add(temp);
            } else {
                System.err.println("内容有错！");
                break;
            }
        }
        return cidPairSet;
    }


}
