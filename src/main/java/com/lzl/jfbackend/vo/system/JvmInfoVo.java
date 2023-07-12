package com.lzl.jfbackend.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JvmInfoVo {

    String totalMem;

    String usedMem;

    String maxMem;

    String freeMem;

    String usedRate;

    String jdkVersion;

    String jdkHome;

}
