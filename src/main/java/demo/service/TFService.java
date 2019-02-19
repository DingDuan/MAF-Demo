package demo.service;

import demo.common.Result;
import demo.vo.Inputs;

public interface TFService {
    Result getSimValue(Inputs inputs);

    Result detect(Inputs inputs);
}
