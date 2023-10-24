package ynu.edu.textsummarizationsystem.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.mapper.NewsMapper;


import java.util.*;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsMapper newsMapper;

    /**
     * 分页并按照日期降序
     * 按照sentiment查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getList(@RequestParam("page") String page,
                              @RequestParam("sentiment") String sentiment) {

        Integer page1 = Integer.parseInt(page);
        IPage<News> recordIPage = new Page<>(page1, 1000); //分页(起始页为1)
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sentiment",sentiment);
        queryWrapper.orderByDesc("datetime");  //日期降序
        List<News> records = newsMapper.selectPage(recordIPage, queryWrapper).getRecords();
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
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCompany(@RequestParam("company") String company,
                                 @RequestParam("sentiment") String sentiment){
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company",company);
        queryWrapper.eq("sentiment",sentiment);
        List<News> records = newsMapper.selectList(queryWrapper);
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



}
