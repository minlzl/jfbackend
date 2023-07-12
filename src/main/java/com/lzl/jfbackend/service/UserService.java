package com.lzl.jfbackend.service;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.protobuf.Api;
import com.lzl.jfbackend.constant.Constant;
import com.lzl.jfbackend.exception.ApiException;
import com.lzl.jfbackend.mapper.UserMapper;
import com.lzl.jfbackend.pojo.User;
import com.lzl.jfbackend.vo.LoginVo;
import com.lzl.jfbackend.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    UserMapper userMapper;

    @Transactional
    public LoginVo login(String username, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        if (user.getPassword().equals(SaSecureUtil.md5BySalt(password, Constant.SALT))) {
            StpUtil.login(username);
            return new LoginVo(StpUtil.getTokenValue(), username, user.getPermission());
        }
        throw new ApiException("密码错误");
    }

    @Transactional
    public UserVo addUser(User user) {
        if (user == null || user.getName() == null) {
            throw new ApiException("未知错误");
        }
        User u = userMapper.selectOne(new QueryWrapper<User>().eq("name", user.getName()));
        if (u != null) {
            throw new ApiException("该用户名已被注册");
        }
        user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(), Constant.SALT));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);
        int i = userMapper.insert(user);
        if (i == 0) {
            throw new ApiException("添加失败");
        }
        return new UserVo(user.getName(), user.getPermission(), user.getDescription(), user.getCreateTime());
    }

    public List<UserVo> listUser(String query) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().like("name", query).or().like("description", query));
        List<UserVo> userVos = new ArrayList<>();
        for (User user : users) {
            UserVo userVo = new UserVo(user.getName(), user.getPermission(), user.getDescription(), user.getCreateTime());
            userVos.add(userVo);
        }
        return userVos;
    }

    public UserVo editUser(User user) {
        if (user == null || user.getName() == null) {
            throw new ApiException("修改失败");
        }
        user.setPassword(null);
        user.setUpdateTime(LocalDateTime.now());
        update(user, new UpdateWrapper<User>().eq("name", user.getName()));
        return new UserVo(user.getName(), user.getPermission(), user.getDescription(), user.getCreateTime());
    }

    public boolean delUser(User user) {
        int i = userMapper.delete(new QueryWrapper<User>().eq("name", user.getName()));
        return i >= 1;
    }
}
