package com.example.demo;

import com.example.demo.model.*;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class AutoClient {
    private static Set<Integer> indexSet = Collections.synchronizedSet(new HashSet<>());

    private Map<String, List<Contest>> contestMap = Collections.synchronizedMap(new HashMap<>());

//    private List<Contest> contestList = Collections.synchronizedList(new ArrayList<>());  February 13th

    public Map<String, List<Contest>> getContestMap() {
        return contestMap;
    }

    public void setContestMap(Map<String, List<Contest>> contestMap) {
        this.contestMap = contestMap;
    }

//    public List<Contest> getContestList() {
//        return contestList;
//    }

//    public void setContestList(List<Contest> contestList) {
//        this.contestList = contestList;
//    }

    @PostConstruct
    @Scheduled(cron = "0 0/8 * * * ? ")
    public void run() throws Exception {
        indexSet.add(0);
        indexSet.add(1);
        indexSet.add(2);
        indexSet.add(3);
        for (int betTypeIndex = 0; betTypeIndex < 4; betTypeIndex++) {

            Iterator<Integer> iterator = indexSet.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                iterator.remove();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WebClient webClient = null;
                        try {
                            webClient = new WebClient(BrowserVersion.CHROME);
                            webClient.getOptions().setJavaScriptEnabled(true);
                            webClient.getOptions().setCssEnabled(false);
                            webClient.getOptions().setActiveXNative(false);
                            webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
                            webClient.getOptions().setRedirectEnabled(true);
                            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
                            webClient.getOptions().setThrowExceptionOnScriptError(false);
                            webClient.getOptions().setTimeout(500000);
                            webClient.waitForBackgroundJavaScript(15000);
                            System.out.println("正在获取网页");
                            HtmlPage htmlPage = null;
                            try {
                                htmlPage = webClient.getPage("");
                                //用于刷新页面
//            webClient.getWebWindows().get(0).getHistory().go(0);
                                //执行刷新,返回的是一个page接口，这个刷新还没有验证过
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                htmlPage = (HtmlPage) htmlPage.refresh();
                            } catch (IOException e) {
                                System.out.println("没能获取到网页");
                                e.printStackTrace();
                            }

                            //移动鼠标，点击Pl展示按钮
                            DomElement byId = htmlPage.getElementById("format-toggle-text");

                            //byiD获得焦点之后，下方js事件触发，可点击内容出现
                            byId.focus();

                            List<Contest> contests = new ArrayList<>();
                            HtmlAnchor elementById = (HtmlAnchor) htmlPage.getElementById("formatSelector" + (next + 1));
                            try {
                                elementById.click();

                                //试图获取 exp-ard 然后点击出现更多 betType和PL
                                List<HtmlSpan> xPath = htmlPage.getByXPath("//span[contains(@class,'exp-ard')]");

//        List<Object> byXPath = htmlPage.getByXPath("//*[@id=\"event2029\"]/div[2]/div[3]/table/tbody/tr[1]/td[16]/a/span/span");
                                for (HtmlSpan o : xPath) {
                                    HtmlSpan span = o;
                                    try {
                                        span.click();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Document parse = Jsoup.parse(htmlPage.asXml());
                                String betType = parse.getElementById("formatSelector" + (next + 1)).text();

                                //获取到所有的赛事的信息 elements是所有网页可参与竞猜比赛集合
                                System.out.println(System.currentTimeMillis() + "------开始分析时间");
                                Elements elements = parse.getElementsByClass("table-outer-wrapper");
                                for (int k = 0; k < elements.size(); k++) {
                                    //此处可以new一个event对象，装event信息
                                    Contest contest = new Contest();

                                    //获得了比赛赛事
                                    String fightName = elements.get(k).getElementsByClass("table-header").first().getElementsByTag("a").first().text();
                                    String fightDate = "";
                                    try {
                                        //获得了比赛日期--注意，这里日期可能为空，也就是没有span标签，会获取失败，做异常判断，如果是异常，则日期为空
                                        fightDate = elements.get(k).getElementsByClass("table-header").first().getElementsByTag("span").first().text();
                                    } catch (Exception e) {
                                        //获取日期异常，可能为空
                                        System.out.println("获取日期异常");
//                e.printStackTrace();
                                    }
//            System.out.println("****");
//            System.out.println(fightName + "比赛名称");
                                    contest.setContestName(fightName);
//            System.out.println(fightDate + "比赛日期");
                                    //下方日期需要转换，暂时不处理
//                contest.setStartTime(DateFormatUtil.yMDStrToDate(fightDate));
                                    //获取所有竞猜公司--提供赔率的公司,thead下其实有很多 th， 为了省事直接取text 其实可以更谨慎。（最后一个th其实不是赔率公司 而是 Props 赔率走势按钮）
                                    Elements thead = elements.get(k).getElementsByTag("thead").get(1).select("tr").get(0).select("th");
                                    List<GamblCompany> gamblCompanyList = new ArrayList<>();
                                    for (Element element1 : thead) {
                                        GamblCompany com = new GamblCompany();
//                com.setCompanyName(element1.select("th").select("th").text());
                                        com.setCompanyName(element1.text());
                                        gamblCompanyList.add(com);
                                    }
//            String html = elements.get(k).html();
//            System.out.println(html);
                                    System.out.println("--------间隔符--------");
//            String text = element.getElementsByClass("even").select("span").first().text();
                                    Elements elementsByClass = elements.get(k).getElementsByClass("table-scroller");
//            Elements elementsByOdd = elements.get(k).getElementsByClass("table-scroller");
                                    List<Finghting> finghtingList = new ArrayList<>();
                                    contest.setFinghtingList(finghtingList);
                                    for (int i = 0; i < elementsByClass.size(); i++) {

                                        //所有的tr 规律是 先even odd ，然后 pr 然后 pr_odd  pr  pr_odd  一直到下一场比赛；由于已经取走了even odd，所有余下的另外处理或者重写
                                        Elements trElements = elementsByClass.get(i).getElementsByTag("tbody").get(i).getElementsByTag("tr");
                                        List<Integer> envenIndexList = new ArrayList<>();
//                                        List<Integer> oddIndexList = new ArrayList<>();
//                                        List<Integer> prIndexList = new ArrayList<>();
//                                        List<Integer> prOddIndexList = new ArrayList<>();

                                        for (int w = 0; w < trElements.size(); w++) {
                                            if (trElements.get(w).className().equals("even")) {
                                                envenIndexList.add(w);
//                                            } else if (trElements.get(w).className().equals("odd")) {
//                                                oddIndexList.add(w);
//                                            } else if (trElements.get(w).className().equals("pr")) {
//                                                prIndexList.add(w);
//                                            } else if (trElements.get(w).className().equals("pr-odd")) {
//                                                prOddIndexList.add(w);
                                            }
                                        }
                                        for (int e = 0; e < envenIndexList.size(); e++) {
                                            Finghting f = new Finghting();
                                            f.setContestName(contest.getContestName());
                                            Fighter fighterA = new Fighter();
                                            String peopleAStr = trElements.get(envenIndexList.get(e)).select("span").first().text();
                                            String peopleBStr = trElements.get(envenIndexList.get(e) + 1).select("span").first().text();

                                            fighterA.setFighterName(peopleAStr);
                                            f.setPeopleA(fighterA);
                                            Fighter fighterB = new Fighter();
                                            fighterB.setFighterName(peopleBStr);
                                            f.setPeopleB(fighterB);

                                            f.setGamblingList(new ArrayList<>());


                                            Elements evenselectTD = trElements.get(envenIndexList.get(e)).select("td");
                                            Elements oddselectTD = trElements.get(envenIndexList.get(e) + 1).select("td");

                                            //如果是最后一个even，则无法用 e+1，因为会越界
                                            if (e == envenIndexList.size() - 1) {
                                                int lastLength = trElements.size() - 2 - envenIndexList.get(e);
                                                for (int z = 0; z < lastLength; z = z + 2) {
                                                    if (z == 0) {
                                                        //开始获取PL 第一个默认胜/负
                                                        Gambling lastgambling = new Gambling();
                                                        lastgambling.setMaybeResultTypeToA(fighterA.getFighterName() + " will win");
                                                        lastgambling.setMaybeResultTypeToB(fighterB.getFighterName() + " will lose");
                                                        lastgambling.setOddsList(new ArrayList<>());
                                                        Elements lasttdList = trElements.get(envenIndexList.get(e)).select("td");
                                                        for (int w = 0; w < lasttdList.size() - 3; w++) {
                                                            Date date = new Date(System.currentTimeMillis());
                                                            Odds odds = new Odds();
                                                            odds.setPeilvA(evenselectTD.get(w).text());
                                                            odds.setPeilvB(oddselectTD.get(w).text());
                                                            odds.setGamblCompany(gamblCompanyList.get(w));
                                                            odds.setOddsTime(date);
                                                            odds.setBetTypeEnum(betType);
                                                            lastgambling.getOddsList().add(odds);
                                                        }
                                                        f.getGamblingList().add(lastgambling);
                                                    } else {
                                                        Gambling gamblingIn = new Gambling();
                                                        try {
                                                            gamblingIn.setMaybeResultTypeToA(fighterA.getFighterName() + " " + trElements.get(envenIndexList.get(e) + z - 1).select("th").first().text());
                                                        } catch (Exception e0) {
                                                            System.out.println("A越界" + (envenIndexList.get(e) + z - 1));
                                                        }

                                                        try {
                                                            gamblingIn.setMaybeResultTypeToB(fighterB.getFighterName() + "  " + trElements.get(envenIndexList.get(e) + z).select("th").first().text());
                                                        } catch (Exception e2) {
                                                            System.out.println("B越界" + (envenIndexList.get(e) + z));
                                                        }

                                                        gamblingIn.setOddsList(new ArrayList<>());

                                                        Elements prTD = trElements.get(envenIndexList.get(e) + z).select("td");
                                                        Elements prOddTD = trElements.get(envenIndexList.get(e) + z + 1).select("td");

                                                        for (int b = 0; b < evenselectTD.size() - 3; b++) {
                                                            Odds odds = new Odds();
                                                            odds.setPeilvA(prTD.get(b).text());
                                                            odds.setPeilvB(prOddTD.get(b).text());
                                                            odds.setGamblCompany(gamblCompanyList.get(b));
                                                            odds.setBetTypeEnum(betType);
                                                            gamblingIn.getOddsList().add(odds);
                                                        }
                                                        f.getGamblingList().add(gamblingIn);
                                                    }

                                                }

                                            } else {
                                                int length = envenIndexList.get(e + 1) - envenIndexList.get(e);
                                                for (int r = 0; r < length - 1; r = r + 2) {
                                                    if (r == 0) {
                                                        //开始获取PL 第一个默认胜/负
                                                        Gambling gambling = new Gambling();
                                                        gambling.setMaybeResultTypeToA(fighterA.getFighterName() + " will win");
                                                        gambling.setMaybeResultTypeToB(fighterB.getFighterName() + " will lose");
                                                        gambling.setOddsList(new ArrayList<>());
                                                        Elements tdList = trElements.get(envenIndexList.get(e)).select("td");
                                                        for (int w = 0; w < tdList.size() - 3; w++) {
                                                            Date date = new Date(System.currentTimeMillis());
                                                            Odds odds = new Odds();
                                                            odds.setPeilvA(evenselectTD.get(w).text());
                                                            odds.setPeilvB(oddselectTD.get(w).text());
                                                            odds.setGamblCompany(gamblCompanyList.get(w));
                                                            odds.setOddsTime(date);
                                                            odds.setBetTypeEnum(betType);
                                                            gambling.getOddsList().add(odds);
                                                        }
                                                        f.getGamblingList().add(gambling);
                                                    } else {
                                                        Gambling gamblingIn = new Gambling();
                                                        try {
                                                            gamblingIn.setMaybeResultTypeToA(fighterA.getFighterName() + " " + trElements.get(envenIndexList.get(e) + r).select("th").first().text());
                                                        } catch (Exception e0) {
                                                            System.out.println("C越界" + (envenIndexList.get(e) + r));
                                                        }

                                                        try {
                                                            gamblingIn.setMaybeResultTypeToB(fighterB.getFighterName() + "  " + trElements.get(envenIndexList.get(e) + r + 1).select("th").first().text());
                                                        } catch (Exception e2) {
                                                            System.out.println("D越界" + (envenIndexList.get(e) + r + 1));
                                                        }

                                                        gamblingIn.setOddsList(new ArrayList<>());

                                                        Elements prTD = trElements.get(envenIndexList.get(e) + r).select("td");
                                                        Elements prOddTD = trElements.get(envenIndexList.get(e) + r + 1).select("td");

                                                        for (int b = 0; b < evenselectTD.size() - 3; b++) {
                                                            Odds odds = new Odds();
                                                            odds.setPeilvA(prTD.get(b).text());
                                                            odds.setPeilvB(prOddTD.get(b).text());
                                                            odds.setGamblCompany(gamblCompanyList.get(b));
                                                            odds.setBetTypeEnum(betType);
                                                            gamblingIn.getOddsList().add(odds);
                                                        }
                                                        f.getGamblingList().add(gamblingIn);
                                                    }
                                                }
                                            }

                                            contest.getFinghtingList().add(f);
                                        }


                                        //下方是betType
//                String text = elements.get(k).getElementsByClass("pr").get(i).select("th").text();
//                gamb.setMaybeType(text);


                                        //下方pl赋值，必须点击odd下的 pr标签内 pr-odd <span>下的<span class="exp-ard">才能够获取到下方的其他Pl列表，点开之后，出现具体的内容

//                Elements prEle = elements.get(k).getElementsByClass("pr");
//                Elements prEleOdd = elements.get(k).getElementsByClass("pr-odd");
//                for (int t = 0; t < prEle.size(); t++) {
//                    Elements td = prEle.get(t).select("td");
//                    if (td != null) {
//                        for (int p = 0; p < td.size(); p++) {
//                            Odds odd = new Odds();
//                            String text = "";
//                            try {
//                                text = td.get(p).select("a").get(0).select("span").get(0).select("span").get(0).text();
//                                odd.setPeilvA(text);
//                            } catch (Exception e) {
//                                odd.setPeilvA(text);
//                            }
//                            String textB = "";
//                            try {
//                                textB = prEleOdd.get(t).select("td").select("a").get(0).select("span").get(0).select("span").get(0).text();
//                                odd.setPeilvB(textB);
//                            } catch (Exception e) {
//                                odd.setPeilvB(textB);
//                            }
//                            odd.setOddsTime(new Date(System.currentTimeMillis()));
////                            oddsSet.add(odd);
//                        }
//                    }
//                }


//                Elements elementsTd = elements.get(k).getElementsByClass("pr").get(i).select("td");
//                Elements elementsTdOdd = elements.get(k).getElementsByClass("pr-odd").get(i).select("td");
//                for (int j = 0; j < elementsTd.size(); j++) {
//                    Odds odds = new Odds();
////                    odds.setBetTypeEnum(text);
//                    odds.setOddsTime(new Date(System.currentTimeMillis()));
//                    String peilvA = elementsTd.get(j).select("a").get(0).select("span").get(0).select("span").get(0).text();
//                    odds.setPeilvA(peilvA);
//                    String peilvB = elementsTdOdd.get(j).select("a").get(0).select("span").get(0).select("span").get(0).text();
//                    odds.setPeilvB(peilvB);
//                    gamb.getOddsAList().add(odds);
//                }
//                f.getGamblingList().add(gamb);
                                    }


//                                contestList.add(contest);
                                    contests.add(contest);
                                    //关于赔率的获取 even 是选手A 相关信息 包括选手名称和它的赔率
                                    //关于赔率的获取 odd 是选手B 相关信息 包括选手名称和它的赔率
                                    // 下方的pr是指的是选手A 不同的竞猜方式 比如一回合结束-   pr下有很多td 因为有不同公司给不同的赔率
                                    // 下方的pr-odd是指的是选手B 不同的竞猜方式 比如一回合结束-   pr下有很多td 因为有不同公司给不同的赔率
                                }
                                System.out.println("--分析完成---" + System.currentTimeMillis());
                                contestMap.put(betType, contests);

                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("-----点击PL异常-----");
                            }


//        List<Object> elementByIds = htmlPage.getByXPath("//*[@id=\"formatSelector2\"]/text()");

//        System.out.println(elementById.asText());

                            System.out.println(System.currentTimeMillis() + "------结束分析时间");
                        } catch (Exception e) {
//        System.out.println(contestList.toString());
                            webClient.close();
                            System.out.println("-------一次计算完成-------");
                        }
                    }
                }).start();
            }
        }
    }
}
