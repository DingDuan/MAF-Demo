package demo.service.impl;

import demo.com.tcsa.analysis.PUTAnalysis;
import demo.common.ResponseCode;
import demo.common.Result;
import demo.com.tcsa.model.MUTModel;
import demo.dao.MUTModelDao;
import demo.dao.SimValueModelDao;
import demo.vo.Inputs;
import demo.vo.MUTInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import demo.service.TFService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TFServiceImpl implements TFService {

    @Resource
    private SimValueModelDao simValueModelDao;

    @Resource
    private MUTModelDao mutModelDao;

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
    public Result detect(String srcPath) {
//        String[] paths = inputs.getPaths();
//        String srcPath = paths[0];
        System.out.println("dfsafdsdsfdvdfvsfvsdfv:"+srcPath);
//        String p1Path = paths[1];
//        String p2Path = paths[2];
        List<MUTModel> mutModelList = new ArrayList<>();
        mutModelList = PUTAnalysis.analyze(srcPath);
        try {
            List<MUTInfoVO> resultList = new ArrayList<>();
            for(MUTModel mutModel : mutModelList) {
                MUTModel mutModelEntity = mutModelDao.save(mutModel);
                MUTInfoVO result = new MUTInfoVO();
                BeanUtils.copyProperties(mutModelEntity, result);
                resultList.add(result);
            }

            return Result.success().message("检测结果保存成功！").withData(resultList);
        }catch (Exception e){
            return Result.error().message("检测结果保存失败，数据库更新错误！").code(ResponseCode.DB_UPDATE_ERROR);

        }
//        TPAnalysis.myAnalyze(p1Path);
//        TPAnalysis.myAnalyze(p2Path);

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
}
