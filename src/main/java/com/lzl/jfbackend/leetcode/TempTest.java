package com.lzl.jfbackend.leetcode;

import ch.qos.logback.classic.spi.EventArgUtil;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TempTest {

    /**
     * 获取系统信息
     */
    private static void getSysInfo() {
        SystemInfo systemInfo = new SystemInfo();
        System.out.println(systemInfo.getOperatingSystem());
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        ComputerSystem computerSystem = hardware.getComputerSystem();
        System.out.println(computerSystem.getHardwareUUID());
        System.out.println(computerSystem.getFirmware().getDescription());
        System.out.println(computerSystem.getModel());
    }

    private static void printComputerSystem() {

        SystemInfo systemInfo = new SystemInfo();
        ComputerSystem computerSystem = systemInfo.getHardware().getComputerSystem();
        System.out.println("manufacturer: " + computerSystem.getManufacturer());
        System.out.println("model: " + computerSystem.getModel());
        System.out.println("serialnumber: " + computerSystem.getSerialNumber());
        final Firmware firmware = computerSystem.getFirmware();
        System.out.println("firmware:");
        System.out.println("  manufacturer: " + firmware.getManufacturer());
        System.out.println("  name: " + firmware.getName());
        System.out.println("  description: " + firmware.getDescription());
        System.out.println("  version: " + firmware.getVersion());
//      System.out.println("  release date: " + (firmware.getReleaseDate() == null ? "unknown": firmware.getReleaseDate() == null ? "unknown" : FormatUtil.formatDate(firmware.getReleaseDate())));
        final Baseboard baseboard = computerSystem.getBaseboard();
        System.out.println("baseboard:");
        System.out.println("  manufacturer: " + baseboard.getManufacturer());
        System.out.println("  model: " + baseboard.getModel());
        System.out.println("  version: " + baseboard.getVersion());
        System.out.println("  serialnumber: " + baseboard.getSerialNumber());
    }

    /**
     * 获取 cpu 信息
     */
    private static void getCpuInfo() throws InterruptedException {
        System.err.println("===============cpu信息begin===============");
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
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
        System.out.println("cpu型号:" + processor);
        CentralProcessor.ProcessorIdentifier identifier = processor.getProcessorIdentifier();
        System.out.println("-------------" + identifier.getModel());
        System.out.println("-------------" + identifier.getName());
        System.out.println("-------------" + identifier.getMicroarchitecture());
        System.out.println("-------------" + identifier.getFamily());
        System.out.println("-------------" + identifier.getIdentifier());
        System.out.println("-------------" + identifier.getStepping());
        System.out.println("物理核数：" + processor.getPhysicalProcessorCount());
        System.out.println("逻辑核数：" + processor.getLogicalProcessorCount());
//        processor.
        System.out.println("cpu核数:" + processor.getLogicalProcessorCount());
        System.out.println("frq" + Arrays.toString(processor.getCurrentFreq()));
        System.out.println("maxFre" + processor.getMaxFreq());
        System.out.println("cpu系统使用率:" + new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        System.out.println("cpu用户使用率:" + new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        System.out.println("cpu当前等待率:" + new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        System.out.println("cpu当前使用率:" + new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));
        System.err.println("===============cpu信息end===============\n");

    }

    /**
     * 获取主机内存信息
     */
    private static void getMemoryInfo() {
        System.err.println("===============主机内存信息begin===============");
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalMemory = memory.getTotal(); // 总内存
        long acaliableMemory = memory.getAvailable(); // 剩余内存
        System.out.println("总内存:" + formatByte(totalMemory));
        System.out.println("已经使用的内存:" + formatByte(totalMemory - acaliableMemory));
        System.out.println("剩余内存:" + formatByte(acaliableMemory));
        System.out.println("使用率:" + new DecimalFormat("#.##%").format((totalMemory - acaliableMemory) * 1.0 / totalMemory));
        System.err.println("===============主机内存信息end===============\n");
    }

    /**
     * 换算单位
     *
     * @param byteNumber 换算的数字
     */
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

    /**
     * 获取 Jvm 信息
     */
    private static void getJvmInfo() {
        System.err.println("===============Jvm信息begin===============");
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory(); // jvm总内存
        long jvmMaxMemoryByte = runtime.maxMemory(); // jvm 最大内存
        long freeMemoryByte = runtime.freeMemory(); // jvm 空闲空间
        String jdkVersion = System.getProperty("java.version");  // jdk 版本
        String jdkHome = System.getProperty("java.home");    // jdk 安装路径
        System.out.println("jvm总内存:" + formatByte(jvmTotalMemoryByte));
        System.out.println("jvm已使用内存:" + formatByte(jvmTotalMemoryByte - freeMemoryByte));
        System.out.println("jvm最大内存:" + formatByte(jvmMaxMemoryByte));
        System.out.println("jvm剩余内存:" + formatByte(freeMemoryByte));
        System.out.println("jvm内存使用率:" + new DecimalFormat("#.##%").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        System.out.println("jdk版本号:" + jdkVersion);
        System.out.println("jdk安装路径:" + jdkHome);
        System.err.println("===============Jvm信息end===============");
    }

    private static void getFileInfo() {
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        for (OSFileStore store : fileStores) {
            System.out.println(store.getName() + "---" + store.getDescription());
            System.out.println("free" + formatByte(store.getFreeSpace()));
            System.out.println("used" + formatByte(store.getUsableSpace()));
            System.out.println("total" + formatByte(store.getTotalSpace()));
            System.out.println("rate" + new DecimalFormat("#.##%").
                    format((store.getTotalSpace() - store.getFreeSpace()) * 1.0 / store.getTotalSpace()));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        getSysInfo();
        printComputerSystem();
        getCpuInfo();
        getMemoryInfo();
        getJvmInfo();
        getFileInfo();
    }

}
