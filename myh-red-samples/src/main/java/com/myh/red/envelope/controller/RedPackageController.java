package com.myh.red.envelope.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 抢红包控制器;
 *
 * @author myh
 * @date 2020/9/29
 */
@RestController
public class RedPackageController {

    /**
     * 针对某一个活动进行，拆分红包;
     *
     * @return
     */
    @RequestMapping("/splitRedPackage")
    public String splitRedPackage() {
        return "";
    }



}