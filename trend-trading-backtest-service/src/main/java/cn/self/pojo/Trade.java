package cn.self.pojo;

import lombok.Data;

/**
 * @author ck
 * 交易明细类
 */
@Data
public class Trade {
    private String buyDate;
    private String sellDate;
    private float buyClosePoint;
    private float sellClosePoint;
    private float rate;
}
