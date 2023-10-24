package ynu.edu.textsummarizationsystem.controller;


import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.ContextCollect;
import ynu.edu.textsummarizationsystem.mapper.ContextCollectMapper;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("contextCollect")
@CrossOrigin(origins = "http://localhost:8001")
public class ContextCollectController {

    @Autowired
    private ContextCollectMapper contextCollectMapper;


    /**
     * 通过用户id查询历史记录
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping("/getContextCollectById")
    @ResponseBody
    public List<ContextCollect> getContextCollectById(@RequestParam("userid") String userId,
                                                      @RequestParam("page") Integer page) {
        List<ContextCollect> context = contextCollectMapper.getContextCollectById(userId,page);
        return context;
    }

    //获得所有记录
    @RequestMapping("/getAllContextCollect")
    @ResponseBody
    public List<ContextCollect> getAllContextCollect(){
        return contextCollectMapper.getAllContextCollect();
    }

    //存储收藏记录
    @RequestMapping(value = "/storgeContent",method = RequestMethod.POST)
    @ResponseBody
    public void storageContent(@RequestParam("userid") String userId,
                               @RequestParam("content") String content,
                               @RequestParam("summarization") String summarization){
        ContextCollect contextCollect =new ContextCollect(null,Long.parseLong(userId),content,summarization,new Date());
        contextCollectMapper.insert(contextCollect);
    }

    @RequestMapping(value = "/getText",method = RequestMethod.GET)
    @ResponseBody
    public String getText(){
        System.out.println("Hello World");
        return "Hello World";
    }



}
