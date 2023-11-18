package ynu.edu.textsummarizationsystem.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.AttentionCompany;
import ynu.edu.textsummarizationsystem.beans.CollectNews;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.beans.counters;
import ynu.edu.textsummarizationsystem.dao.CollectNewsRepository;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("collectnews")
public class CollectNewsController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CollectNewsRepository collectNewsRepository;

    /**
     * 根据newid收藏新闻
     * state==200 表示收藏成功
     * state==202 表示输入的新闻已经收藏过了
     * @param userid
     * @param newid
     * @return
     */
//    http://localhost:8000/collectnews/collectnewsById
    @RequestMapping(value = "/collectnewsById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject collectnewsById(@RequestParam("userid") String userid,
                                      @RequestParam("newid") String newid){

        JSONObject resp = new JSONObject();

        //查询该新闻是否已经收藏
        Criteria criteria = Criteria.where("newid").is(Integer.parseInt(newid));
        Query query = new Query(criteria);
        List<CollectNews> collectNewsList = mongoTemplate.find(query,CollectNews.class);
        if(collectNewsList.size()!=0){
            resp.put("state","202");
            resp.put("remind","该新闻已经收藏过了，请勿重复收藏！");
        }else{
            //获得自增主键值
            List<counters> all = mongoTemplate.findAll(counters.class);
            int id = all.get(0).getCollectnews_value();
            Query query2 = Query.query(Criteria.where("_id").is(all.get(0).getId()));
            Update update = new Update();
            update.set("collectnews_value", id+1);
            mongoTemplate.updateFirst(query2, update, counters.class);

            CollectNews collectNews = new CollectNews();
            collectNews.setId(id+1);
            collectNews.setNewid(Integer.parseInt(newid));
            collectNews.setUserid(Integer.parseInt(userid));
            collectNewsRepository.save(collectNews);
            resp.put("state","200");
            resp.put("remind","收藏成功");
        }
        return resp;
    }

    /**
     * 根据userid查询当前用户收藏的所有新闻
     * state==200  表示查询成功
     * state==201  表示用户未收藏任何新闻
     * @param userid
     * @return
     */
//    http://localhost:8000/collectnews/getnewslist
    @RequestMapping(value = "/getnewslist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getNewsList(@RequestParam("userid") String userid){

        Integer userid1 = Integer.parseInt(userid);
        Query query = Query.query(Criteria.where("userid").is(userid1));
        //用户关注的新闻列表
        List<CollectNews> collectNewsList = mongoTemplate.find(query,CollectNews.class);
        JSONObject resp = new JSONObject();
        if(collectNewsList.size() == 0) {
            resp.put("state","201");
            resp.put("remind","您还未收藏任何新闻！");
        }else {
            //查询关注的新闻
            Integer newscount = 0;
            List<JSONObject> items_sentiment0 = new LinkedList<>();  //sentiment==0的新闻集合
            List<JSONObject> items_sentiment1 = new LinkedList<>();  //sentiment==1的新闻集合
            List<JSONObject> items_sentiment2 = new LinkedList<>();  //sentiment==2的新闻集合
            for(CollectNews collectNewsnews:collectNewsList){
                Query query2 = Query.query(Criteria.where("_id").is(collectNewsnews.getNewid()));
                List<News> records = mongoTemplate.find(query2,News.class);
                newscount = newscount+records.size();
                for (News record : records) {
                    String sentiment = record.getSentiment();
                    String s = com.alibaba.fastjson2.JSONObject.toJSONString(record);//转为JSON格式的字符串
                    JSONObject item = JSONObject.parseObject(s);
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
            resp.put("attention_news_count",newscount);//收藏的新闻总数
            resp.put("sentiment0_news",items_sentiment0);//sentiment==0的所有新闻
            resp.put("sentiment1_news",items_sentiment1);//sentiment==1的所有新闻
            resp.put("sentiment2_news",items_sentiment2);//sentiment==2的所有新闻
        }
        return resp;

    }


    /**
     * 查询用户是否收藏当前新闻
     * state==200  表示未收藏
     * state==201  表示已收藏
     * @param userid
     * @param newid
     * @return
     */
    //http://localhost:8000/collectnews/whethercollect
    @RequestMapping(value = "/whethercollect", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject whetherattention(@RequestParam("userid") String userid,
                                       @RequestParam("newid") String newid){
        Integer userid1 = Integer.parseInt(userid);
        JSONObject resp = new JSONObject();
        Query query = Query.query(Criteria.where("userid").is(userid1).and("newid").is(Integer.parseInt(newid)));
        List<CollectNews> collectNews = mongoTemplate.find(query,CollectNews.class);
        if(collectNews.size()==0){
            resp.put("state","200");
            resp.put("remind","该新闻还未收藏");
        }else{
            resp.put("state","201");
            resp.put("remind","该新闻已收藏了");
        }
        return resp;
    }

    /**
     * 取消收藏的新闻
     * state==200 表示取消收藏成功
     * state==202 表示你还未收藏该新闻！
     * @param userid
     * @param newid
     * @return
     */
//    http://localhost:8000/collectnews/deletecollect
    @RequestMapping(value = "/deletecollect", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deletecollect(@RequestParam("userid") String userid,
                                    @RequestParam("newid") String newid){
        JSONObject resp = new JSONObject();
        Integer  userid1 = Integer.parseInt(userid);
        Criteria criteria = Criteria.where("userid").is(userid1).and("newid").is(Integer.parseInt(newid));
        Query query = new Query(criteria);
        // 输出删除前的文档数量
        long countBeforeDelete = mongoTemplate.count(query, CollectNews.class);
        if(countBeforeDelete == 0){
            resp.put("state", "202");
            resp.put("remind", "你还未收藏该公司！");
            return resp;
        }
        mongoTemplate.remove(query,CollectNews.class);
        resp.put("state","200");
        resp.put("remind","取消收藏成功！");
        return resp;
    }




}
