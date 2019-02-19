package demo.com.tcsa.analysis;

import demo.dao.MUTModelDao;
import demo.dao.SimValueModelDao;
import demo.dao.TFModelDao;
import demo.com.tcsa.daoImpl.SimValueModelDaoImpl;
import demo.com.tcsa.daoImpl.TFModelDaoImpl;
import demo.com.tcsa.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
  * @Date 2018/6/26 下午3:44
  */
public class TFAnalysis {

    private static List<MUTModel> mutModelList;

//    private static MUTModelDao mutModelDao = MUTModelDaoImpl.getInstance();

    private static TFModelDao tfModelDao = TFModelDaoImpl.getInstance();

    private static SimValueModelDao simValueModelDao = SimValueModelDaoImpl.getInstance();


    /**
      * 
      * @param 
      * @return
      * @throws
      * @date 2018/6/26 下午7:25
      */
    private static void saveTFSimValueToDatabase(List<ContestantSimilarityByMID> contestantSimilarityByMIDList
            , int category) {
        for (ContestantSimilarityByMID contestantSimilarityByMID:
                contestantSimilarityByMIDList) {
            int MID = contestantSimilarityByMID.getMID();
            List<ContestantSimilarity> contestantSimilarityList = contestantSimilarityByMID.getContestantSimilarityList();
            if (contestantSimilarityList == null) {
                // no contestant testing this MUT.
                continue;
            }
            for (ContestantSimilarity contestantSimilarity :
                    contestantSimilarityList) {
                int CID1 = contestantSimilarity.getCID();
                List<TFSimilarityModel> tfSimilarityModelList = contestantSimilarity.getTfSimilarityModelList();
                if (tfSimilarityModelList == null) {
                    // no more than one contestant testing this MUT.
                    continue;
                }
                for (TFSimilarityModel tFSimilarityModel:
                        tfSimilarityModelList) {
                    int CID2 = tFSimilarityModel.getCID2();
                    int simValue = tFSimilarityModel.getSimValue();
                    simValueModelDao.saveSimValueModel(new SimValueModel(MID
                                , CID1, CID2, simValue, category));
                }
            }
        }
    }

    private static List<ContestantSimilarityByMID> calculateSimilarityBetweenTFByThread(int[] mIDArray) {
        List<ContestantSimilarityByMID> contestantSimilarityByMIDList = new ArrayList<>(mIDArray.length);
        int threadNum = 0;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int mid : mIDArray) {
            if (mid == 0) {
                continue;
            }
            ContestantSimilarityByMID contestantSimilarityByMID = new ContestantSimilarityByMID(mid);
            List<TFModel> tfModelList = tfModelDao.getTFModelListByMID(mid);
            if (tfModelList == null) {
                contestantSimilarityByMIDList.add(contestantSimilarityByMID);
                continue;
            }
            int count = tfModelList.size();
            if (count < 2) {
                contestantSimilarityByMIDList.add(contestantSimilarityByMID);
                continue;
            }
            List<ContestantSimilarity> contestantSimilarityList = new ArrayList<>(count);
            threadNum++;
            final int currentThreadNum = threadNum;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("子线程[" + currentThreadNum + "]开启");
                    System.out.println("MID：" + mid);
                    System.out.println("对比人数：" + count);
                    long startTime=System.currentTimeMillis();
                    System.out.println("对比开始时间：" + startTime);
                    int compareNumber = 0;
                    for (int index = 0; index < count; index++) {
                        TFModel baseTFModel = tfModelList.get(index);
                        int CID1 = baseTFModel.getCid();
                        ContestantSimilarity contestantSimilarity = new ContestantSimilarity(CID1);
                        String baseTestFragment = baseTFModel.getFragment();
                        if (baseTestFragment != null) {
                            List<TFSimilarityModel> tfSimilarityModelList = new ArrayList<>(count - index - 1);
                            for (int index1 = index + 1; index1 < count; index1++) {
                                TFModel tfModel = tfModelList.get(index1);
                                int CID2 = tfModel.getCid();
                                String testFragment = tfModel.getFragment();
//                                int simValue = SimAnalysis.getSimValue(baseTestFragment, testFragment);
                                int simValue = SimAnalysis.fuzzyRatio(baseTestFragment, testFragment);
                                compareNumber++;
                                TFSimilarityModel tfSimilarityModel = new TFSimilarityModel(CID1, CID2, simValue);
                                tfSimilarityModelList.add(tfSimilarityModel);
                            }
                            contestantSimilarity.setTfSimilarityModelList(tfSimilarityModelList);
                        }
                        contestantSimilarityList.add(contestantSimilarity);
                    }
                    System.out.println("MID：" + mid);
                    System.out.println("对比次数：" + compareNumber);
                    long endTime=System.currentTimeMillis();
                    System.out.println("对比结束时间：" + endTime);
                    System.out.println("对比运行耗时：" + (endTime - startTime) + "ms");
                    System.out.println("子线程[" + currentThreadNum + "]结束");
                }
            });

            contestantSimilarityByMID.setContestantSimilarityList(contestantSimilarityList);
            contestantSimilarityByMIDList.add(contestantSimilarityByMID);
        }
        System.out.println("已经开启所有的子线程");
        fixedThreadPool.shutdown();
        System.out.println("shutdown()：启动一次顺序关闭，执行以前提交的任务，但不接受新任务。");
        while(true){
            if(fixedThreadPool.isTerminated()){
                System.out.println("所有的子线程都结束了！");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fixedThreadPool = null;

        return contestantSimilarityByMIDList;
    }

    /**
     *
     * @param
     * @return
     * @throws
     * @date 2018/6/26 下午6:58
     */
    private static void calculateSimilarityBetweenTF(int[] mIDArray, int category) {
        List<ContestantSimilarityByMID> contestantSimilarityByMIDList = new ArrayList<>(mIDArray.length);
        for (int mid : mIDArray) {
            if (mid == 0) {
                continue;
            }
            int compareNumber = 0;
            System.out.println("MID：" + mid);
            ContestantSimilarityByMID contestantSimilarityByMID = new ContestantSimilarityByMID(mid);
            List<TFModel> tfModelList = tfModelDao.getTFModelListByMID(mid);
            if (tfModelList == null) {
                contestantSimilarityByMIDList.add(contestantSimilarityByMID);
                continue;
            }
            int count = tfModelList.size();
            System.out.println("对比人数：" + count);
            if (count < 2) {
                contestantSimilarityByMIDList.add(contestantSimilarityByMID);
                continue;
            }
            long startTime=System.currentTimeMillis();
            System.out.println("对比开始时间：" + startTime);
            List<ContestantSimilarity> contestantSimilarityList = new ArrayList<>(count);
            for (int index = 0; index < count; index++) {
                TFModel baseTFModel = tfModelList.get(index);
                int CID1 = baseTFModel.getCid();
                ContestantSimilarity contestantSimilarity = new ContestantSimilarity(CID1);
                String baseTestFragment = baseTFModel.getFragment();
                if (baseTestFragment != null) {
                    List<TFSimilarityModel> tfSimilarityModelList = new ArrayList<>(count - index - 1);
                    for (int index1 = index + 1; index1 < count; index1++) {
                        TFModel tfModel = tfModelList.get(index1);
                        int CID2 = tfModel.getCid();
                        String testFragment = tfModel.getFragment();
                        int simValue = 0;
//                        if (category == 0) {
//                            simValue = SimAnalysis.getSimValue(baseTestFragment, testFragment);
//                        }
                        if (category == 1) {
                            simValue = SimAnalysis.fuzzyPartialRatio(baseTestFragment, testFragment);
                        }
                        compareNumber++;
                        TFSimilarityModel tfSimilarityModel = new TFSimilarityModel(CID1, CID2, simValue);
                        tfSimilarityModelList.add(tfSimilarityModel);
                    }
                    contestantSimilarity.setTfSimilarityModelList(tfSimilarityModelList);
                }
                contestantSimilarityList.add(contestantSimilarity);
            }
            contestantSimilarityByMID.setContestantSimilarityList(contestantSimilarityList);
            contestantSimilarityByMIDList.add(contestantSimilarityByMID);
            System.out.println("对比次数：" + compareNumber);
            long endTime=System.currentTimeMillis();
            System.out.println("对比结束时间：" + endTime);
            System.out.println("对比运行耗时：" + (endTime - startTime) + "ms");
            saveTFSimValueToDatabase(contestantSimilarityByMIDList, category);
            contestantSimilarityByMIDList.clear();
        }
    }

    /**
     *
     * @date 2018/6/26 下午3:44
     */
    public static void analysis() {
//        mutModelList = mutModelDao.getMUTModelList();
//        int[] mIDArray = new int[mutModelList.size()];
//        int index = 0;
//        for (MUTModel mutModel : mutModelList) {
//            long mid =  mutModel.getMethodId();
////            if (mid == -373229334 || mid == -561849238|| mid == -576060075
////                    || mid == -620252230 || mid == -620421252
////                    || mid == -698809980 || mid == -699150091
////                    || mid == -715073250 || mid == -717360243
////                    || mid == -723512252 || mid == -862597736
////                    || mid == -949293390 ) {
//                mIDArray[index] = mutModel.getMethodId();
//                index++;
////            }
//        }
//
//        // category: 0-ration; 1-partialRatio;
//        calculateSimilarityBetweenTF(mIDArray, 1);

    }

}
