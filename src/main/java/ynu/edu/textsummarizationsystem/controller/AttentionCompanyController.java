package ynu.edu.textsummarizationsystem.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.AttentionCompany;
import ynu.edu.textsummarizationsystem.beans.News;
import ynu.edu.textsummarizationsystem.mapper.AttentionCompanyMapper;
import ynu.edu.textsummarizationsystem.mapper.NewsMapper;

import java.util.LinkedList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("attentioncompany")
public class AttentionCompanyController {
    @Autowired
    private AttentionCompanyMapper attentionCompanyMapper;
    @Autowired
    private NewsMapper newsMapper;

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
        QueryWrapper<AttentionCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("company",company);
        Integer t = attentionCompanyMapper.delete(queryWrapper);
        if(t==0) {
            resp.put("state", "202");
            resp.put("remind", "你还未收藏该公司！");
            return resp;
        }
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
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company",company);
        List<News> records = newsMapper.selectList(queryWrapper);
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
        QueryWrapper<AttentionCompany> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("userid",userid1);
        queryWrapper2.eq("company",company);
        List<AttentionCompany> attentionCompanies = attentionCompanyMapper.selectList(queryWrapper2);
        if(attentionCompanies.size()!=0){
            resp.put("state","202");
            resp.put("remind","该公司已经关注过了，请勿重复关注！");
        }else{
            AttentionCompany attentionCompany = new AttentionCompany(userid1, company);
            attentionCompanyMapper.insert(attentionCompany);
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
        QueryWrapper<AttentionCompany> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid1);
        //用户关注的公司列表
        List<AttentionCompany> attentionCompanies = attentionCompanyMapper.selectList(queryWrapper);
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
            QueryWrapper<News> queryWrapper2 = new QueryWrapper<>();
            for(AttentionCompany company:attentionCompanies){
                queryWrapper2.clear();
                queryWrapper2.eq("company",company.getCompany());
                List<News> records = newsMapper.selectList(queryWrapper2);
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
    @RequestMapping(value = "/whetherattention", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject whetherattention(@RequestParam("userid") String userid,
                                       @RequestParam("company") String company){
        Integer userid1 = Integer.parseInt(userid);
        JSONObject resp = new JSONObject();
        QueryWrapper<AttentionCompany> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("userid",userid1);
        queryWrapper2.eq("company",company);
        List<AttentionCompany> attentionCompanies = attentionCompanyMapper.selectList(queryWrapper2);
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
