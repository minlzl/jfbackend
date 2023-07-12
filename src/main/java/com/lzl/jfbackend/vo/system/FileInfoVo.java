package com.lzl.jfbackend.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoVo {

    String name;

    String freeSpace;

    String usedSpace;

    String totalSpace;

    String usedRate;
}
