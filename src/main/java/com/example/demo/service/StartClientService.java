package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface StartClientService {
    void startClient();

    Map getUFCDate();
}
