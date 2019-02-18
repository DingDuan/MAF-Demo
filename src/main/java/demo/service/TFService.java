package demo.service;

import demo.com.tcsa.common.Result;
import demo.com.tcsa.vo.Inputs;

public interface TFService {
    Result getSimValue();

    Result detect(Inputs inputs);
}
