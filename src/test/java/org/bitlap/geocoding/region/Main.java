package org.bitlap.geocoding.region;

import org.bitlap.geocoding.region.model.RegionEntity;
import org.bitlap.geocoding.region.util.OutUtil;

import java.io.IOException;

public class Main {
    
    // 导入数据库成功后，执行china.sql，插入数据项：【中国】
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
//        String pathname = "/tmp/cnarea" + 20210707 + ".dat";
//        String pathname = "D:\\github\\geocoding\\src\\main\\resources\\core\\region.dat";
        String pathname = "D:\\githubgeocoding\\src\\main\\resources\\core\\region_2023.dat";
        RegionDatFileHelper.writeDatFile(pathname);
        long end = System.currentTimeMillis();
        OutUtil.info(String.format("cost %s ms", end - start));
        RegionEntity regionEntity = RegionDatFileHelper.readDatFile(pathname);
        OutUtil.info(regionEntity.toString());
    }
}