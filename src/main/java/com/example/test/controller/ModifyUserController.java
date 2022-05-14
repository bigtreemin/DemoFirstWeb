package com.example.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.test.bean.UserBean;
import com.example.test.service.UserService;

@Controller
public class ModifyUserController {

    @Autowired
    UserService userService;

    /**
     * 查询所有用户信息
     * @param modelMap
     * @return
     */
    @RequestMapping("/users")
    public String showUsers(ModelMap modelMap){
        List<UserBean> userList = userService.queryAllUser();
        modelMap.addAttribute("userList",userList);
        return "users";
    }
    
    @RequestMapping(value = "/usersJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBean>> showUsersJson(){
    	List<UserBean> userList = userService.queryAllUser();
    	return ResponseEntity.ok(userList);
    }

    /**
     * 新增用户
     * remark：这里未做用户名是否重复校验
     * @param userBean
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String, Object> addUser(UserBean userBean){
        int flag = userService.addUser(userBean);
        Map<String,Object> map = new HashMap<String,Object>();
        if(flag == 1){
            map.put("msg","User added");
            return map;
        }else {
            map.put("msg","Add user failed");
            return map;
        }
    }


    /**
     * 根据用户ID删除用户信息
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/deleteUser+{id}")
    public String dropUser(@PathVariable("id") String id,ModelMap modelMap){
        int flag = userService.dropUser(id);
        List<UserBean> userList = userService.queryAllUser();
        modelMap.addAttribute("userList",userList);
        if(flag == 1){
            return "users";
        }else {
            return "error";
        }
    }

    /**
     * 根据用户ID查询用户信息
     * @param id
     * @return
     */
    @RequestMapping("/user+{id}")
    public String queryUser(@PathVariable("id") String id,ModelMap modelMap){
        UserBean userBean = userService.queryUserById(id);
        modelMap.addAttribute("user",userBean);
        return "userInfo";
    }

    @RequestMapping("/userJson+{id}")
    public ResponseEntity<UserBean> queryUserJson(@PathVariable("id") String id){
        UserBean userBean = userService.queryUserById(id);
        return ResponseEntity.ok(userBean);
    }
    /**
     * 根据用户ID修改用户信息
     * remark：这里未做用户名是否重复校验
     * @param userBean
     * @return
     */
    @RequestMapping("/modifyUser")
    @ResponseBody
    public Map modifyUser(UserBean userBean){
        int flag = userService.modifyUser(userBean);
        Map<String,Object> map = new HashMap<>();
        if(flag == 1){
            map.put("msg","update succeed");
            return map;
        }else {
            map.put("msg","update failed");
            return map;
        }
    }

}
