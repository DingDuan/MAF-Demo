//package com.tcsa.analysis;
//
//import com.tcsa.service.libsvm.SVMPredict;
//import com.tcsa.service.libsvm.SVMScale;
//import com.tcsa.service.libsvm.SVMTrain;
//
//import java.io.File;
//import java.io.IOException;
//
///**
//  *
//  * @param
//  * @return
//  * @throws
//  * @date 2018/7/25 下午5:49
//  * @author sunweisong
//  */
//public class SVMLearner {
//
//    /**
//      *
//      * @param
//      * @return
//      * @throws
//      * @date 2018/7/25 下午9:15
//      * @author sunweisong
//      */
//    public static void train(String rootPath) {
//        StringBuffer buffer = new StringBuffer(rootPath);
//        buffer.append("/666/analysis/Datalog/sim_analysis/svm try data");
//        String baseDirectoryPath = buffer.toString();
//        String trainDataFilePath = baseDirectoryPath + File.separator + "train data sets"
//                + File.separator + "train_data_8000.txt";
//        File trainDataFile = new File(trainDataFilePath);
//        String modelOutputFilePath = baseDirectoryPath + File.separator + "model_output_8000.txt";
//        if (trainDataFile.exists()) {
//
//            /**
//             *  trainDataFilePath : 存放SVM训练模型用的数据的路径
//             *  modelOutputFile : 练出来的模型的路径
//             */
////            String[] train_args = {trainDataFilePath, modelOutputFilePath};
////            long startTime = System.currentTimeMillis();
////            System.out.println("训练开始时间: " + startTime);
////            SVMTrain svm_train = new SVMTrain();
////            try {
////                svm_train.run(train_args); //训练
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            long endTime = System.currentTimeMillis();
////            System.out.println("训练结束时间: " + endTime);
////            System.out.println("耗时: "+(endTime - startTime) + "ms");
//
//
//            String testDataFilePath = baseDirectoryPath + File.separator + "test data sets"
//                    + File.separator + "test_data_8000.txt";
//            String resultOutputFilePath = baseDirectoryPath + File.separator + "test_result_output_8000.txt";
//            predict(testDataFilePath, modelOutputFilePath, resultOutputFilePath);
//        }
//    }
//
//    /**
//      *
//      * @param testDataFilePath:测试数据文件路径
//      * @param modelFilePath:训练输出模型存放的文件路径
//      * @param resultOutputFilPath:预测结果文件存放路径,也可以是目录
//      * @throws
//      * @date 2018/7/25 下午9:15
//      * @author sunweisong
//      */
//    public static void predict(String testDataFilePath
//            , String modelFilePath, String resultOutputFilPath) {
//        File testDataFile = new File(testDataFilePath);
//        if (!testDataFile.exists()) {
//            System.err.println("测试数据文件不存在！");
//            return;
//        }
//        File modelFile = new File(modelFilePath);
//        if (!modelFile.exists()) {
//            System.err.println("模型文件不存在！");
//            return;
//        }
//        File resultOutputFile = new File(resultOutputFilPath);
//        if (resultOutputFile.isDirectory()) {
//            if (!resultOutputFile.exists()) {
//                System.err.println("结果输出目录不存在！");
//                return;
//            }
//        }
//        /**
//         *  testDataFilePath : 这个是存放测试数据
//         *  modelOutputFile : 调用的是训练以后的模型
//         *  testResultOutputFile : 生成的结果的文件的路径
//         */
//        String[] predict_args = {testDataFilePath, modelFilePath, resultOutputFilPath };
//        SVMPredict svm_predict = new SVMPredict();
//        try {
//            svm_predict.main(predict_args);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//      *
//      * @param dataFilePath:数据集存放文件
//      * @param lower:缩放下界
//      * @param upper:缩放上界
//      * @param scaleParaSaveFilePath:缩放操作参数存储路径
//      * @throws
//      *
//      * @date 2018/7/26 下午3:43
//      * @author sunweisong
//      */
//    public static void scale(int lower, int upper
//            , String scaleParaSaveFilePath, String dataFilePath) {
//        String lower_arg = "-l" + lower;
//        String upper_arg = "-u" + upper;
//        String save_file_arg = "-s" + scaleParaSaveFilePath;
//        String[] scale_args = {"-l", lower + "", "-u", upper + "", "-s", scaleParaSaveFilePath, dataFilePath};
//        try {
//            SVMScale.main(scale_args);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
