package com.iroving.billanalysis.bean;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AliRecord implements Serializable {
    private static final long serialVersionUID = 2290194403366603942L;
    /**
     * 支付宝有几种装填
     * 退款成功
     * 交易关闭
     * 交易成功
     */
    public static final String PAY_STATUS_SUCCESS = "交易成功";

    /**
     * 支付类型
     */
    public static final String PAY_TYPE_PAYOUT = "支出";

    /**
     * 收入
     */
    public static final String PAY_TYPE_EARNING = "收入";

    /**
     * 余额宝
     */
    public static final String GOOD_NAME_EXTRA = "余额宝";


    @Alias("交易号")
    private String serialNo;
    @Alias("商家订单号")
    private String orderId;
    @Alias("交易创建时间")
    private LocalDateTime createTime;
    @Alias("付款时间")
    private LocalDateTime payTime;
    @Alias("最近修改时间")
    private LocalDateTime lastModifyTime;
    @Alias("交易来源地")
    private String from;
    @Alias("类型")
    private String type;
    @Alias("交易对方")
    private String payTo;
    @Alias("商品名称")
    private String goodName;
    @Alias("金额（元）")
    private Double price;
    @Alias("收/支")
    private String payType;
    @Alias("交易状态")
    private String payStatus;
    @Alias("服务费（元）")
    private Double servicePrice;
    @Alias("成功退款（元）")
    private Double returnPrice;
    @Alias("备注")
    private String remark;
    @Alias("资金状态")
    private String fundStatus;
}
