package com.iroving.billanalysis.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.iroving.billanalysis.AliRecord;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BillController {

    private static final String ORIGINAL_DATA = "-original";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String sayHello() {
//        redisTemplate.opsForValue().set("key", "value");
        redisTemplate.delete("2020-08");
        List<String> haha = new ArrayList<>();
        haha.add("1");
        redisTemplate.opsForList().rightPushAll("2020-08", haha);
        List range = redisTemplate.opsForList().range("2020-08", 0, -1);
        redisTemplate.delete("2020-08");

        return "hello";
    }

    @RequestMapping("/uploadBill")
    public List<Object> uploadBill(@RequestParam("file") MultipartFile file) throws Exception {
        File des = null;
        try {
            //文件命名需要是时间  定义成2020-10这样
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            String fileName = System.getProperty("user.dir") + UUID.fastUUID().toString() + ".csv";
            des = new File(fileName);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(des));

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(des), "utf8");
            char[] chars = new char[1024];
            int len;
            while ((len = inputStreamReader.read(chars)) != -1) {
                outputStreamWriter.write(chars, 0, len);
            }
            outputStreamWriter.close();
            inputStreamReader.close();

            CsvReader reader = CsvUtil.getReader();
//            从文件中读取CSV数据
            List<AliRecord> result = reader.read(ResourceUtil.getUtf8Reader(fileName), AliRecord.class);
            CsvData data = reader.read(des);
            List<CsvRow> rows = data.getRows();
            //遍历行
            List<AliRecord> records = new ArrayList<>();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //第一次是标题，要跳过
            int i = 0;
            for (CsvRow csvRow : rows) {
                //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
                AliRecord aliRecord = new AliRecord();
                List<String> rawList = csvRow.getRawList();
                if (rawList != null && rawList.size() == 17) {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    aliRecord.setSerialNo(StringUtils.trimWhitespace(rawList.get(0)));
                    aliRecord.setOrderId(StringUtils.trimWhitespace(rawList.get(1)));
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(2)))) {
                        aliRecord.setCreateTime(LocalDateTime.parse(StringUtils.trimWhitespace(rawList.get(2)), dateTimeFormatter));
                    }
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(3)))) {
                        aliRecord.setPayTime(LocalDateTime.parse(StringUtils.trimWhitespace(rawList.get(3)), dateTimeFormatter));
                    }
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(4)))) {
                        aliRecord.setLastModifyTime(LocalDateTime.parse(StringUtils.trimWhitespace(rawList.get(4)), dateTimeFormatter));
                    }
                    aliRecord.setFrom(StringUtils.trimWhitespace(rawList.get(5)));
                    aliRecord.setType(StringUtils.trimWhitespace(rawList.get(6)));
                    aliRecord.setPayTo(StringUtils.trimWhitespace(rawList.get(7)));
                    aliRecord.setGoodName(StringUtils.trimWhitespace(rawList.get(8)));
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(9)))) {
                        aliRecord.setPrice(Double.parseDouble(StringUtils.trimWhitespace(rawList.get(9))));
                    }
                    aliRecord.setPayType(StringUtils.trimWhitespace(rawList.get(10)));
                    aliRecord.setPayStatus(StringUtils.trimWhitespace(rawList.get(11)));
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(12)))) {
                        aliRecord.setServicePrice(Double.parseDouble(StringUtils.trimWhitespace(rawList.get(12))));
                    }
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(13)))) {
                        aliRecord.setReturnPrice(Double.parseDouble(StringUtils.trimWhitespace(rawList.get(13))));
                    }
                    aliRecord.setRemark(StringUtils.trimWhitespace(rawList.get(14)));
                    aliRecord.setFundStatus(StringUtils.trimWhitespace(rawList.get(15)));
                    records.add(aliRecord);
                } else {
//                    System.out.println(22);
                }
            }
            redisTemplate.opsForList().rightPushAll(originalFilename.substring(0, originalFilename.indexOf(".")) + ORIGINAL_DATA, records);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (des != null) {
                System.out.println("文件删除成功？" + des.delete());
            }
        }
        return null;
    }

    /**
     * 这边的date是yyyy-MM
     *
     * @param date
     * @return
     */
    @GetMapping("/listBillOriginal")
    public List<AliRecord> listBill(String date) {
        List<AliRecord> originalBillList = (List<AliRecord>) redisTemplate.opsForList().range(date + ORIGINAL_DATA, 0, -1);
        return originalBillList;
    }

}
