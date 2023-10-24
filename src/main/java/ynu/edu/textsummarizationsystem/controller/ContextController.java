package ynu.edu.textsummarizationsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ynu.edu.textsummarizationsystem.beans.Context;
import ynu.edu.textsummarizationsystem.mapper.ContextMapper;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("context")
@CrossOrigin(origins = "http://localhost:8001")

public class ContextController {

    @Autowired
    private ContextMapper contextMapper;

    /**
     * 通过用户id查询历史记录
     * @param userId
     * @param page
     * @return
     */
    @RequestMapping("/getContextByUserId")
    @ResponseBody
    public List<Context> getContextById(@RequestParam("userid") String userId,
                               @RequestParam("page") Integer page) {
        List<Context> context = contextMapper.getContextById(userId,page);
        return context;
    }

    //获得所有记录
    @RequestMapping("/getAllContext")
    @ResponseBody
    public List<Context> getAllContext(){
        return contextMapper.getAllContext();
    }


    //存储记录
    @RequestMapping(value = "/storgeContent",method = RequestMethod.POST)
    @ResponseBody
    public void storageContent(@RequestParam("userid") String userId,
                               @RequestParam("content") String content,
                               @RequestParam("summarization") String summarization){
        Context context =new Context(null,Long.parseLong(userId),content,summarization,new Date(),content.length());
        contextMapper.insert(context);
    }

}
