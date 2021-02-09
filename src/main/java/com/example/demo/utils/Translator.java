package com.example.demo.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;
import java.util.*;

/**
 * 原计划使用谷歌翻译，翻译人名，发现页面有变动，导致无法使用
 */
public class Translator implements Runnable {
    private String TranslatorUrl;
    private Map taskNameMap = Collections.synchronizedMap(new HashMap<>());
    private Map NameMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void run() {
        WebClient webClient = null;
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(50000);
        webClient.waitForBackgroundJavaScript(15000);
        System.out.println("正在获取网页");
        HtmlPage htmlPage = null;
        try {
            htmlPage = webClient.getPage("https://translate.google.cn/");
            //用于刷新页面
//            webClient.getWebWindows().get(0).getHistory().go(0);
            //执行刷新,返回的是一个page接口，这个刷新还没有验证过
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            htmlPage = (HtmlPage) htmlPage.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("没能获取到网页");

        //移动鼠标，点击Pl展示按钮

        Object englishClick = htmlPage.getByXPath("//*[@id=\"i9\"]/span[3]").get(0);
        Object chineseClick = htmlPage.getByXPath("//*[@id=\"i12\"]/span[3]").get(0);
        HtmlAnchor enClick = (HtmlAnchor) englishClick;
        HtmlAnchor cnClick = (HtmlAnchor) chineseClick;
        try {
            enClick.click();
            cnClick.click();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Object> byXPath = htmlPage.getByXPath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[2]/c-wiz[1]/span/span/div/textarea");
        Object chineseText = htmlPage.getByXPath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[2]/c-wiz[2]/div[5]/div/div[1]/span[1]/span/span").get(0);

        HtmlTextInput input = (HtmlTextInput)byXPath.get(0);
        input.setText("Hello World");

        HtmlTextInput text = (HtmlTextInput) chineseText;
        String textText = text.getText();
        System.out.println(textText);


        //去taskNameMap取内容
        Set set = taskNameMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();

        }

    }
}
