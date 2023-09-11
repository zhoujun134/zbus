package com.zj.zbus.controller;

import com.zj.common.exception.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhoujun
 * @Date: 2023/9/11 07:49
 */

@Api(tags = "主页相关", value = "IndexController")
@RestController
@RequestMapping("/api")
@Slf4j
public class IndexController {

    @GetMapping("/home")
    @ApiOperation("主页信息")
    public Result<String> home() {
        return Result.ok("主页信息，不需要权限");
    }

}
