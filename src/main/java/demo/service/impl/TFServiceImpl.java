package demo.service.impl;

import demo.com.tcsa.analysis.PUTAnalysis;
import demo.com.tcsa.analysis.TPAnalysis;
import demo.com.tcsa.model.ContestantTFModel;
import demo.common.ResponseCode;
import demo.common.Result;
import demo.dao.TFModelDao;
import demo.entity.MUTModel;
import demo.dao.MUTModelDao;
import demo.dao.SimValueModelDao;
import demo.entity.SimValueModel;
import demo.entity.TFModel;
import demo.vo.Inputs;
import demo.vo.MUTInfoVO;
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

//        String srcPath = inputs.getSrcPath();
//        System.out.println(srcPath);
//        String p1Path = inputs.getP1Path();
//        String p2Path = inputs.getP2Path();
//
//        PUTAnalysis.analyze(srcPath);
//        TPAnalysis.myAnalyze(p1Path);
//        TPAnalysis.myAnalyze(p2Path);
//
//        // 计算测试片段之间相似度
//        TFAnalysis.analysis();
//
//        String[] p1s = p1Path.split("/");
//        int cid1 = Integer.parseInt(p1s[p1s.length-1]);
//        String[] p2s = p2Path.split("/");
//        int cid2 = Integer.parseInt(p2s[p2s.length-1]);
//
//        List<SimValueModel> list = simValueModelDao.searchSimValueByParameter(cid1,cid2);
        List<Double> result = new ArrayList<>();
//        for(SimValueModel simValueModel : list){
//            result.add(simValueModel.getSimValue());
//        }

        return Result.success().code(200).withData(result);
    }

    @Override
    public Result detect(Inputs inputs) {
        String srcPath = inputs.getSrcPath();
        String p1Path = inputs.getP1Path();
        String p2Path = inputs.getP2Path();
        List<MUTModel> mutModelList;
        List<TFModel> tfModelList;
        mutModelList = PUTAnalysis.analyze(srcPath);
        Map<Integer, List<ContestantTFModel>> tfMap1 = TPAnalysis.myAnalyze(mutModelList,p1Path);
        Map<Integer, List<ContestantTFModel>> tfMap2 = TPAnalysis.myAnalyze(mutModelList,p2Path);


        try {
            List<MUTInfoVO> resultList = new ArrayList<>();
//            for(MUTModel mutModel : mutModelList) {
//                MUTModel mutModelEntity = mutModelDao.save(mutModel);
//                //等写完把result改成simvalue那个
//                MUTInfoVO result = new MUTInfoVO();
//                BeanUtils.copyProperties(mutModelEntity, result);
//                resultList.add(result);
//            }

            saveTFToDB(tfMap1);
            saveTFToDB(tfMap2);

            return Result.success().message("检测结果保存成功！").withData(resultList);
        }catch (Exception e){
            return Result.error().message("检测结果保存失败，数据库更新错误！").code(ResponseCode.DB_UPDATE_ERROR);

        }
        // 计算测试片段之间相似度
//        TFAnalysis.analysis();

//        String[] p1s = p1Path.split("/");
//        int cid1 = Integer.parseInt(p1s[p1s.length-1]);
//        String[] p2s = p2Path.split("/");
//        int cid2 = Integer.parseInt(p2s[p2s.length-1]);
//
//        List<SimValueModel> list = simValueModelDao.searchSimValueByParameter(cid1,cid2);

//        for(SimValueModel simValueModel : list){
//            result.add(simValueModel.getSimValue());
//        }
//
    }

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
}
