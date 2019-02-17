package demo.service;

import demo.com.tcsa.common.Result;
import demo.com.tcsa.vo.ALLPath;

public interface TFService {
    Result getSimValue();

    Result detect(ALLPath paths);
}
