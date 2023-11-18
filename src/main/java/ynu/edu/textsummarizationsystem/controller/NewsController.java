package ynu.edu.textsummarizationsystem.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.dao.NewsRepository;


import java.util.*;


@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("news")
@Service
public class NewsController {
    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 分页并按照日期降序
     * 按照sentiment查询
     * @param page
     * @return
     */
//    http://localhost:8000/news/getList
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getList(@RequestParam("page") String page,
                              @RequestParam("sentiment") String sentiment) {

        Integer page1 = Integer.parseInt(page);
        Query query = new Query(Criteria.where("sentiment").is(sentiment));
        Sort sort = Sort.by(Sort.Direction.DESC, "datetime");
        query.with(sort);
        PageRequest pageRequest = PageRequest.of(page1 - 1, 1000);
        List<News> records = mongoTemplate.find(query,News.class);
        List<JSONObject> items = new LinkedList<>();
        for (News record : records) {
            String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
            JSONObject item = JSONObject.parseObject(s);
            items.add(item);
        }
        JSONObject resp = new JSONObject();
        resp.put("records_count", records.size());
        resp.put("page", page1);
        resp.put("record", items);

        return resp;
    }


    /**
     * 按照公司名称查询
     * @param company
     * @return
     */
    //    http://localhost:8000/news/getCompanyList
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCompany(@RequestParam("company") String company,
                                 @RequestParam("sentiment") String sentiment){

        Query query = Query.query(Criteria.where("company").is(company).and("sentiment").is(sentiment));
        List<News> records = mongoTemplate.find(query,News.class);
        JSONObject resp = new JSONObject();
        if(records.size() == 0){
            resp.put("state","404");
        }else {
            List<JSONObject> items = new LinkedList<>();
            for (News record : records) {
                String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
                JSONObject item = JSONObject.parseObject(s);
                items.add(item);
            }

            resp.put("collect_record", items);
            resp.put("state","200");
        }


        return resp;
    }

    /**
     * 点击数排行榜(前100条新闻)
     * @return
     */
//    http://localhost:8000/news/rankinglist
    @RequestMapping(value = "/rankinglist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject hostlist(){
        Query query = new Query();
        Sort sort = Sort.by(Sort.Direction.DESC, "click");
        query.with(sort);
        PageRequest pageRequest = PageRequest.of(0, 100);
        List<News> records = mongoTemplate.find(query,News.class);
        List<JSONObject> items = new LinkedList<>();
        for (News record : records) {
            String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
            JSONObject item = JSONObject.parseObject(s);
            items.add(item);
        }
        JSONObject resp = new JSONObject();
        resp.put("rankinglist", items);
        return resp;


    }



}
