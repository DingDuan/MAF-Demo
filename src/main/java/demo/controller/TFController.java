package demo.controller;

import demo.common.Result;
import demo.vo.Inputs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import demo.service.TFService;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/TF")
public class TFController {
    @Resource
    private TFService tfService;

    @GetMapping("/detect")
    public Result detect(@RequestParam("srcPath") String srcPath){
        return tfService.detect(srcPath);
    }

    @GetMapping("/simValue")
    public Result getSimValue(@RequestParam("inputs") Inputs inputs) {
        return tfService.getSimValue(inputs);
    }
}
