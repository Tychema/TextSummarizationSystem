package ynu.edu.textsummarizationsystem.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.AttentionCompany;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.beans.counters;
import ynu.edu.textsummarizationsystem.dao.AttentionCompanyRepository;
import ynu.edu.textsummarizationsystem.dao.CountersRepository;
import ynu.edu.textsummarizationsystem.dao.NewsRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("attentioncompany")
public class AttentionCompanyController{

    @Autowired
    private AttentionCompanyRepository attentionCompanyRepository;
    @Autowired
    private CountersRepository countersRepository;

    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 取消收藏的company
     * state==200 表示取消收藏成功
     * state==201 表示公司名为空
     * state==202 表示你还未收藏该公司！
     * @param userid
     * @param company
     * @return
     */
//    http://localhost:8000/attentioncompany/deletecompany
    @RequestMapping(value = "/deletecompany", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deletecompany(@RequestParam("userid") String userid,
                                    @RequestParam("company") String company){
        JSONObject resp = new JSONObject();
        company = company.trim();
        if(company.length()==0){
            resp.put("state","201");
            resp.put("remind","公司名不能为空!");
            return resp;
        }
        Integer  userid1 = Integer.parseInt(userid);
        Criteria criteria = Criteria.where("userid").is(userid1).and("company").is(company);
        Query query = new Query(criteria);
        // 输出删除前的文档数量
        long countBeforeDelete = mongoTemplate.count(query, AttentionCompany.class);
        System.out.println(countBeforeDelete);
        if(countBeforeDelete == 0){
            resp.put("state", "202");
            resp.put("remind", "你还未收藏该公司！");
            return resp;
        }
        mongoTemplate.remove(query,AttentionCompany.class);
        resp.put("state","200");
        resp.put("remind","取消收藏成功！");
        return resp;
    }

    /**
     * 按照company查询所有新闻
     * state==200 表示查询成功
     * state==201 表示输入的company为空
     * state==202 表示没有该company的新闻
     * @param company
     * @return
     */
//    http://localhost:8000/attentioncompany/getcompanylist
    @RequestMapping(value = "/getcompanylist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getcompanynews(@RequestParam("company") String company){
        JSONObject resp = new JSONObject();
        company = company.trim();
        if(company.length()==0){
            resp.put("state","201");
            resp.put("remind","公司名不能为空");
            return resp;
        }
        Query query = Query.query(Criteria.where("company").is(company));
        List<News> records = mongoTemplate.find(query,News.class);
        Integer newscount=records.size();
        if(newscount==0){
            resp.put("state","202");
            resp.put("remind","没有该公司的新闻");
            return resp;
        }
        List<JSONObject> items_sentiment0 = new LinkedList<>();  //sentiment==0的新闻集合
        List<JSONObject> items_sentiment1 = new LinkedList<>();  //sentiment==1的新闻集合
        List<JSONObject> items_sentiment2 = new LinkedList<>();  //sentiment==2的新闻集合
        for (News record : records) {
            String sentiment = record.getSentiment();
            String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
            JSONObject item = JSONObject.parseObject(s);
            if("0".equals(sentiment)) {
                items_sentiment0.add(item);
            } else if("1".equals(sentiment)) {
                items_sentiment1.add(item);
            } else {
                items_sentiment2.add(item);
            }

        }
        resp.put("state","200");
        resp.put("company",company);
        resp.put("remind","查询成功");
        resp.put("attention_news_count",newscount);//该公司的新闻总数(没有新闻时，应该不能关注)
        resp.put("sentiment0_news",items_sentiment0);//sentiment==0的所有新闻
        resp.put("sentiment1_news",items_sentiment1);//sentiment==1的所有新闻
        resp.put("sentiment2_news",items_sentiment2);//sentiment==2的所有新闻
        return resp;

    }



    /**
     * 关注company
     * state==200 表示关注成功
     * state==201 表示输入的公司名为空
     * state==202 表示输入的公司已经关注过了
     * @param userid
     * @param company
     * @return
     */
//    http://localhost:8000/attentioncompany/attentioncompanylist
    @RequestMapping(value = "/attentioncompanylist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject attention(@RequestParam("userid") String userid,
                                @RequestParam("company") String company){
        JSONObject resp = new JSONObject();
        //去除首尾空格
        company =company.trim();
        if(company.length()==0) {
            resp.put("state","201");
            resp.put("remind","公司名不能为空");
            return resp;
        }
        Integer userid1 = Integer.parseInt(userid);
        //查询该公司是否已经关注
        Criteria criteria = Criteria.where("userid").is(userid1).and("company").is(company);
        Query query = new Query(criteria);
        List<AttentionCompany> attentionCompanies = mongoTemplate.find(query,AttentionCompany.class);
        if(attentionCompanies.size()!=0){
            resp.put("state","202");
            resp.put("remind","该公司已经关注过了，请勿重复关注！");
        }else{
            //获得自增主键值
            List<counters> all = mongoTemplate.findAll(counters.class);
            int id = all.get(0).getAttentioncompany_value();
            Query query2 = Query.query(Criteria.where("_id").is(all.get(0).getId()));
            Update update = new Update();
            update.set("attentioncompany_value", id+1);
            mongoTemplate.updateFirst(query2, update, counters.class);

            AttentionCompany attentionCompany = new AttentionCompany();
            attentionCompany.setCompany(company);
            attentionCompany.setUserid(userid1);
            attentionCompany.setId(id+1);
            attentionCompanyRepository.save(attentionCompany);
            resp.put("state","200");
            resp.put("remind","关注成功");
        }
        return resp;
    }

    /**
     * 根据userid查询当前用户关注公司的所有新闻
     * state==200  表示查询成功
     * state==201  表示用户未关注任何公司
     * @param userid
     * @return
     */
//    http://localhost:8000/attentioncompany/getattentionlist
    @RequestMapping(value = "/getattentionlist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getCompanyList(@RequestParam("userid") String userid){

        Integer userid1 = Integer.parseInt(userid);
        Query query = Query.query(Criteria.where("userid").is(userid1));
        //用户关注的公司列表
        List<AttentionCompany> attentionCompanies = mongoTemplate.find(query,AttentionCompany.class);
        JSONObject resp = new JSONObject();
        if(attentionCompanies.size() == 0) {
            resp.put("state","201");
            resp.put("remind","您还未关注任何公司！");
        }else {
            //查询关注的公司新闻
            Integer newscount = 0;
            List<JSONObject> items_sentiment0 = new LinkedList<>();  //sentiment==0的新闻集合
            List<JSONObject> items_sentiment1 = new LinkedList<>();  //sentiment==1的新闻集合
            List<JSONObject> items_sentiment2 = new LinkedList<>();  //sentiment==2的新闻集合
            for(AttentionCompany company:attentionCompanies){
                Query query2 = Query.query(Criteria.where("company").is(company.getCompany()));
                List<News> records = mongoTemplate.find(query2,News.class);
                newscount = newscount+records.size();
                for (News record : records) {
                    String sentiment = record.getSentiment();
                    String s = JSONObject.toJSONString(record);//转为JSON格式的字符串
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
            resp.put("attention_news_count",newscount);//关注的新闻总数
            resp.put("sentiment0_news",items_sentiment0);//sentiment==0的所有新闻
            resp.put("sentiment1_news",items_sentiment1);//sentiment==1的所有新闻
            resp.put("sentiment2_news",items_sentiment2);//sentiment==2的所有新闻
        }
        return resp;

    }


    /**
     * 查询用户是否关注当前company
     * state==200  表示未关注
     * state==201  表示已关注
     * @param userid
     * @param company
     * @return
     */
    //http://localhost:8000/attentioncompany/whetherattention
    @RequestMapping(value = "/whetherattention", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject whetherattention(@RequestParam("userid") String userid,
                                       @RequestParam("company") String company){
        Integer userid1 = Integer.parseInt(userid);
        JSONObject resp = new JSONObject();
        Query query = Query.query(Criteria.where("userid").is(userid1).and("company").is(company));
        List<AttentionCompany> attentionCompanies = mongoTemplate.find(query,AttentionCompany.class);
        if(attentionCompanies.size()==0){
            resp.put("state","200");
            resp.put("remind","该company还未关注");
        }else{
            resp.put("state","201");
            resp.put("remind","该company已关注了");
        }
        return resp;
    }

}
