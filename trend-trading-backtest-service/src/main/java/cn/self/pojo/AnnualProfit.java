package cn.self.pojo;

import lombok.Data;

/**
 * @author ck
 * 每年收益实体类，其中的字段有： 年份，指数收益，趋势收益
 */
@Data
public class AnnualProfit {
    private int year;
    private float indexIncome;
    private float trendIncome;
}
