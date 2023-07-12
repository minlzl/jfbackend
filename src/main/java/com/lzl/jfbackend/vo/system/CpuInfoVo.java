package com.lzl.jfbackend.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpuInfoVo {

    Integer physicalProcessorCount;

    Integer logicalProcessorCount;

    String name;

    String architecture;

    String sysUsage;

    String userUsage;

    String curUsage;

}
