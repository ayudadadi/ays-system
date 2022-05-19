package com.marvin.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.marvin.service.MovieService;
import com.marvin.bean.Movie;
import com.marvin.mapper.MovieMapper;
import com.marvin.utils.HttpUtils;
import com.marvin.utils.OtherUtils;
import com.marvin.utils.PropertiesUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Marvin
 * @Description com.marvin.ayssystem
 * @create 2021-12-14 0:58
 */

@SpringBootTest
public class TestCrawler {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieMapper movieMapper;

    @Test
    void testCrawler() throws IOException, ParseException {
        long startTime = System.currentTimeMillis();    //获取开始时间

        // 工具类初始化
        HttpUtils httpUtils = new HttpUtils();

        // 查询链接
        String strUrl = "https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=电影&year_range=";

        // properties中对应key尾部
        String[] keyTail = new String[]{"2021","2020", "2019", "2010s", "2000s", "1990s"};

        // 数据库表名，时间范围的配置文件位置
        // 初始化配置文件助手
        PropertiesUtils pros = new PropertiesUtils();

        List<Movie> movieList = new ArrayList<>();

        // 对查询范围做循环
        for (int i = 0; i < keyTail.length ; i++) {
            // 设置查询范围和表名
            String rangeName = pros.getProperties("range_" + keyTail[i]);

            // 设置页数
            int page = 0;
            while (true) {
                // 设置链接
                String content = httpUtils.doGetHtml(strUrl + rangeName + "&start=" + (20 * page));
                if (page != 8 && content != null && !content.equals("")) {
                    String data = JSONObject.parseObject(content).getString("data");
                    page++;
                    if (data != null && !data.equals("")) {
                        JSONArray jsonArray = JSONArray.parseArray(data);
                        if (jsonArray.size() == 0) {
                            break;
                        } else {
                            for (int j = 0; j < jsonArray.size(); j++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                Movie movieInfo = new Movie();
                                // 获取当前系统时间
                                Date nowTime = new Date(new java.util.Date().getTime());
                                // 获取影片详情链接和图片链接
                                String url = jsonObject.getString("url");
                                String cover = jsonObject.getString("cover");

                                // 获取影片详情信息页面
                                Document doc = Jsoup.parse(httpUtils.doGetHtml(url));

                                // 获取影片id
                                String id = jsonObject.getString("id");

                                // 查找表中有无相同id数据存在

                                QueryWrapper<Movie> queryWrapper = new QueryWrapper();
                                queryWrapper.eq("id",id);
                                Integer count = movieMapper.selectCount(queryWrapper);

                                // 不存在则插入数据
                                if (count == 0) {
                                    // 设置可以在当前页面获取到的数据（json）
                                    setJsonData(httpUtils, movieInfo, jsonObject, id, url, cover);

                                    // 解析影片详情页面
                                    Element divInfo = doc.select("div#info").first();

                                    if (divInfo != null) {
                                        // 设置不可变信息
                                        setImmutableData(divInfo, movieInfo, nowTime);
                                        // 设置其他可变信息
                                        setMutableData(doc, movieInfo, nowTime);
                                        // 插入数据库中
                                        movieList.add(movieInfo);

//                                        int num = movieMapper.insert(movieInfo);
//                                        if(num == 1){
//                                            System.out.println("<--------- Insert success --------->");
//                                        }else{
//                                            System.out.println("<--------- Insert error --------->");
//                                        }

                                    }
                                } else if (count == 1) {
                                    setMutableData(doc, movieInfo, nowTime);
                                    // 更新数据库中的可变信息
                                    int num = movieMapper.updateById(movieInfo);
                                    if(num == 1){
                                        System.out.println("<--------- Update success --------->");
                                    }else{
                                        System.out.println("<--------- Update error --------->");
                                    }
                                }
                            }
                            // 休眠
                            try {
                                // 设置随机的2到5秒的随机数休眠，避免异常请求
                                int num1 = new Random().nextInt(10);
                                int num2 = (new Random().nextInt(3)+2)*10+num1;
                                int sleepMillis = num2 * 1000;
                                Thread.sleep(sleepMillis);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        break;
                    }
                }else{
                    break;
                }
            }


        }
        boolean flag = movieService.saveBatch(movieList);
        if(flag){
            System.out.println("电影信息已插入数据库！");
        }else {
            System.out.println("电影信息插入失败！");
        }

        long endTime = System.currentTimeMillis();    //获取结束时间
        float allTime = (endTime - startTime)/1000F;
        System.out.println("爬虫运行时间："  + allTime + " 秒 ");    //输出程序运行时间
    }

    /**
     * 设置可变的信息
     *
     * @param doc 影片详情信息页面
     * @param movieInfo 需要插入数据库的pojo实体
     * @param nowTime 系统当前时间，作为更新时间插入数据库
     */
    void setMutableData(Document doc, Movie movieInfo, Date nowTime) {
        // 设置评论条数
        Element reviewTemp = doc.select("#comments-section h2 a").first();
        if (reviewTemp != null) {
            long reviews = Long.parseLong(reviewTemp.text().split(" ")[1]);
            movieInfo.setReviews(reviews);
        }

        // 设置更新时间
        movieInfo.setUpdated(nowTime);
    }

    /**
     * 设置不可变的信息
     *
     * @param divInfo 影片详情页面的info信息
     * @param movieInfo 需要插入数据库的pojo实体
     * @param nowTime 系统当前时间，作为创建时间插入数据库
     * @throws ParseException
     */
    void setImmutableData(Element divInfo, Movie movieInfo, Date nowTime) throws ParseException {
        // 日期正则表达式
        String dateRegular = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 设置影片类型
        Elements types = divInfo.select("span[property=v:genre]");
        if (types != null) {
            StringBuilder typeBuilder = new StringBuilder();
            for (Element element : types) {
                typeBuilder.append(element.text() + "/");
            }
            if (typeBuilder.length() > 1) {
                typeBuilder.deleteCharAt(typeBuilder.length() - 1);
            }
            String type = typeBuilder.toString().trim();
            movieInfo.setType(type);
        }


        // 设置影片上映时间，获取并将日期转为sqlDate类型
        Element srcDate = divInfo.select("span[property=v:initialReleaseDate]").first();
        if (srcDate != null) {
            String dateTemp = OtherUtils.clearBracket(srcDate.text());
            if (dateTemp.matches(dateRegular)) {
                java.sql.Date sqlDate = new java.sql.Date(dateFormat.parse(dateTemp).getTime());
                movieInfo.setReleaseDate(sqlDate);
            } else {
                // 不符合规定的日期格式，不插入数据库中
                System.out.println(dateTemp);
            }
        }

        // 设置影片片长
        Element lengthTemp = divInfo.select("span[property=v:runtime]").first();
        if (lengthTemp != null) {
            String length = lengthTemp.text().trim();
            movieInfo.setLength(length);
        }

        // 设置影片上映国家
        String[] countryTemp1 = divInfo.text().split("制片国家/地区:");
        if (countryTemp1.length > 1) {
            String[] countryTemp2 = countryTemp1[1].split(" ");
            if (countryTemp2.length > 1) {
                String country = countryTemp2[1];
                movieInfo.setCountry(country);
            }
        }

        // 设置创建时间
        movieInfo.setCreated(nowTime);


    }

    /**
     * 设置Json中的信息，并获取影片封面图片
     *
     * @param httpUtils 获取页面内容的工具类
     * @param movieInfo 需要插入数据库的pojo实体
     * @param jsonObject 网页中，解析过的json格式的数据
     * @param id 影片id
     * @param url 影片url
     * @param cover 影片封面链接
     */
    static void setJsonData(HttpUtils httpUtils, Movie movieInfo, JSONObject jsonObject, String id, String url, String cover) {
        // 获取部分插入数据的具体值
        String directorsTemp = jsonObject.getString("directors");
        String castsTemp = jsonObject.getString("casts");
        Double rate = jsonObject.getDouble("rate");
        String title = jsonObject.getString("title");


        // 获取处理后的导演和主演字符串
        String directors = directorsTemp.substring(1, directorsTemp.length() - 1).replace("\"", "");
        String casts = castsTemp.substring(1, castsTemp.length() - 1).replace("\"", "");


        // 设置可以在页面直接获取的数据
        movieInfo.setId(id);
        movieInfo.setUrl(url);
        movieInfo.setCover(cover);
        movieInfo.setRate(rate);
        movieInfo.setTitle(title);
        movieInfo.setDirectors(OtherUtils.getAppropriateText(directors, 100));
        movieInfo.setCasts(OtherUtils.getAppropriateText(casts, 100));
    }
}
