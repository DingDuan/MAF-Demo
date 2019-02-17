package demo.controller;

import demo.com.tcsa.common.Result;
import demo.com.tcsa.vo.ALLPath;
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

    @PostMapping("/detect")
    public Result detect(@RequestBody ALLPath paths){
        return tfService.detect(paths);
    }

    @GetMapping("/simValue")
    public Result getSimValue() {
        return tfService.getSimValue();
    }
}
