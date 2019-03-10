package demo.service.impl;

import demo.com.tcsa.analysis.PUTAnalysis;
import demo.com.tcsa.analysis.SimAnalysis;
import demo.com.tcsa.analysis.TFAnalysis;
import demo.com.tcsa.analysis.TPAnalysis;
import demo.com.tcsa.model.ContestantSimilarity;
import demo.com.tcsa.model.ContestantSimilarityByMID;
import demo.com.tcsa.model.ContestantTFModel;
import demo.com.tcsa.model.TFSimilarityModel;
import demo.common.ResponseCode;
import demo.common.Result;
import demo.dao.TFModelDao;
import demo.entity.MUTModel;
import demo.dao.MUTModelDao;
import demo.dao.SimValueModelDao;
import demo.entity.SimValueModel;
import demo.entity.TFModel;
import demo.vo.IndexDisplayVO;
import demo.vo.Inputs;
import demo.vo.MUTInfoVO;
import demo.vo.SimValueVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import demo.service.TFService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class TFServiceImpl implements TFService {

    @Resource
    private SimValueModelDao simValueModelDao;

    @Resource
    private MUTModelDao mutModelDao;

    @Resource
    private TFModelDao tfModelDao;

    @Override
    public Result getSimValue(Inputs inputs) {


        List<Double> result = new ArrayList<>();
//        for(SimValueModel simValueModel : list){
//            result.add(simValueModel.getSimValue());
//        }

        return Result.success().code(200).withData(result);
    }

    @Override
    public Result detect(Inputs inputs) {
        List<IndexDisplayVO> indexDisplayVOList = new ArrayList<>();
        String srcPath = inputs.getSrcPath();
        String p1Path = inputs.getP1Path();
        String p2Path = inputs.getP2Path();
        double threshold = inputs.getThreshold();
        List<MUTModel> mutModelList;
        String[] p1s = p1Path.split("/");
        int cid1 = Integer.parseInt(p1s[p1s.length-1]);
        String[] p2s = p2Path.split("/");
        int cid2 = Integer.parseInt(p2s[p2s.length-1]);
        try {
            List<SimValueModel> simValueModelList = simValueModelDao.searchSimValueByPair(cid1,cid2);
            if(simValueModelList.size() != 0){
                for(int i=0;i<simValueModelList.size();i++){
                    IndexDisplayVO indexDisplayVO = new IndexDisplayVO();
                    indexDisplayVO.setMethodId(i+1);
                    SimValueModel simValueModel = simValueModelList.get(i);
                    int mid = simValueModel.getMid();
                    indexDisplayVO.setMethodName(mutModelDao.getMethodNameByMID(mid));

                    indexDisplayVO.setTfid1(tfModelDao.getIdByMIDAndCid(mid,cid1));
                    indexDisplayVO.setTfid2(tfModelDao.getIdByMIDAndCid(mid,cid2));

                    double simValue = simValueModel.getSimValue();
                    indexDisplayVO.setSimValue(simValue);
                    if(simValue >= threshold*100){
                        indexDisplayVO.setPlag(true);
                    }else{
                        indexDisplayVO.setPlag(false);
                    }

                    indexDisplayVOList.add(indexDisplayVO);
                }
            }else {
                mutModelList = PUTAnalysis.analyze(srcPath);
                Map<Integer, List<ContestantTFModel>> tfMap1 = TPAnalysis.myAnalyze(mutModelList, p1Path);
                Map<Integer, List<ContestantTFModel>> tfMap2 = TPAnalysis.myAnalyze(mutModelList, p2Path);


                //
                //        for(SimValueModel simValueModel : list){
                //            result.add(simValueModel.getSimValue());
                //        }


                List<SimValueVO> resultList = new ArrayList<>();
                for (MUTModel mutModel : mutModelList) {
                    //                System.out.println(mutModelList.size());
                    MUTModel mutModelEntity = mutModelDao.save(mutModel);
                }

                saveTFToDB(tfMap1);
                saveTFToDB(tfMap2);
                // 计算测试片段之间相似度并存入数据库
                List<List<SimValueVO>> simValueList = tfAnalysis(mutModelList);
                //11个list，每个里面一个元素


                for (int i = 0; i < simValueList.size(); i++) {
                    //                System.out.println("List"+i+":");
                    List<SimValueVO> list = simValueList.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        //                    System.out.println(list.get(j).getSimValue());
                        IndexDisplayVO indexDisplayVO = new IndexDisplayVO();
                        indexDisplayVO.setMethodId(i + 1);

                        SimValueVO simValueVO = list.get(j);
                        int mid = simValueVO.getMid();
                        indexDisplayVO.setMethodName(mutModelDao.getMethodNameByMID(mid));

                        indexDisplayVO.setTfid1(tfModelDao.getIdByMIDAndCid(mid, cid1));
                        indexDisplayVO.setTfid2(tfModelDao.getIdByMIDAndCid(mid, cid2));

                        double simValue = simValueVO.getSimValue();
                        indexDisplayVO.setSimValue(simValue);
                        if (simValue >= threshold * 100) {
                            indexDisplayVO.setPlag(true);
                        } else {
                            indexDisplayVO.setPlag(false);
                        }

                        indexDisplayVOList.add(indexDisplayVO);
                    }
                }
            }


                return Result.success().message("检测结果保存成功！").withData(indexDisplayVOList);
        }catch (Exception e){
            return Result.error().message("检测结果保存失败，数据库更新错误！").code(ResponseCode.DB_UPDATE_ERROR);

        }
    }

//    public List<IndexDisplayVO> prepareIndexDisplay(List<SimValueVO> list){
//
//    }

    public void saveTFToDB(Map<Integer, List<ContestantTFModel>> tfMap){
        Iterator<Map.Entry<Integer,List<ContestantTFModel>>> entries = tfMap.entrySet().iterator();
        while(entries.hasNext()) {
            Map.Entry<Integer, List<ContestantTFModel>> entry = entries.next();
            int MID = entry.getKey();
            List<ContestantTFModel> contestantTFModelList = entry.getValue();
            if (contestantTFModelList != null) {
                for (ContestantTFModel contestantTFModel :
                        contestantTFModelList) {
                    TFModel tfModel = new TFModel();
                    tfModel.setMid(MID);
                    tfModel.setCid(contestantTFModel.getCID());
                    tfModel.setFragment(contestantTFModel.getTestFragment());
                    tfModel.setStateNum(contestantTFModel.getStateNumber());
                    tfModel.setLength(contestantTFModel.getFragmentLength());
                    TFModel tfModelEntity = tfModelDao.save(tfModel);
                }
            }
        }
    }

    public List<List<SimValueVO>> tfAnalysis(List<MUTModel> mutModelList) {
//        mutModelList = mutModelDao.getMUTModelList();
        int[] mIDArray = new int[mutModelList.size()];
        int index = 0;
        for (MUTModel mutModel : mutModelList) {
            long mid =  mutModel.getMethodId();
//            if (mid == -373229334 || mid == -561849238|| mid == -576060075
//                    || mid == -620252230 || mid == -620421252
//                    || mid == -698809980 || mid == -699150091
//                    || mid == -715073250 || mid == -717360243
//                    || mid == -723512252 || mid == -862597736
//                    || mid == -949293390 ) {
            mIDArray[index] = mutModel.getMethodId();
            index++;
//            }
        }

        // category: 0-ration; 1-partialRatio;
        List<List<SimValueVO>> resultLists = calculateSimilarityBetweenTF(mIDArray, 1);
        return resultLists;
    }

    public List<List<SimValueVO>> calculateSimilarityBetweenTF(int[] mIDArray, int category) {
        List<ContestantSimilarityByMID> contestantSimilarityByMIDList = new ArrayList<>(mIDArray.length);
        List<List<SimValueVO>> resultLists = new ArrayList<>();
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
            resultLists.add(saveTFSimValueToDatabase(contestantSimilarityByMIDList, category));
            contestantSimilarityByMIDList.clear();
        }
        return resultLists;
    }

    private List<SimValueVO> saveTFSimValueToDatabase(List<ContestantSimilarityByMID> contestantSimilarityByMIDList
            , int category) {
        List<SimValueVO> resultList = new ArrayList<>();
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
                    SimValueModel simValueModel = new SimValueModel();
                    simValueModel.setMid(MID);
                    simValueModel.setCid1(CID1);
                    simValueModel.setCid2(CID2);
                    simValueModel.setSimValue(simValue);
                    simValueModel.setCategory(category);
                    simValueModelDao.save(simValueModel);

                    SimValueVO result = new SimValueVO();
                    BeanUtils.copyProperties(simValueModel, result);
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }
}
