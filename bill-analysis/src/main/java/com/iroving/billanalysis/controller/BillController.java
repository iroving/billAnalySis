package com.iroving.billanalysis.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.iroving.billanalysis.bean.AliRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class BillController {

    /**
     * 原始数据存在redis中的key的后缀
     * 2010-01-original
     */
    private static final String ORIGINAL_DATA = "-original";
    /**
     * 处理过的数据
     */
    private static final String CLASSIFIED_DATA = "-classified";

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
//            List<AliRecord> result = reader.read(ResourceUtil.getUtf8Reader(fileName), AliRecord.class);
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

                    aliRecord.setPayType(StringUtils.trimWhitespace(rawList.get(10)));

                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(9)))) {
                        double price = Double.parseDouble(StringUtils.trimWhitespace(rawList.get(9)));
                        if (AliRecord.PAY_TYPE_PAYOUT.equalsIgnoreCase(aliRecord.getPayType())) {
                            aliRecord.setPrice(price);
                        } else if (AliRecord.PAY_TYPE_EARNING.equalsIgnoreCase(aliRecord.getPayType())) {
                            aliRecord.setPrice(-price);
                        } else {
                            aliRecord.setPrice(0d);
                        }
                    }
                    aliRecord.setPayStatus(StringUtils.trimWhitespace(rawList.get(11)));
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(12)))) {
                        aliRecord.setServicePrice(Double.parseDouble(StringUtils.trimWhitespace(rawList.get(12))));
                    }
                    if (!StringUtils.isEmpty(StringUtils.trimWhitespace(rawList.get(13)))) {
                        aliRecord.setReturnPrice(Double.parseDouble(StringUtils.trimWhitespace(rawList.get(13))));
                    }
                    aliRecord.setRemark(StringUtils.trimWhitespace(rawList.get(14)));
                    aliRecord.setFundStatus(StringUtils.trimWhitespace(rawList.get(15)));
                    if (AliRecord.PAY_STATUS_SUCCESS.equalsIgnoreCase(aliRecord.getPayStatus())
                            && (AliRecord.PAY_TYPE_EARNING.equalsIgnoreCase(aliRecord.getPayType())
                            || AliRecord.PAY_TYPE_PAYOUT.equalsIgnoreCase(aliRecord.getPayType()))
                            //余额宝的一点点收入不要看了，太麻烦
                            && (aliRecord.getGoodName() != null && !aliRecord.getGoodName().contains(AliRecord.GOOD_NAME_EXTRA))) {
                        records.add(aliRecord);
                    }
                } else {
//                    System.out.println(22);
                }
            }
            String redisKey = originalFilename.substring(0, originalFilename.indexOf(".")) + ORIGINAL_DATA;
            List historyData = redisTemplate.opsForList().range(redisKey, 0, -1);
            if (historyData != null && historyData.size() > 0) {
                redisTemplate.delete(redisKey);
            }
            redisTemplate.opsForList().rightPushAll(redisKey, records);
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

    /**
     * 这边的date是yyyy-MM
     *
     * @param
     * @return
     */
    @PostMapping("/uploadBillClassified")
    public String uploadBillClassified(@RequestBody Map<String, List<AliRecord>> queryParams) {
        List<AliRecord> child = queryParams.get("child");
        List<AliRecord> life = queryParams.get("life");
        List<AliRecord> wife = queryParams.get("wife");
        List<AliRecord> husband = queryParams.get("husband");
        List<AliRecord> favor = queryParams.get("favor");
        List<AliRecord> billDateList = queryParams.get("billDate");

        List<AliRecord> childClassify = child.parallelStream().filter(x -> !"将货物拖到这边".equals(x.getGoodName())).collect(Collectors.toCollection(ArrayList::new));
        List<AliRecord> lifeClassify = life.parallelStream().filter(x -> !"将货物拖到这边".equals(x.getGoodName())).collect(Collectors.toCollection(ArrayList::new));
        List<AliRecord> wifeClassify = wife.parallelStream().filter(x -> !"将货物拖到这边".equals(x.getGoodName())).collect(Collectors.toCollection(ArrayList::new));
        List<AliRecord> husbandClassify = husband.parallelStream().filter(x -> !"将货物拖到这边".equals(x.getGoodName())).collect(Collectors.toCollection(ArrayList::new));
        List<AliRecord> favorClassify = favor.parallelStream().filter(x -> !"将货物拖到这边".equals(x.getGoodName())).collect(Collectors.toCollection(ArrayList::new));
        String billDate = billDateList.get(0).getGoodName();

        String redisKeyChild = billDate + "-child" + CLASSIFIED_DATA;
        String redisKeyLife = billDate + "-life" + CLASSIFIED_DATA;
        String redisKeyWife = billDate + "-wife" + CLASSIFIED_DATA;
        String redisKeyHusband = billDate + "-husband" + CLASSIFIED_DATA;
        String redisKeyFavor = billDate + "-favor" + CLASSIFIED_DATA;

        List historyData = redisTemplate.opsForList().range(redisKeyChild, 0, -1);
        if (historyData != null && historyData.size() > 0) {
            redisTemplate.delete(redisKeyChild);
        }

        historyData = redisTemplate.opsForList().range(redisKeyLife, 0, -1);
        if (historyData != null && historyData.size() > 0) {
            redisTemplate.delete(redisKeyLife);
        }

        historyData = redisTemplate.opsForList().range(redisKeyWife, 0, -1);
        if (historyData != null && historyData.size() > 0) {
            redisTemplate.delete(redisKeyWife);
        }

        historyData = redisTemplate.opsForList().range(redisKeyHusband, 0, -1);
        if (historyData != null && historyData.size() > 0) {
            redisTemplate.delete(redisKeyHusband);
        }

        historyData = redisTemplate.opsForList().range(redisKeyFavor, 0, -1);
        if (historyData != null && historyData.size() > 0) {
            redisTemplate.delete(redisKeyFavor);
        }

        redisTemplate.opsForList().rightPushAll(redisKeyChild, childClassify);
        redisTemplate.opsForList().rightPushAll(redisKeyLife, lifeClassify);
        redisTemplate.opsForList().rightPushAll(redisKeyWife, wifeClassify);
        redisTemplate.opsForList().rightPushAll(redisKeyHusband, husbandClassify);
        redisTemplate.opsForList().rightPushAll(redisKeyFavor, favorClassify);


//        CLASSIFIED_DATA
//        List<AliRecord> originalBillList = (List<AliRecord>) redisTemplate.opsForList().range(date + ORIGINAL_DATA, 0, -1);
        return "保存成功！";
    }


}
