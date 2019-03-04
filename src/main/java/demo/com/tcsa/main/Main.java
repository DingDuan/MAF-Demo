package demo.com.tcsa.main;

import com.csvreader.CsvReader;
import demo.com.tcsa.analysis.*;
import demo.com.tcsa.model.ContestantTFModel;
import demo.dao.MUTModelDao;
//import demo.com.tcsa.service.plaggie.Plaggie;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class Main {

//    public static final String rootPath = "/Users/sunweisong/Desktop/our_tool_tcsa/CST2017";
//    public static final String rootPath = "/Users/dd/study/iSE/plagiarism_detection/NewDataSets/ALU/ALU_code";
//    public static final String PUTRootPath = "/Users/dd/study/iSE/plagiarism_detection/NewDataSets/PUT";

    public static final String PUTRootPath = "/Users/dd/Desktop/MAF-Data/src";
    public static final String rootPath = "/Users/dd/Desktop/MAF-Data/junit/2";


    /**
     *
     *
     */
    private static void analysis() {

        //将选手的测试代码从提交文件夹提取出来直接放入选手ID目录
        //ExperimentDataProcess.analyze(rootPath);

        //分析待测程序
//        PUTAnalysis.analyze(PUTRootPath);

        //旧的，不用
        //TPAnalysis.analyze(rootPath);

        // 我的 测试程序分析，提取片段
//        Map<Integer, List<ContestantTFModel>> map =  TPAnalysis.myAnalyze(rootPath);

        // 计算测试片段之间相似度
        //TFAnalysis.analysis();

        // similarity matrix analysis
//        SimMatrixAnalysis.analysis(rootPath);

        // plagiarism analysis
//        PlagiarismAnalysis.analysis(rootPath);

        //diff分析（含文件相似度）
        //DifflibAnalysis.analysis();

        // plaggie分析
//        Plaggie.analyze();

        // Fuzzywuzzy分析
//        FuzzywuzzyAnalysis.analyze();

        // SearchAnalysis
//        SearchAnalysis.analyze();

        // 分析巧合性重复：coincidental repetition
//        CoincidentalRepetitionAnalysis.analyze();

        // 数据统计
//        StatisticAnalysis.analyze();

    }

    private static void learningTest() {
        //        SVMLearner.train(rootPath);
        String testDataFilePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/test data sets/test_data_label_1_8000.txt";
        String modelFilePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/model_output_8000.txt";
        String resultFilePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/test_result_output_label_1_8000.txt";
        //SVMLearner.predict(testDataFilePath, modelFilePath, resultFilePath);

//        String dataFilePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/train data sets/train_data_all.txt";
//        String scaleParaFilePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/scale_para_save.txt";
//        SVMLearner.scale(0,1, scaleParaFilePath, dataFilePath);
    }

    private static void fuzzywuzzy() {

        String sourceStr = "var1=(\"variable\");" +
                "ag1=.variable(var1);" +
                "va1=(\"value0001\");" +
                "ag1=.value(va1);"+
                "assertNotNull(ag1.toString());";
        String targetStr = "variable1=(\"variable1\");" +
                "value1=(\"value1\");" +
                "argument;" +
                "argument=.variable(variable1);" +
                "ag1=.value(value1);" +
                "assertEquals(\"value1\",argument.toString());" +
                "assertNotNull(\"VAR:variable1\",argument.toString());";

        targetStr = "Argument[] ags1 = {ag1,ag2};" +
                "Predicate pd1 = new Predicate(\"test0001\");" +
                "Datalog dl1 = new Datalog(pd1,ags1);" +
                "assertNotNull(dl1.toFact());";
        sourceStr = "Argument[] arguments=new Argument[10];" +
                "Predicate predict;" +
                "Datalog datalog;" +
                "arguments[i]=Argument.value(new Value(\"value\"+i));" +
                "predict=new Predicate(\"predict\");" +
                "datalog=new Datalog(predict, arguments);" +
                "assertEquals(f, datalog.toFact());";

        targetStr = "[] ags1 = {ag1,ag2};" +
                " pd1 = new (\"test0001\");" +
                " dl1 = new (pd1,ags1);" +
                "assertNotNull(dl1.());";
        sourceStr = "[] arguments=new [10];" +
                " predict;" +
                " datalog;" +
                "arguments[i]=.value(new (\"value\"+i));" +
                "predict=new (\"predict\");" +
                "datalog=new (predict, arguments);" +
                "assertEquals(f, datalog.());";

        int simValue = FuzzySearch.ratio(sourceStr, targetStr);
        System.out.println("ratio:" + simValue);

        simValue = FuzzySearch.partialRatio(sourceStr, targetStr);
        System.out.println("partialRatio:" + simValue);

        simValue = FuzzySearch.tokenSetRatio(sourceStr, targetStr);
        System.out.println("tokenSetRatio:" +simValue);

        simValue = FuzzySearch.tokenSetPartialRatio(sourceStr, targetStr);
        System.out.println("tokenSetPartialRatio:" +simValue);

        simValue = FuzzySearch.tokenSortRatio(sourceStr, targetStr);
        System.out.println("tokenSortRatio:" + simValue);

        simValue = FuzzySearch.tokenSortPartialRatio(sourceStr, targetStr);
        System.out.println("tokenSortPartialRatio:" + simValue);

        simValue = FuzzySearch.weightedRatio(sourceStr, targetStr);
        System.out.println("weightedRatio:" + simValue);
    }

    /**
     *
     * @param
     * @return
     * @date 2018/4/3 下午9:50
     */
    public static void main(String[] args) {

//        fuzzywuzzy();
        analysis();

//        HibernateUtil.closeSessionFactory();
//        test();
//        test1();
//        test3();
//        test10();
    }

    public static void test() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        double simValue = SimAnalysis.getSimValue("sweisong", "sunweisong");
        System.out.println(simValue);
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }


    public static void test3() {
        int threshold = 65;
        String basePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis";

        String pcClass1FilePath = basePath + File.separator + "pc_class_1_matrix.csv";

        int minSimValue = findMinSVAmongAllMaxSVFromPCRecords(pcClass1FilePath);
        System.out.println("最小值" + minSimValue);


//        String[] cidList = {"12167","13437","13443","13478","15720"
//                ,"19387","19848","20092","20139","20247","20593","20598"
//                ,"20711","20786","21051","21055","21528","22159","22760"
//                , "23554","23580","23582","23770"};
//        for (String cid : cidList) {
//            String simMatrixFilePath = basePath + File.separator + "sim_matrix.csv";
//            String targetFilePath = basePath + File.separator + "Omission Data"
//                    + File.separator + cid + ".csv";
//            CsvReader reader = null;
//            try {
//                reader = new CsvReader(simMatrixFilePath, ',', Charset.forName("UTF-8"));
//                File targetFile = new File(targetFilePath);
//                List<String[]> contentBeLabeledList = new ArrayList<>();
//                int count = 0;
//                while(reader.readRecord()) {
//                    count++;
//                    if (count == 1) {
//                        continue;
//                    }
//                    String[] rowContents = reader.getValues();
//                    String cidGroup = rowContents[0];
//                    int startIndex = cidGroup.indexOf("<");
//                    int endIndex = cidGroup.indexOf(">");
//                    String cidString = cidGroup.substring(startIndex + 1, endIndex);
//                    String[] cidArray = cidString.split(",");
//                    if (cid.equals(cidArray[0].trim()) || cid.equals(cidArray[1].trim())) {
//                        int length = rowContents.length;
//                        int maxSimValue = 0;
//                        for (int index = 1; index < length; index++) {
//                            int simValue = Integer.parseInt(rowContents[index]);
//                            if (simValue > maxSimValue) {
//                                maxSimValue = simValue;
//                            }
//                        }
//                        if (maxSimValue > threshold) {
//                            contentBeLabeledList.add(rowContents);
//                        }
//                    }
//                }
//                if (contentBeLabeledList.size() > 0) {
//                    FileUtil.writeMatrixToCSVFile(targetFile,null
//                            , contentBeLabeledList, false);
//                    contentBeLabeledList.clear();
//                    contentBeLabeledList = null;
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                reader.close();
//            }
//        }
    }

    private static int findMinSVAmongAllMaxSVFromPCRecords(String pcClass1FilePath) {
        CsvReader reader = null;
        List<Integer> maxSimValueList = new ArrayList<>();
        try {
            reader = new CsvReader(pcClass1FilePath, ',', Charset.forName("UTF-8"));
            int count = 0;
            while(reader.readRecord()) {
                count++;
                if (count == 1) {
                    continue;
                }
                String[] rowContents = reader.getValues();
                int length = rowContents.length;
                int maxSimValue = 0;
                for (int index = 1; index < length - 1; index++) {
                    int simValue = Integer.parseInt(rowContents[index]);
                    if (simValue > maxSimValue) {
                        maxSimValue = simValue;
                    }
                }
                maxSimValueList.add(maxSimValue);
            }
            System.out.println("list大小" + maxSimValueList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        if (maxSimValueList.size() > 0) {
            int minSimValue = maxSimValueList.get(0);
            for (int simValue : maxSimValueList) {
                if (simValue >= minSimValue) {
                    continue;
                }
                minSimValue = simValue;
            }
            return minSimValue;
        }
        return 0;
    }


    public static void test1() {
        String sourcePath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/test data sets/test_data_8000.txt";
        String targetPath = "/Users/sunweisong/Desktop/TCSAData/CST2017/666/analysis/Datalog/sim_analysis/svm try data/test data sets/test_data_label_1_8000.txt";
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileReader = new FileReader(sourceFile);
            bufferedReader = new BufferedReader(fileReader);
            fileWriter = new FileWriter(targetFile, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                char first = line.charAt(0);
                if (first == '1') {
                    count++;
                    bufferedWriter.write(line + "\r\n");
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                    bufferedWriter = null;
                }
                if (fileWriter != null) {
                    fileWriter.close();
                    fileWriter = null;
                }
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
    }


    public static void test10() {
        String path = "/Users/sunweisong/Desktop/our_tool_tcsa/CST2017/666/analysis/Datalog/sim_analysis/tfs_classified_by_mid";
        File director = new File(path);
        if (director.exists()) {
            File[] files = director.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
//                if ("58".equals(file.getName())) {
//                    File[] temp = file.listFiles();
//                    for (File f : temp) {
//                        System.out.println(f.getName());
//                    }
//                }
            }
        }

    }

}
