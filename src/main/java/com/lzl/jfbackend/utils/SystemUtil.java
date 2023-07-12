package com.lzl.jfbackend.utils;

import com.lzl.jfbackend.vo.system.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemUtil {
    public static SystemInfoVo systemInfoVo() {
        SystemInfo systemInfo = new SystemInfo();
        SystemInfoVo info = new SystemInfoVo();
        OperatingSystem system = systemInfo.getOperatingSystem();
        info.setName(system.toString());
        info.setModel(systemInfo.getHardware().getComputerSystem().getModel());
        info.setTime(LocalDateTime.now());
        return info;
    }

    public static CpuInfoVo cpuInfoVo() {
        SystemInfo systemInfo = new SystemInfo();
        CpuInfoVo cpuInfoVo = new CpuInfoVo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        CentralProcessor.ProcessorIdentifier identifier = processor.getProcessorIdentifier();
        cpuInfoVo.setName(identifier.getName());
        cpuInfoVo.setArchitecture(identifier.getMicroarchitecture());
        cpuInfoVo.setLogicalProcessorCount(processor.getLogicalProcessorCount());
        cpuInfoVo.setPhysicalProcessorCount(processor.getPhysicalProcessorCount());

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            // 睡眠 1s
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpuInfoVo.setSysUsage(new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        cpuInfoVo.setUserUsage(new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        cpuInfoVo.setCurUsage(new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));
        return cpuInfoVo;
    }

    public static MemInfoVo memInfoVo() {
        SystemInfo systemInfo = new SystemInfo();
        MemInfoVo memInfoVo = new MemInfoVo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalMemory = memory.getTotal(); // 总内存
        long availableMemory = memory.getAvailable(); // 剩余内存
        memInfoVo.setFreeMem(formatByte(availableMemory));
        memInfoVo.setUsedMem(formatByte(totalMemory - availableMemory));
        memInfoVo.setTotalMem(formatByte(totalMemory));
        memInfoVo.setUsedRate(new DecimalFormat("#.##%").format((totalMemory - availableMemory) * 1.0 / totalMemory));
        return memInfoVo;
    }

    public static JvmInfoVo jvmInfoVo() {
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory(); // jvm总内存
        long jvmMaxMemoryByte = runtime.maxMemory(); // jvm 最大内存
        long freeMemoryByte = runtime.freeMemory(); // jvm 空闲空间
        String jdkVersion = System.getProperty("java.version");  // jdk 版本
        String jdkHome = System.getProperty("java.home");    // jdk 安装路径
        JvmInfoVo jvmInfoVo = new JvmInfoVo();
        jvmInfoVo.setJdkVersion(jdkVersion);
        jvmInfoVo.setJdkHome(jdkHome);
        jvmInfoVo.setTotalMem(formatByte(jvmTotalMemoryByte));
        jvmInfoVo.setUsedMem(formatByte(jvmTotalMemoryByte - freeMemoryByte));
        jvmInfoVo.setFreeMem(formatByte(freeMemoryByte));
        jvmInfoVo.setMaxMem(formatByte(jvmMaxMemoryByte));
        jvmInfoVo.setUsedRate(new DecimalFormat("#.##%").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        return jvmInfoVo;
    }

    public static List<FileInfoVo> fileInfoVos() {
        List<FileInfoVo> fileInfoVos = new ArrayList<>();
        FileSystem fileSystem = new SystemInfo().getOperatingSystem().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        for (OSFileStore store : fileStores) {
            FileInfoVo fileInfoVo = new FileInfoVo();
            fileInfoVo.setName(store.getName());
            fileInfoVo.setFreeSpace(formatByte(store.getFreeSpace()));
            fileInfoVo.setUsedSpace(formatByte(store.getTotalSpace() - store.getFreeSpace()));
            fileInfoVo.setTotalSpace(formatByte(store.getTotalSpace()));
            fileInfoVo.setUsedRate(new DecimalFormat("#.##%").
                    format((store.getTotalSpace() - store.getFreeSpace()) * 1.0 / store.getTotalSpace()));
            fileInfoVos.add(fileInfoVo);
        }
        return fileInfoVos;
    }

    private static String formatByte(long byteNumber) {
        double format = 1024.0;
        double kbNumber = byteNumber / format;
        if (kbNumber < format)
            return new DecimalFormat("#.##KB").format(kbNumber);
        double mbNumber = kbNumber / format;
        if (mbNumber < format)
            return new DecimalFormat("#.##MB").format(mbNumber);
        double gbNumber = mbNumber / format;
        if (gbNumber < format)
            return new DecimalFormat("#.##GB").format(gbNumber);
        double tbNumber = gbNumber / format;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

}
