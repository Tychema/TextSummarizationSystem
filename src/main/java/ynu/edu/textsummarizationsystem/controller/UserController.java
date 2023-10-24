package ynu.edu.textsummarizationsystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ynu.edu.textsummarizationsystem.beans.User;
import ynu.edu.textsummarizationsystem.mapper.UserMapper;
import ynu.edu.textsummarizationsystem.utils.MD5;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:8001")

public class UserController {

    @Autowired
    private UserMapper UserMapper;

    @RequestMapping("/getByUserId")
    @ResponseBody
    public User SelectUserById(@RequestParam("userid") String userId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid",userId);
        List<User> user = UserMapper.selectByMap(map);
        return user.get(0);
    }

    @RequestMapping("/getByUserName")
    @ResponseBody
    public User getByUserName(@RequestParam("username") String userName) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",userName);
        List<User> username = UserMapper.selectByMap(map);
        return username.get(0);
    }

    @RequestMapping(value = "/InsertUser",method = RequestMethod.POST)
    @ResponseBody
    public String InsertUser(@RequestParam("username") String userName,
                             @RequestParam("password") String password
    ) {
        MD5 md5 = new MD5(password);
        User user = new User(null,userName,md5.getMD5(),0);
        try {
            UserMapper.insert(user);
            return "200";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String UserLogin(@RequestParam("username") String userName,
                            @RequestParam("password") String password
    ) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",userName);
        try {
            List<User> users = UserMapper.selectByMap(map);
            try {
                User user = users.get(0);
                MD5 md5 = new MD5(password);
                String database_password= user.getPassWord();
                String md5_password=md5.getMD5();
                System.out.println(database_password);
                System.out.println(md5_password);
                if(md5_password.equals(database_password)){
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
        return UserMapper.selectList(null);
    }





}
