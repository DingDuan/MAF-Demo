package demo.service.impl;

import demo.com.tcsa.analysis.PUTAnalysis;
import demo.com.tcsa.analysis.TFAnalysis;
import demo.com.tcsa.analysis.TPAnalysis;
import demo.com.tcsa.common.Result;
import demo.com.tcsa.dao.SimValueModelDao;
import demo.com.tcsa.model.SimValueModel;
import demo.com.tcsa.vo.ALLPath;
import org.springframework.stereotype.Service;
import demo.service.TFService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TFServiceImpl implements TFService {

//    @Resource
//    private SimValueModelDao simValueModelDao;

    @Override
    public Result getSimValue() {
//        String srcPath = paths[0];
//        String p1Path = paths[1];
//        String p2Path = paths[2];
//        String[] p1s = p1Path.split("/");
//        int cid1 = Integer.parseInt(p1s[p1s.length-1]);
//        String[] p2s = p2Path.split("/");
//        int cid2 = Integer.parseInt(p2s[p2s.length-1]);
//        PUTAnalysis.analyze(srcPath);
//        TPAnalysis.myAnalyze(p1Path);
//        TPAnalysis.myAnalyze(p2Path);
//
//        // 计算测试片段之间相似度
//        TFAnalysis.analysis();

//        List<SimValueModel> list = simValueModelDao.searchSimValueByParameter(cid1,cid2);
        List<Double> result = new ArrayList<>();
//        for(SimValueModel simValueModel : list){
//            result.add(simValueModel.getSimValue());
//        }
        return Result.success().code(200).withData(result);
    }

    @Override
    public Result detect(ALLPath paths) {
        return null;
    }
}
