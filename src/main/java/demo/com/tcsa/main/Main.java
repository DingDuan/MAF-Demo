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
//        analysis();
//
////        HibernateUtil.closeSessionFactory();
////        test();
////        test1();
////        test3();
////        test10();
//
//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("题目：Datalog");
//        System.out.println("选手ID：1 vs 2");
//        System.out.println("最大相似度百分比：78");

        double[] TPs = {3135,
                3135,
                3135,
                3130,
                3113,
                3099,
                3068,
                3052,
                3047,
                3015,
                2989,
                2962,
                2943,
                2869,
                2735,
                2630,
                1834,
                2351,
                774,
                709,
                738,
                698,
                693,
                410};
        double[] FPs = {74200,
                73807,
                72671,
                68763,
                62595,
                54003,
                42487,
                26189,
                28188,
                21029,
                18920,
                14370,
                13269,
                8391,
                4739,
                2892,
                1690,
                1065,
                494,
                402,
                344,
                296,
                241,
                8};
        double P = 0;
        double R = 0.998;
        for(int i=0;i<TPs.length;i++){
//            System.out.println(
 P = TPs[i]/(TPs[i]+FPs[i]);
//        );
            R = TPs[i]/3185.0;
            System.out.println(2*R*P/(P+R));
        }

        int[] cids = {8235,
                8668,
                11165,
                11417,
                11839,
                14218,
                17093,
                17123,
                17259,
                17747,
                17898,
                17983,
                18028,
                18815,
                18996,
                19378,
                19529,
                19838,
                20308,
                20772,
                20882,
                21549,
                21550,
                22877,
                34596,
                35116,
                35447,
                35454,
                35481,
                35483,
                35500,
                35531,
                35532,
                35546,
                35594,
                35610,
                35668,
                35670,
                35675,
                35749,
                35762,
                36660,
                36661,
                36703,
                36705,
                36706,
                36728,
                37002,
                38847,
                38850,
                38851,
                38893,
                39098,
                39099,
                39116,
                39134,
                39140,
                39153,
                39165,
                39179,
                39183,
                39233,
                39275,
                39276,
                39294,
                39296,
                39307,
                39312,
                39313,
                39334,
                39345,
                39379,
                39445,
                39465,
                39499,
                39555,
                39577,
                39712,
                39800,
                39913,
                39918,
                40060,
                40076,
                40077,
                40081,
                40148,
                40177,
                40261,
                40267,
                40405,
                40453,
                40478,
                40543,
                40548,
                40549,
                40550,
                40551,
                40552,
                40553,
                40554,
                40555,
                40556,
                40557,
                40558,
                40559,
                40560,
                40561,
                40563,
                40564,
                40565,
                40566,
                40567,
                40568,
                40570,
                40571,
                40572,
                40574,
                40575,
                40576,
                40577,
                40578,
                40579,
                40580,
                40581,
                40582,
                40583,
                40586,
                40587,
                40588,
                40589,
                40590,
                40592,
                40593,
                40594,
                40595,
                40596,
                40597,
                40598,
                40599,
                40600,
                40601,
                40602,
                40603,
                40604,
                40605,
                40606,
                40607,
                40608,
                40610,
                40611,
                40612,
                40614,
                40616,
                40617,
                40621,
                40627,
                40631,
                40632,
                40634,
                40638,
                40641,
                40651,
                40652,
                40657,
                40658,
                40661,
                40662,
                40663,
                40664,
                40665,
                40666,
                40667,
                40668,
                40669,
                40670,
                40671,
                40673,
                40675,
                40676,
                40677,
                40680,
                40681,
                40682,
                40684,
                40685,
                40686,
                40687,
                40689,
                40690,
                40691,
                40692,
                40693,
                40694,
                40696,
                40697,
                40698,
                40700,
                40701,
                40703,
                40705,
                40706,
                40708,
                40709,
                40710,
                40711,
                40713,
                40714,
                40716,
                40717,
                40719,
                40745,
                40746,
                40751,
                40752,
                40754,
                40761,
                40771,
                40799,
                40802,
                40811,
                40818,
                40820,
                40821,
                40830,
                40889,
                40906,
                40916,
                40918,
                40919,
                40921,
                40922,
                40924,
                40927,
                40929,
                40930,
                40931,
                40933,
                40934,
                40936,
                40940,
                40941,
                40943,
                40945,
                40946,
                40952,
                40965,
                40981,
                40985,
                40986,
                41005,
                41011,
                41014,
                41018,
                41022,
                41023,
                41097,
                41101,
                41104,
                41128,
                41129,
                41131,
                41137,
                41143,
                41145,
                41154,
                41155,
                41172,
                41173,
                41186,
                41188,
                41190,
                41204,
                41207,
                41214,
                41218,
                41219,
                41226,
                41238,
                41247,
                41257,
                41267,
                41271,
                41273,
                41274,
                41275,
                41288,
                41312,
                41323,
                41326,
                41371,
                41390,
                41391,
                41392,
                41394,
                41395,
                41398,
                41413,
                41417,
                41420,
                41427,
                41442,
                41447,
                41477,
                41483,
                41486,
                41487,
                41489,
                41520,
                41522,
                41535,
                41538,
                41543,
                41544,
                41549,
                41556,
                41557,
                41563,
                41579,
                41582,
                41600,
                41604,
                41610,
                41618,
                41642,
                41651,
                41673,
                41678,
                41694,
                41753,
                41788,
                41796,
                41797,
                41798,
                41810,
                41812,
                41849,
                41859,
                41863,
                41864,
                41865,
                41880,
                41886,
                41887,
                41889,
                41899,
                41900,
                41901,
                41902,
                41906,
                41907,
                41914,
                41928,
                41985,
                41991,
                41992,
                41993,
                41997,
                42002,
                42003,
                42004,
                42005,
                42006,
                42007,
                42008,
                42024,
                42025,
                42035,
                42045,
                42051,
                42063,
                42068,
                42071,
                42082,
                42116,
                42119,
                42122,
                42123,
                42135,
                42168,
                42173,
                42181,
                42182,
                42183,
                42215,
                42242,
                42261,
                42305,
                42307,
                42417,
                42418,
                42430,
        };
        String path = "/Users/dd/Desktop/cids.xlsx";

        File writeFile = new File(path);

//        try{
//            //第二步：通过BufferedReader类创建一个使用默认大小输出缓冲区的缓冲字符输出流
//            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile));
//
//            //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
//            for (int i = 0; i < cids.length; i++) {
//                for (int j = i + 1; j < cids.length; j++) {
//                    writeText.newLine();
//                    //调用write的方法将字符串写到流中
//                    writeText.write(cids[i]+","+cids[j]);
//                }
//            }
//
//
//            //使用缓冲区的刷新方法将数据刷到目的地中
//            writeText.flush();
//            //关闭缓冲区，缓冲区没有调用系统底层资源，真正调用底层资源的是FileWriter对象，缓冲区仅仅是一个提高效率的作用
//            //因此，此处的close()方法关闭的是被缓存的流对象
//            writeText.close();
//        }catch (FileNotFoundException e){
//            System.out.println("没有找到指定文件");
//        }catch (IOException e){
//            System.out.println("文件读写出错");
//        }

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
