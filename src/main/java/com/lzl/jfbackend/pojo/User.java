package com.lzl.jfbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String permission;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic(value = "0",delval = "1")
    @TableField("is_deleted")
    private Integer deleted;

}
