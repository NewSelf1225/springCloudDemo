package cn.self.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.self.pojo.AnnualProfit;
import cn.self.pojo.IndexData;
import cn.self.pojo.Profit;
import cn.self.pojo.Trade;
import cn.self.service.BackTestService;
import com.ctc.wstx.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BackTestController {

    @Autowired
    BackTestService backTestService;

    @GetMapping("/simulate/{code}/{ma}/{buyThreshold}/{sellThreshold}/{serviceCharge}/{startData}/{endData}")
    @CrossOrigin
    public Map<String, Object> backTest(@PathVariable("code") String code,
                                        @PathVariable("ma") int ma,
                                        @PathVariable("startData") String strStartData,
                                        @PathVariable("buyThreshold") float buyThreshold,
                                        @PathVariable("sellThreshold") float sellThreshold,
                                        @PathVariable("serviceCharge") float serviceCharge,
                                        @PathVariable("endData") String strEndData)
            throws Exception {
        List<IndexData> allIndexDatas = backTestService.listIndexData(code);

        String indexStartData = allIndexDatas.get(0).getDate();
        String indexEndData = allIndexDatas.get(allIndexDatas.size() - 1).getDate();

        allIndexDatas = filterByDateRange(allIndexDatas, strStartData, strEndData);

        float sellRate = sellThreshold;
        float buyRate = buyThreshold;

        Map<String, ?> simulateResult = backTestService.simulate(ma, sellRate, buyRate, serviceCharge, allIndexDatas);
        List<Profit> profits = (List<Profit>) simulateResult.get("profits");
        List<Trade> trades = (List<Trade>) simulateResult.get("trades");

        int winCount = (Integer) simulateResult.get("winCount");
        int lossCount = (Integer) simulateResult.get("lossCount");
        float avgWinRate = (Float) simulateResult.get("avgWinRate");
        float avgLossRate = (Float) simulateResult.get("avgLossRate");

        /**indexIncomeTotal 索引总计
         * indexIncomeAnnual    指数年度
         * trendIncomeTotal 趋势测量
         * trendIncomeAnnual    趋势
         */
        float years = backTestService.getYear(allIndexDatas);
        float indexIncomeTotal = (allIndexDatas.get(allIndexDatas.size() - 1).getClosePoint() - allIndexDatas.get(0).getClosePoint()) / allIndexDatas.get(0).getClosePoint();
        float indexIncomeAnnual = (float) Math.pow(1 + indexIncomeTotal, 1 / years) - 1;
        float trendIncomeTotal = (profits.get(profits.size() - 1).getValue() - profits.get(0).getValue()) / profits.get(0).getValue();
        float trendIncomeAnnual = (float) Math.pow(1 + trendIncomeTotal, 1 / years) - 1;

        List<AnnualProfit> annualProfits = (List<AnnualProfit>) simulateResult.get("annualProfits");

        Map<String, Object> result = new HashMap<>();
        result.put("indexDatas", allIndexDatas);
        result.put("indexStartData", indexStartData);
        result.put("indexEndData", indexEndData);
        result.put("profits", profits);
        result.put("trades", trades);
        result.put("years", years);
        result.put("indexIncomeTotal", indexIncomeTotal);
        result.put("indexIncomeAnnual", indexIncomeAnnual);
        result.put("trendIncomeTotal", trendIncomeTotal);
        result.put("trendIncomeAnnual", trendIncomeAnnual);

        result.put("winCount", winCount);
        result.put("lossCount", lossCount);
        result.put("avgWinRate", avgWinRate);
        result.put("avgLossRate", avgLossRate);

        result.put("annualProfits", annualProfits);

        return result;
    }

    private List<IndexData> filterByDateRange(List<IndexData> allIndexDatas, String strStartData, String strEndData) {
        if (StrUtil.isBlankOrUndefined(strStartData) || StrUtil.isBlankOrUndefined(strEndData)) {
            return allIndexDatas;
        }

        List<IndexData> result = new ArrayList<>();
        Date startDate = DateUtil.parse(strStartData);
        Date endData = DateUtil.parse(strEndData);

        for (IndexData indexData : allIndexDatas) {
            Date date = DateUtil.parse(indexData.getDate());
            if (date.getTime() >= startDate.getTime() && date.getTime() <= endData.getTime()) {
                result.add(indexData);
            }
        }
        return result;
    }
}
