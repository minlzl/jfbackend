package com.lzl.jfbackend.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfoVo {

    /**
     * 操作系统名称
     */
    String name;

    /**
     * 计算机型号
     */
    String model;

    /**
     * 系统时间
     */
    LocalDateTime time;


}
