package org.bitlap.geocoding;

import lombok.Data;
import org.bitlap.geocoding.model.Address;
import org.bouncycastle.util.encoders.Base64;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

public class ParseAreaUtil {

    private  static List<String> list = new ArrayList<>();

    static {
        BufferedReader bufReader = null;
        try {
//            InputStream ins = ParseAreaUtil.class.getClassLoader().getResourceAsStream("C:\\Users\\DELL1199\\IdeaProjects\\untitled\\src\\main\\resources\\text\\company_name.txt");
            InputStreamReader ins = new InputStreamReader(new FileInputStream(new File("D:\\githubgeocoding\\src\\main\\resources\\txt\\company_name.txt")));
            bufReader = new BufferedReader(ins);
            String str = null;
            while ((str = bufReader.readLine()) != null) {
                if(str != null && !"".equals(str))
                list.add(str);
            }
        } catch (IOException e) {
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void writeFile(String content) {
        // 指定要写入的文件路径
        String filePath = "d:\\file_2023.txt";

        try {
            // 创建一个 BufferedWriter 对象，用于写入文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            // 将字符串写入文件
            writer.write(content);

            // 关闭文件写入流
            writer.close();

            System.out.println("字符串已成功写入文件。");
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        GeocodingX geocodingX = new GeocodingX("core/region_2023.dat");

        list.stream().forEach(x->{
            Address address = geocodingX.normalizing(x);
            if(address==null){
                CustomAddress customAddress = new CustomAddress();
                customAddress.setOriginal(x);
                System.out.println(customAddress.toString());

            }else{
                CustomAddress customAddress = new CustomAddress();
                customAddress.setOriginal(x);
                customAddress.setProvinceId(Optional.ofNullable(address.getProvinceId()).orElseGet(()->-999999999999L));
                customAddress.setProvince(address.getProvince());
                customAddress.setCityId(Optional.ofNullable(address.getCityId()).orElseGet(()->-999999999999L));
                customAddress.setCity(address.getCity());
                customAddress.setDistrictId(Optional.ofNullable(address.getDistrictId()).orElseGet((()->-999999999999L)));
                customAddress.setDistrict(address.getDistrict());
                customAddress.setStreetId(Optional.ofNullable(address.getStreetId()).orElseGet((()->-999999999999L)));
                customAddress.setStreet(address.getStreet());
                customAddress.setTownId(Optional.ofNullable(address.getTownId()).orElseGet((()->-999999999999L)));
                customAddress.setTown(address.getTown());
                customAddress.setVillageId(Optional.ofNullable(address.getVillageId()).orElseGet((()->-999999999999L)));
                customAddress.setVillage(address.getVillage());
                customAddress.setRoadNum(address.getRoadNum());
                customAddress.setRoad(address.getRoad());
                customAddress.setText(address.getText());
                System.out.println(customAddress.toString());
            }
        });

//        parseDatFiel();
    }



    public static void parseDatFiel() throws Exception {
        String myselfFiel = "D:\\github\\geocoding\\src\\main\\resources\\core\\region_2023.dat"; // Replace with your actual data file path
//        {"id":7113875307449,"parentId":7111771832497,"level":3,"name":"重庆路93巷双 26号至 32号","shortName":"重庆路93巷双 26号至 32号","alias":"台湾省,新北,板桥,重庆路93巷双 26号至 32号","type":"PlatformL4","zip":"220560"}
//        String myselfFiel = "D:\\github\\geocoding\\src\\main\\resources\\core\\region_2021.dat"; // Replace with your actual data file path
        //{"id":7113310495621,"parentId":7110409446666,"name":"保福路２段双 150号以上","alias":"","type":"PlatformL4","zip":"234009"}
        File file = new File(myselfFiel);

        if (!file.exists()) {
            throw new IllegalArgumentException("Geocoding data file [" + myselfFiel + "] does not exist.");
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }

        fileInputStream.close();
        byteArrayOutputStream.close();

        byte[] inputData = byteArrayOutputStream.toByteArray();

        byte[] decodedData = Base64.decode(inputData);

        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(decodedData);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteInputStream);

        ByteArrayOutputStream uncompressedByteArrayOutputStream = new ByteArrayOutputStream();
        byte[] uncompressedBuffer = new byte[1024];
        int uncompressedLength;
        while ((uncompressedLength = gzipInputStream.read(uncompressedBuffer)) != -1) {
            uncompressedByteArrayOutputStream.write(uncompressedBuffer, 0, uncompressedLength);
        }

        gzipInputStream.close();
        uncompressedByteArrayOutputStream.close();

        String string = new String(uncompressedByteArrayOutputStream.toByteArray());

        writeFile(string);
    }

}

@Data
class CustomAddress{
    private String original;
    private Long provinceId;
    private String province;
    private Long cityId;
    private String city;
    private Long districtId;
    private String district;
    private Long streetId;
    private String street;
    private Long townId;
    private String town;
    private Long villageId;
    private String village;
    private String road;
    private String roadNum;
    private String text;

    @Override
    public String toString() {
        return original +
                "#@#" + provinceId +
                "#@#" + province +
                "#@#" + cityId +
                "#@#" + city  +
                "#@#" + districtId +
                "#@#" + district +
                "#@#" + streetId +
                "#@#" + street +
                "#@#" + townId +
                "#@#" + town +
                "#@#" + villageId +
                "#@#" + village  +
                "#@#" + road +
                "#@#" + roadNum +
                "#@#" + text;
    }
}