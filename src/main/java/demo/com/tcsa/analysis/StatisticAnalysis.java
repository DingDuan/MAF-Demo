package demo.com.tcsa.analysis;

import com.csvreader.CsvReader;
import demo.dao.SimValueModelDao;
import demo.dao.TFModelDao;
import demo.com.tcsa.daoImpl.SimValueModelDaoImpl;
import demo.com.tcsa.daoImpl.TFModelDaoImpl;
import demo.com.tcsa.model.*;
import demo.com.tcsa.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
  * @Author sunweisong
  * @Date 2018/8/11 下午8:13
  */
public class StatisticAnalysis {

    private static TFModelDao tfModelDao = TFModelDaoImpl.getInstance();

    private static SimValueModelDao simValueModelDao = SimValueModelDaoImpl.getInstance();

    private static final HashSet<Integer> exceptCIDSet = new HashSet<Integer>() {{
        add(82); add(163); add(182); add(248);
        add(267); add(274); add(306); add(308);
        add(410); add(461); add(542); add(548);
        add(568); add(574); add(579); add(589);
    }};

    private static final List<Double> thresholdList = new ArrayList<Double>() {{
        add(0.05); add(0.1); add(0.15); add(0.2);
        add(0.25); add(0.3); add(0.35); add(0.4);
        add(0.45); add(0.50); add(0.55); add(0.60);
        add(0.65); add(0.70); add(0.75); add(0.80);
        add(0.85); add(0.90); add(0.95); add(0.96);
        add(0.97); add(0.98); add(0.99); add(1.0);
    }};

    private static final int totalLabel1Num = 4312;
    private static final int totalLabel0Num = 186959;

    private static final String rootPath = "/Users/sunweisong/Desktop/useful data for paper";

    public static void analyze() {

        // 统计所有阈值下的结果，并计算每个阈值下的Precision, recall, F1
        statisticPRFValue();


        // 统计巧合行重复的测试片段信息
//        statisticConincidentRepeatTFInfo();
       // 上一步骤CID 对的label 未标记成功，重新写个代码标记

//        String staticResultDirectoryPath_tf_difflib = rootPath + File.separator
//                + "difflib/comparison_by_tf";
//        String staticResultDirectoryPath_tf_fuzzywuzzy = rootPath + File.separator
//                + "fuzzywuzzy(our approach)/comparison_by_tf";
//        String staticResultDirectoryPath_tf_plaggie = rootPath + File.separator
//                + "plaggie/comparison_by_tf";
//        File staticResultDirectory = new File(staticResultDirectoryPath_tf_plaggie);
//        if (!staticResultDirectory.exists()) {
//            System.out.println("目录：" + staticResultDirectoryPath_tf_plaggie + "不存在！");
//            return;
//        }
//        String humanReviewStaticResultFilePath = rootPath + File.separator
//                + "human review/human_review_statistic_all.csv";
//        File humanReviewStaticResultFile = new File(humanReviewStaticResultFilePath);
//        if (!humanReviewStaticResultFile.exists()) {
//            System.out.println("文件：" + humanReviewStaticResultFilePath + "不存在！");
//            return;
//        }
//        List<PCLabelModel> cidPairWithLabelList = extractCIDPairWithLabelFromFile(humanReviewStaticResultFile);
//        int count =  0;
//        for (PCLabelModel pcLabelModel : cidPairWithLabelList) {
//            if (pcLabelModel.getLabel() == 1) {
//                count++;
////                System.out.println(pcLabelModel.toString());
//            }
//        }
//        System.out.println(count);
//        labelCIDPairsWithHumanReviewData(cidPairWithLabelList, staticResultDirectory);
//        cidPairWithLabelList = null;

    }

    private static void labelCIDPairsWithHumanReviewData(List<PCLabelModel> cidPairWithLabelList
            , File staticResultDirectory) {
        File[] staticFileArray = staticResultDirectory.listFiles();
        for (File staticFile : staticFileArray) {
            String fileName = staticFile.getName();
            if (!"tfs_information_0.96.csv".equals(fileName)) {
                continue;
            }
            String targetFilePath = staticResultDirectory
                    + File.separator + "tfs_information_0.96_with_label" + ".csv";
            File targetFile = new File(targetFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            String[] headArray = {"CID1", "CID2", "Length", "StateNum", "MaxSimValue", "Label"};
            FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);


            String sourceFilePath = staticFile.getAbsolutePath();
            CsvReader reader = null;
            List<StatisticTFRowInfo> statisticTFRowInfoModelList = new ArrayList<>();
            try {
                reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
                int index = 0;
                while(reader.readRecord()) {
                    index++;
                    if (index == 1) {
                        continue;
                    }
                    String[] rowContents = reader.getValues();
                    int cid1 = Integer.parseInt(rowContents[0].trim());
                    int cid2 = Integer.parseInt(rowContents[1].trim());
                    int length = Integer.parseInt(rowContents[2].trim());
                    int stateNum = Integer.parseInt((rowContents[3]).trim());
                    double maxValue = Double.parseDouble(rowContents[4].trim());
                    int label = Integer.parseInt(rowContents[5].trim());
                    StatisticTFRowInfo statisticTFRowInfo = new StatisticTFRowInfo(cid1, cid2, length
                            , stateNum, maxValue, label);
                    statisticTFRowInfoModelList.add(statisticTFRowInfo);
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

            for (StatisticTFRowInfo statisticTFRowInfo : statisticTFRowInfoModelList) {
                int CID1 = statisticTFRowInfo.getCID1();
                int CID2 = statisticTFRowInfo.getCID2();
                for (PCLabelModel pcLabelModel : cidPairWithLabelList) {
                    int cid1 = pcLabelModel.getCid1();
                    int cid2 = pcLabelModel.getCid2();
                    if (CID1 == cid1 && CID2 == cid2) {
                        statisticTFRowInfo.setLabel(pcLabelModel.getLabel());
                        break;
                    }
                    if (CID1 == cid2 && CID2 == cid1) {
                        statisticTFRowInfo.setLabel(pcLabelModel.getLabel());
                        break;
                    }
                }
            }

            // {"CID1", "CID2", "Length", "StateNum", "MaxSimValue", "Label"};
            List<String[]> records = new ArrayList<>();
            for (StatisticTFRowInfo statisticTFRowInfo: statisticTFRowInfoModelList) {
                records.add(new String[] {statisticTFRowInfo.getCID1() + ""
                        , statisticTFRowInfo.getCID2() + ""
                        , statisticTFRowInfo.getLength() + ""
                        , statisticTFRowInfo.getStateNum() + ""
                        , statisticTFRowInfo.getMaxSimValue() + ""
                        , statisticTFRowInfo.getLabel() + ""});
            }
            FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
            statisticTFRowInfoModelList.clear();
            records.clear();
            records = null;
        }
    }

    private static void statisticConincidentRepeatTFInfo() {

        String staticResultDirectoryPath_tf_difflib = rootPath + File.separator
                + "difflib/comparison_by_tf";
        String staticResultDirectoryPath_tf_fuzzywuzzy = rootPath + File.separator
                + "fuzzywuzzy(our approach)/comparison_by_tf";
        String staticResultDirectoryPath_tf_plaggie = rootPath + File.separator
                + "plaggie/comparison_by_tf";
        File staticResultDirectory = new File(staticResultDirectoryPath_tf_plaggie);
        if (!staticResultDirectory.exists()) {
            System.out.println("目录：" + staticResultDirectoryPath_tf_plaggie + "不存在！");
            return;
        }
        String humanReviewStaticResultFilePath = rootPath + File.separator
                + "human review/human_review_statistic_all.csv";
        File humanReviewStaticResultFile = new File(humanReviewStaticResultFilePath);
        if (!humanReviewStaticResultFile.exists()) {
            System.out.println("文件：" + humanReviewStaticResultFilePath + "不存在！");
            return;
        }

        List<PCLabelModel> cidPairWithLabelList = extractCIDPairWithLabelFromFile(humanReviewStaticResultFile);
        traverseStaticResultDirectory(staticResultDirectory, cidPairWithLabelList, 0.96);

    }

    /**
      * 计算 Precision, Recall and F1-Measure
      * @param
      * @return
      * @throws
      * @date 2018/8/13 下午7:08
      * @author sunweisong
      */
    private static void statisticPRFValue() {

        // 过滤掉统计文件中所有 619 位选手之外的选手CID Pairs

        String staticResultDirectoryPath_file_plaggie = rootPath + File.separator
                + "plaggie/comparison_by_file";
        String staticResultDirectoryPath_tf_plaggie = rootPath + File.separator
                + "plaggie/comparison_by_tf";


        String staticResultDirectoryPath_file_difflib = rootPath + File.separator
                + "difflib/comparison_by_file";
        String staticResultDirectoryPath_tf_difflib = rootPath + File.separator
                + "difflib/comparison_by_tf";

        String staticResultDirectoryPath_file_fuzzywuzzy = rootPath + File.separator
                + "fuzzywuzzy(our approach)/comparison_by_file";
        String staticResultDirectoryPath_tf_fuzzywuzzy = rootPath + File.separator
                + "fuzzywuzzy(our approach)/comparison_by_tf";


        File staticResultDirectory = new File(staticResultDirectoryPath_tf_plaggie);
        if (!staticResultDirectory.exists()) {
            System.out.println("目录：" + staticResultDirectoryPath_tf_plaggie + "不存在！");
            return;
        }
        String humanReviewStaticResultFilePath = rootPath + File.separator
                + "human review/human_review_statistic_all.csv";
        File humanReviewStaticResultFile = new File(humanReviewStaticResultFilePath);
        if (!humanReviewStaticResultFile.exists()) {
            System.out.println("文件：" + humanReviewStaticResultFilePath + "不存在！");
            return;
        }

        List<PCLabelModel> cidPairWithLabelList = extractCIDPairWithLabelFromFile(humanReviewStaticResultFile);
//        traverseStaticResultDirectory(staticResultDirectory, cidPairWithLabelList, 0.8);
        traverseStaticResultDirectory(staticResultDirectory, cidPairWithLabelList);
    }

    private static void traverseStaticResultDirectory(File staticResultDirectory
            , List<PCLabelModel> cidPairWithLabelList, double threshold) {
        File[] staticFileArray = staticResultDirectory.listFiles();
        for (File staticFile : staticFileArray) {
            String fileName = staticFile.getName();
            if (!"all_statistic_result_2.csv".equals(fileName)) {
                continue;
            }
            String targetFilePath = staticResultDirectory
                    + File.separator + "tfs_information_" + threshold + ".csv";
            File targetFile = new File(targetFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            String[] headArray = {"CID1", "CID2", "Length", "StateNum", "MaxSimValue", "Label"};
            FileUtil.writeMatrixToCSVFile(targetFile, headArray, null, true);
            List<CIDPairStatisticModel> cidPairStatisticModelList = filterCIDPairByThreshold(staticFile
                    , threshold);
            if (cidPairStatisticModelList == null) {
                continue;
            }
            int totalLine = cidPairStatisticModelList.size();
            System.out.println("大于阈值 " + threshold + " 的选手对的数量：" + totalLine);
            labelCIDPairs(cidPairStatisticModelList, cidPairWithLabelList);
            int TP = getTPNumber(cidPairStatisticModelList);
            int FP = totalLine - TP;
            System.out.println("TP:" + TP);
            System.out.println("FP:" + FP);

            List<CIDPairTFModel> cidPairTFModelList = extractTFInforForCIDPair(cidPairStatisticModelList);
            // {"CID1", "CID2", "Length", "StateNum", "MaxSimValue", "Label"};
            List<String[]> records = new ArrayList<>();
            for (CIDPairTFModel cidPairTFModel: cidPairTFModelList) {
                TFModel tfModel = cidPairTFModel.getTfModel();
                records.add(new String[] {cidPairTFModel.getCID1() + ""
                        , cidPairTFModel.getCID2() + ""
                        , tfModel.getLength() + ""
                        , tfModel.getStateNum() + ""
                        , cidPairTFModel.getMaxSimValue() + ""
                        , cidPairTFModel.getLabel() + ""});
            }
            FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
            cidPairStatisticModelList.clear();
            cidPairStatisticModelList = null;
            records.clear();
            records = null;
        }
    }

    private static List<CIDPairTFModel> extractTFInforForCIDPair(List<CIDPairStatisticModel> cidPairStatisticModelList) {
        List<CIDPairTFModel> cidPairTFModelList = new ArrayList<>(cidPairStatisticModelList.size());
        for (CIDPairStatisticModel model : cidPairStatisticModelList) {
            int label = model.getLabel();
            int CID1 = model.getCID1();
            int CID2 = model.getCID2();
            double maxSimValue = model.getMaxSimValue();
            CIDPairTFModel cidPairTFModel = new CIDPairTFModel(CID1, CID2, maxSimValue,label);
            List<SimValueModel> simValueModelList = simValueModelDao.searchSimValueByParameter(CID1, CID2);
            int MID = simValueModelList.get(0).getMid();
            double maxSV = simValueModelList.get(0).getSimValue();
            for (int i = 1; i < simValueModelList.size(); i++) {
                SimValueModel simValueModel = simValueModelList.get(i);
                if (simValueModel.getSimValue() > maxSV) {
                    maxSV = simValueModel.getSimValue();
                    MID = simValueModel.getMid();
                }
            }
            System.out.println("<" + CID1 + "," + CID2 + "," + maxSimValue + ">");
            List<TFModel> tfModelList = tfModelDao.searchTFModelListByParameter(CID1, CID2, MID);
            int minStateNum = tfModelList.get(0).getStateNum();
            TFModel tfModelWithMinStateNum = tfModelList.get(0);
            for (int i = 1; i < tfModelList.size(); i++) {
                TFModel tfModel = tfModelList.get(i);
                if (tfModel.getStateNum() < minStateNum) {
                    minStateNum = tfModel.getStateNum();
                    tfModelWithMinStateNum = tfModel;
                }
            }
            System.out.println(tfModelWithMinStateNum.toString());
            cidPairTFModel.setTfModel(tfModelWithMinStateNum);
            cidPairTFModelList.add(cidPairTFModel);
        }
        return cidPairTFModelList;
    }

    private static int getTPNumber(List<CIDPairStatisticModel> cidPairStatisticModelList) {
        int count = 0;
        for (CIDPairStatisticModel model : cidPairStatisticModelList) {
            if (model.getLabel() == 1) {
                count++;
            }
        }
        return count;
    }

    private static void traverseStaticResultDirectory(File staticResultDirectory
            , List<PCLabelModel> cidPairWithLabelList) {
        File[] staticFileArray = staticResultDirectory.listFiles();
        for (File staticFile : staticFileArray) {
            String fileName = staticFile.getName();
            if (!"all_statistic_result_30.csv".equals(fileName)) {
                continue;
            }
            String statisticFilePath = staticResultDirectory + File.separator + "statistics.csv";
            File statisticFile = new File(statisticFilePath);
            if (statisticFile.exists()) {
                statisticFile.delete();
            }
            String[] headArray = {"Threshold"," Precision", "Recall", "F1-Measure", "TP", "FP", "TN", "FN"};
            FileUtil.writeMatrixToCSVFile(statisticFile, headArray, null, true);
            List<String[]> records = new ArrayList<>();
            for (double threshold : thresholdList) {
//                if (threshold > 0.2) {
//                    continue;
//                }
                System.out.println("阈值：" + threshold);
                long startTime = System.currentTimeMillis();
                System.out.println("开始时间 : " + startTime);
                List<CIDPairStatisticModel> cidPairStatisticModelList
                        = filterCIDPairByThreshold(staticFile, threshold);
                if (cidPairStatisticModelList == null) {
                    continue;
                }
                int totalLine = cidPairStatisticModelList.size();
                System.out.println("对比组数量：" + totalLine);
                labelCIDPairs(cidPairStatisticModelList, cidPairWithLabelList);
                int TP = saveCIDPairsAfterLabeled(staticResultDirectory.toString()
                        , cidPairStatisticModelList, threshold);
                int FP = totalLine - TP;
                int FN = totalLabel1Num - TP;
                int TN = totalLabel0Num - FP;
                double precision = (double) TP / totalLine;
                double recall = (double) TP / (TP + FN);
                double f1 = 2 * precision * recall / (precision + recall);
                System.out.println("TP:" + TP);
                System.out.println("FP:" + FP);
                System.out.println("TN:" + TN);
                System.out.println("FN:" + FN);
                System.out.println("Precision:" + precision);
                System.out.println("Recall:" + recall);
                System.out.println("F1-Measure:" + f1);
                records.add(new String[] {threshold + ""
                        , new DecimalFormat("0.000").format(precision)
                        , new DecimalFormat("0.000").format(recall)
                        , new DecimalFormat("0.000").format(f1)
                        , TP + "", FP + "", TN + "", FN + ""});
                long endTime = System.currentTimeMillis();
                System.out.println("结束时间：" + endTime);
                System.out.println("耗时：" + (endTime - startTime) + "ms");
                System.out.println("——————————————————————————————————————");
                cidPairStatisticModelList.clear();
                cidPairStatisticModelList = null;
            }
            if (records.size() > 0) {
                FileUtil.writeMatrixToCSVFile(statisticFile, null, records, true);
                records = null;
            }
        }
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/11 下午9:39
      * @author sunweisong
      */
    private static int saveCIDPairsAfterLabeled(String staticResultDirectoryPath
            , List<CIDPairStatisticModel> cidPairsAfterLabeled, double threshold) {
        int count = 0;
        StringBuffer buffer = new StringBuffer(staticResultDirectoryPath);
        buffer.append(File.separator + "statistic_result_");
        buffer.append(threshold);
        buffer.append(".csv");
        String targetFilePath = buffer.toString();
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        String[] headArray = {"CID1", "CID2", "MaxSimValue", "Label"};
        FileUtil.writeMatrixToCSVFile(targetFile, headArray
                , null, true);
        List<String[]> records = new ArrayList<>(cidPairsAfterLabeled.size());
        for (CIDPairStatisticModel model : cidPairsAfterLabeled) {
            records.add(new String[]{model.getCID1() + "", model.getCID2() + ""
                    , model.getMaxSimValue() + "", model.getLabel() + ""});
            if (model.getLabel() == 1) {
                count++;
            }
        }
        FileUtil.writeMatrixToCSVFile(targetFile, null, records, true);
        records = null;
        headArray = null;
        targetFile = null;
        buffer = null;
        return count;
    }

    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/8/11 下午9:25
      * @author sunweisong
      */
    private static void labelCIDPairs(List<CIDPairStatisticModel> cidPairStatisticModelList
            , List<PCLabelModel> cidPairWithLabelList) {
        for (CIDPairStatisticModel cidPairStatisticModel : cidPairStatisticModelList) {
            int cid1 = cidPairStatisticModel.getCID1();
            int cid2 = cidPairStatisticModel.getCID2();
            for (PCLabelModel pcLabelModel: cidPairWithLabelList) {
                if (pcLabelModel.getCid1() != cid1) {
                    continue;
                }
                if (pcLabelModel.getCid2() != cid2) {
                    continue;
                }
                cidPairStatisticModel.setLabel(pcLabelModel.getLabel());
            }
        }
    }


    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/8/11 下午9:17
     * @author sunweisong
     */
    private static List<PCLabelModel> extractCIDPairWithLabelFromFile(File humanReviewStaticResultFile) {
        String sourceFilePath = humanReviewStaticResultFile.getAbsolutePath();
        CsvReader reader = null;
        List<PCLabelModel> cidPairLabelList = new ArrayList<>();
        try {
            reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
            int index = 0;
            while(reader.readRecord()) {
                index++;
                if (index == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                int cid1 = Integer.parseInt(rowContents[0].trim());
                int cid2 = Integer.parseInt(rowContents[1].trim());
                int label = Integer.parseInt(rowContents[2].trim());
                cidPairLabelList.add(new PCLabelModel(cid1, cid2, label));
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
        return cidPairLabelList;
    }


    /**
      *
      * @param
      * @return
      * @throws
      * @date 2018/8/11 下午8:39
      * @author sunweisong
      */
    private static List<CIDPairStatisticModel> filterCIDPairByThreshold(File sourceFile
            , double threshold) {
        String sourceFilePath = sourceFile.getAbsolutePath();
        CsvReader reader = null;
        List<CIDPairStatisticModel> cidPairStatisticModelList = new ArrayList<>();
        try {
            reader = new CsvReader(sourceFilePath, ',', Charset.forName("UTF-8"));
            int index = 0;
            while(reader.readRecord()) {
                index++;
                if (index == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                int cid1 = Integer.parseInt(rowContents[0].trim());
                int cid2 = Integer.parseInt(rowContents[1].trim());
                if (exceptCIDSet.contains(cid1) || exceptCIDSet.contains(cid2)) {
                    continue;
                }
                double maxSimValue = Double.parseDouble(rowContents[2].trim());
                if (maxSimValue > 1) {
                    maxSimValue = maxSimValue / 100;
                }
                if (maxSimValue < threshold) {
                    continue;
                }
                if (cid1 < cid2) {
                    cidPairStatisticModelList.add(new CIDPairStatisticModel(cid1, cid2, maxSimValue));
                } else {
                    cidPairStatisticModelList.add(new CIDPairStatisticModel(cid2, cid1, maxSimValue));
                }
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
        if (cidPairStatisticModelList.size() == 0) {
            cidPairStatisticModelList.clear();
            cidPairStatisticModelList = null;
            return null;
        }
        return cidPairStatisticModelList;
    }
}
