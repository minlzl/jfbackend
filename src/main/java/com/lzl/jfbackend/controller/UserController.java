package com.lzl.jfbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lzl.jfbackend.exception.ApiException;
import com.lzl.jfbackend.pojo.User;
import com.lzl.jfbackend.result.Result;
import com.lzl.jfbackend.result.ResultFactory;
import com.lzl.jfbackend.service.UserService;
import com.lzl.jfbackend.vo.LoginVo;
import com.lzl.jfbackend.vo.Resp;
import com.lzl.jfbackend.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SaCheckLogin
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/login")
    @SaIgnore
    public Resp<LoginVo> login(@RequestParam("account") String username, @RequestParam("password") String password) {
        if (username == null || password == null) {
            throw new ApiException("参数错误");
        }
        LoginVo login = userService.login(username, password);
        return Resp.success(login);
    }

    @GetMapping("/logout")
    @SaIgnore
    public Resp<String> logout() {
        StpUtil.logout();
        return Resp.success("success");
    }

    @PostMapping("/addUser")
    public Resp<UserVo> addUser(@RequestBody User user) {
        if (user == null || user.getName() == null)
            throw new ApiException("填写错误");
        UserVo userVo = userService.addUser(user);
        return Resp.success(userVo);
    }

    @GetMapping("/listUser")
    public Resp<List<UserVo>> listUser(@RequestParam("query") String query) {
        if (query == null)
            throw new ApiException("参数错误");
        List<UserVo> userVos = userService.listUser(query);
        return Resp.success(userVos);
    }

    @PutMapping("/editUser")
    public Resp<UserVo> editUser(@RequestBody User user) {
        if (user == null || user.getName() == null) {
            throw new ApiException("填写错误");
        }
        UserVo userVo = userService.editUser(user);
        return Resp.success(userVo);
    }

    @DeleteMapping("/delUser")
    public Resp<Boolean> delUser(@RequestBody User user) {
        if (user == null || user.getName() == null)
            throw new ApiException("填写错误");
        boolean delUser = userService.delUser(user);
        return Resp.success(delUser);
    }
}
