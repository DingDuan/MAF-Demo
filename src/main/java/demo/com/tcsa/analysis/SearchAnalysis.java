package demo.com.tcsa.analysis;

import demo.dao.SimValueModelDao;
import demo.dao.TFModelDao;
import demo.com.tcsa.daoImpl.SimValueModelDaoImpl;
//import demo.com.tcsa.daoImpl.TFModelDaoImpl;
import demo.com.tcsa.model.*;
import demo.com.tcsa.util.FileUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

/**
  * @Date 2018/8/8 下午12:01
  */
public class SearchAnalysis {

    private static final String targetFilePath = "/Users/sunweisong/Desktop/cid_pair_by_condition/more_90_label_0_min_info.csv";

    private static SimValueModelDao simValueModelDao = SimValueModelDaoImpl.getInstance();
//    private static TFModelDao tfModelDao = TFModelDaoImpl.getInstance();
    public static void analyze() {
        String filePath = "/Users/sunweisong/Desktop/cid_pair_by_condition/more_90_label_0.xlsx";
        File excelFile = new File(filePath);
        List<CIDPairModel> modelList = null;
        if (excelFile.exists()) {
            modelList = readExcelToObject(excelFile);
        }
        if (modelList != null) {
//            searchTFByCIDPairModel(modelList);

            searchTFSizeInfoByCIDPairModel(modelList);
        }
        excelFile = null;
    }

    private static void searchTFSizeInfoByCIDPairModel(List<CIDPairModel> modelList) {
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        String[] headArray = {"<CID1, CID2>", "MID", "MinLength", "MinStateNum"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
        List<TFMinInfo> tfMinInfoList = new ArrayList<>();
        for (CIDPairModel model : modelList) {
            String cid1String = model.getCID1();
            int cid1 = Integer.parseInt(cid1String);
            String cid2String = model.getCID2();
            int cid2 = Integer.parseInt(cid2String);
            List<SimValueModel> simValueModelList = simValueModelDao.searchSimValueByParameter(cid1, cid2
                    ,90, 100);
            System.out.println(simValueModelList.size());
            if (simValueModelList != null) {
                double maxSimValue = 0;
                // search max simValue
                for (SimValueModel simValueModel : simValueModelList) {
                    if (simValueModel.getSimValue() > maxSimValue) {
                        maxSimValue = simValueModel.getSimValue();
                    }
                }
                for (SimValueModel simValueModel : simValueModelList) {
                    if (simValueModel.getSimValue() != maxSimValue) {
                        continue;
                    }
                    int mid = simValueModel.getMid();
//                    List<TFModel> tfModelList = tfModelDao.searchTFModelListByParameter(cid1
//                            , cid2, mid);
//                    if (tfModelList.size() == 0) {
//                        continue;
//                    }
//                    int minStateNum = tfModelList.get(0).getStateNum();
//                    int minTFLength = tfModelList.get(0).getLength();
                    // search min statement number
//                    for (int index = 1; index < tfModelList.size(); index++) {
//                        TFModel tfModel = tfModelList.get(index);
//                        if (tfModel.getStateNum() < minStateNum) {
//                            minStateNum = tfModel.getStateNum();
//                            minTFLength = tfModel.getLength();
//                        }
//                    }
//                    tfMinInfoList.add(new TFMinInfo(cid1, cid2, mid, minTFLength, minStateNum));
                    break;
                }
            }
        }
        if (tfMinInfoList.size() > 0) {
            List<String[]> records = new ArrayList<>(tfMinInfoList.size());
            for (TFMinInfo tfMinInfo : tfMinInfoList) {
                String cidPair = "<" + tfMinInfo.getCid1() + ", " + tfMinInfo.getCid2() + ">";
                records.add(new String[]{cidPair, tfMinInfo.getMid() + ""
                        , tfMinInfo.getMinTFLength() + "", tfMinInfo.getMinTFStateNum()+ ""});
            }
            FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
        }
    }

//    private static void searchTFByCIDPairModel(List<CIDPairModel> modelList) {
//        File targetFile = new File(targetFilePath);
//        if (targetFile.exists()) {
//            targetFile.delete();
//        }
//        for (CIDPairModel model : modelList) {
//            String cid1String = model.getCID1();
//            int cid1 = Integer.parseInt(cid1String);
//            String cid2String = model.getCID2();
//            int cid2 = Integer.parseInt(cid2String);
//            List<SimValueModel> simValueModelList = simValueModelDao.searchSimValueByParameter(cid1, cid2
//                    ,90, 99);
//            System.out.println(simValueModelList.size());
//            if (simValueModelList != null) {
//                List<SVTFModel> svtfModelList = new ArrayList<>(simValueModelList.size());
//                for (SimValueModel simValueModel : simValueModelList) {
//                    double mid = simValueModel.getMid();
//                    SVTFModel svtfModel = new SVTFModel(simValueModel.getSimValue(), mid);
//                    List<TFModel> tfModelList = tfModelDao.searchTFModelListByParameter(cid1
//                            , cid2, mid);
//                    if (tfModelList.size() == 0) {
//                        continue;
//                    }
//                    svtfModel.setTfModelList(tfModelList);
//                    svtfModelList.add(svtfModel);
//                }
//                FileUtil.writeTFsByCIDToTargetFile(cid1, cid2, svtfModelList, targetFile);
//            }
//        }
//    }

    /**
     * Read excel sheet and write a line recode to a object.
     * @param file
     * @return
     */
    private static List<CIDPairModel> readExcelToObject(File file) {
        Workbook wb = null;
        List<CIDPairModel> modelList = null;
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
    private static List<CIDPairModel> readExcel(Workbook wb, int sheetIndex,
                                           int startReadLine, int tailLine) {
        List<CIDPairModel> modelList = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            Cell firstCell = row.getCell(0);
            if (firstCell.getCellType() == CELL_TYPE_STRING) {
                String cidGroup = firstCell.getStringCellValue();
                int start = cidGroup.indexOf("<");
                int end = cidGroup.indexOf(">");
                cidGroup = cidGroup.substring(start + 1, end).trim();
                String[] cidArray = cidGroup.split(",");
                CIDPairModel model = new CIDPairModel(cidArray[0].trim(), cidArray[1].trim());
                modelList.add(model);
            }
        }
        return modelList;
    }
}
