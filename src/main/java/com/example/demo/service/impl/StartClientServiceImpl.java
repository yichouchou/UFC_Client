package com.example.demo.service.impl;

import com.example.demo.AutoClient;
import com.example.demo.config.WebSocket;
import com.example.demo.service.StartClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StartClientServiceImpl implements StartClientService {

    @Autowired
    WebSocket webSocket;

    @Autowired
    AutoClient autoClient;

    @Override
    public void startClient() {

    }

    @Override
    public Map getUFCDate() {
        return autoClient.getContestMap();
    }

}
