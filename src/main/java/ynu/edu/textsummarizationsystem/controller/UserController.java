package ynu.edu.textsummarizationsystem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import ynu.edu.textsummarizationsystem.beans.User;
import ynu.edu.textsummarizationsystem.beans.counters;
import ynu.edu.textsummarizationsystem.dao.CountersRepository;
import ynu.edu.textsummarizationsystem.dao.UserRepository;
import ynu.edu.textsummarizationsystem.utils.MD5;

@CrossOrigin(origins = "http://localhost:8001")
@RestController
@RequestMapping("user")
public class UserController {

    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountersRepository countersRepository;

    @RequestMapping("/getByUserId")
    @ResponseBody
    public User SelectUserById(@RequestParam("userid") String userId) {
        Query query = Query.query(Criteria.where("_id").is(Integer.parseInt(userId)));
        User user = mongoTemplate.findOne(query,User.class);
        return user;
    }

    @RequestMapping("/getByUserName")
    @ResponseBody
    public User getByUserName(@RequestParam("username") String userName) {
        Query query = Query.query(Criteria.where("username").is(userName));
        User user = mongoTemplate.findOne(query,User.class);
        return user;
    }

    @RequestMapping(value = "/InsertUser",method = RequestMethod.POST)
    @ResponseBody
    public String InsertUser(@RequestParam("username") String userName,
                           @RequestParam("password") String password
                           ) {
        //获得自增主键值
        List<counters> all = mongoTemplate.findAll(counters.class);
        int id = all.get(0).getUser_value();
        Query query = Query.query(Criteria.where("_id").is(all.get(0).getId()));
        Update update = new Update();
        update.set("user_value", id+1);
        mongoTemplate.updateFirst(query, update, counters.class);


        MD5 md5 = new MD5(password);
        User user = new User();
        user.setUsername(userName);
        user.setManagement(0);
        user.setPassword(md5.getMD5());
        user.setId(id+1);
        userRepository.save(user);
        return "注册成功";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String UserLogin(@RequestParam("username") String userName,
                             @RequestParam("password") String password
    ) {

        Query query = Query.query(Criteria.where("username").is(userName));
        try {
            List<User> username = mongoTemplate.find(query,User.class);
            try {
                User user = username.get(0);
                MD5 md5 = new MD5(password);
                String database_password=md5.getMD5();
//                System.out.println("password:"+user.getPassWord());
//                System.out.println("database_password:"+database_password);
                if(user.getPassword().equals(database_password)){
                    return "200";
                }
                else{
                    return "400";
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //查询所有记录
    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<User> getAll(){

        return mongoTemplate.findAll(User.class);
    }





}
