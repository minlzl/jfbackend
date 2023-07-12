package com.lzl.jfbackend.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemInfoVo {

    String totalMem;

    String usedMem;

    String freeMem;

    String usedRate;
}
