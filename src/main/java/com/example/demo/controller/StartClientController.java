package com.example.demo.controller;

import com.example.demo.service.StartClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/client")
public class StartClientController {
    @Autowired
    StartClientService startClientService;

    @PostMapping("/startClient")
    public void startClient(){
        startClientService.startClient();
        //调动线程，开始爬虫
    }

    @GetMapping("/getUFCDate")
    public Map getUFCDate(){
        return startClientService.getUFCDate();
        //socket传输赔率数据信息
    }
}
