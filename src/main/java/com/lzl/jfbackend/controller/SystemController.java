package com.lzl.jfbackend.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lzl.jfbackend.utils.SystemUtil;
import com.lzl.jfbackend.vo.Resp;
import com.lzl.jfbackend.vo.system.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
//@SaCheckLogin
public class SystemController {

    @GetMapping("/cpuInfo")
    public Resp<CpuInfoVo> getCpuInfo() {
        return Resp.success(SystemUtil.cpuInfoVo());
    }

    @GetMapping("/sysInfo")
    public Resp<SystemInfoVo> getSystemInfo() {
        return Resp.success(SystemUtil.systemInfoVo());
    }

    @GetMapping("/memInfo")
    public Resp<MemInfoVo> getMemInfo() {
        return Resp.success(SystemUtil.memInfoVo());
    }

    @GetMapping("/jvmInfo")
    public Resp<JvmInfoVo> getJvmInfo() {
        return Resp.success(SystemUtil.jvmInfoVo());
    }

    @GetMapping("/fileInfo")
    public Resp<List<FileInfoVo>> getFileInfo() {
        return Resp.success(SystemUtil.fileInfoVos());
    }
}
