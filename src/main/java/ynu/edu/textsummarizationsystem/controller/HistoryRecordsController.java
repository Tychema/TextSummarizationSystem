package ynu.edu.textsummarizationsystem.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.AttentionCompany;
import ynu.edu.textsummarizationsystem.beans.HistoryRecords;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.beans.counters;
import ynu.edu.textsummarizationsystem.dao.HistoryRecordsRepository;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("history")
public class HistoryRecordsController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private HistoryRecordsRepository historyRecordsRepository;


    /**
     * 根据newid存入历史记录
     * state==200 表示成功
     * state==202 表示输入的新闻已经存过了
     * @param userid
     * @param newid
     * @return
     */
//    http://localhost:8000/history/historynewsById
    @RequestMapping(value = "/historynewsById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject historynewsById(@RequestParam("userid") String userid,
                                      @RequestParam("newid") String newid,
                                      @RequestParam("clickTime") String clickTime){
        //点击数加1
        Query query5 = Query.query(Criteria.where("_id").is(Integer.parseInt(newid)));
        List<News> all1 = mongoTemplate.find(query5, News.class);
        int click = all1.get(0).getClick();
        Query query4 = Query.query(Criteria.where("_id").is(Integer.parseInt(newid)));
        Update update1 = new Update();
        update1.set("click", click+1);
        mongoTemplate.updateFirst(query4, update1, News.class);


        JSONObject resp = new JSONObject();

        //查询该新闻是否存过
        Criteria criteria = Criteria.where("newid").is(Integer.parseInt(newid)).and("userid").is(Integer.parseInt(userid));
        Query query = new Query(criteria);
        List<HistoryRecords> historyRecords = mongoTemplate.find(query,HistoryRecords.class);
        System.out.println(historyRecords);
        if(historyRecords.size()!=0){
            Query query2 = Query.query(Criteria.where("_id").is(historyRecords.get(0).getId()));
            Update update = new Update();
            update.set("clicktime", clickTime);
            mongoTemplate.updateFirst(query2, update, HistoryRecords.class);
            resp.put("remind","历史记录更新成功");
        }else{
            //获得自增主键值
            List<counters> all = mongoTemplate.findAll(counters.class);
            int id = all.get(0).getHistory_value();
            Query query3 = Query.query(Criteria.where("_id").is(all.get(0).getId()));
            Update update = new Update();
            update.set("history_value", id+1);
            mongoTemplate.updateFirst(query3, update, counters.class);

            HistoryRecords historyRecords1 = new HistoryRecords();
            historyRecords1.setId(id+1);
            historyRecords1.setNewid(Integer.parseInt(newid));
            historyRecords1.setUserid(Integer.parseInt(userid));
            historyRecords1.setClicktime(clickTime);
            historyRecordsRepository.save(historyRecords1);
            resp.put("remind","历史记录存储成功");
        }
        resp.put("state","200");

        return resp;
    }


    /**
     * 根据userid查询当前用户所有的历史记录
     * state==200  表示查询成功
     * state==201  表示用户没有历史记录
     * @param userid
     * @return
     */
//    http://localhost:8000/history/getnewslist
    @RequestMapping(value = "/getnewslist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getNewsList(@RequestParam("userid") String userid){

        Integer userid1 = Integer.parseInt(userid);
        Query query = Query.query(Criteria.where("userid").is(Integer.parseInt(userid)));
        //用户历史记录列表
        List<HistoryRecords> historyRecords = mongoTemplate.find(query,HistoryRecords.class);
        JSONObject resp = new JSONObject();
        if(historyRecords.size() == 0) {
            resp.put("state","201");
            resp.put("remind","您还未浏览过任何新闻！");
        }else {
            //查询历史的新闻
            Integer newscount = 0;
            List<JSONObject> items_sentiment0 = new LinkedList<>();  //sentiment==0的新闻集合
            List<JSONObject> items_sentiment1 = new LinkedList<>();  //sentiment==1的新闻集合
            List<JSONObject> items_sentiment2 = new LinkedList<>();  //sentiment==2的新闻集合
            for(HistoryRecords historyRecords1:historyRecords){
                Query query2 = Query.query(Criteria.where("_id").is(historyRecords1.getNewid()));
                List<News> records = mongoTemplate.find(query2,News.class);
                newscount = newscount+records.size();
                for (News record : records) {
                    String sentiment = record.getSentiment();
                    String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
                    JSONObject item = JSONObject.parseObject(s);
                    item.put("clicktime",historyRecords1.getClicktime());
                    if("0".equals(sentiment)) {
                        items_sentiment0.add(item);
                    } else if("1".equals(sentiment)) {
                        items_sentiment1.add(item);
                    } else {
                        items_sentiment2.add(item);
                    }

                }
            }
            resp.put("state","200");
            resp.put("attention_news_count",newscount);//历史新闻总数
            resp.put("sentiment0_news",items_sentiment0);//sentiment==0的所有新闻
            resp.put("sentiment1_news",items_sentiment1);//sentiment==1的所有新闻
            resp.put("sentiment2_news",items_sentiment2);//sentiment==2的所有新闻
        }
        return resp;

    }





}
