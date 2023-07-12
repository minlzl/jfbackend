package com.lzl.jfbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String name;

    private String permission;

    private String description;

    private LocalDateTime createTime;
}
