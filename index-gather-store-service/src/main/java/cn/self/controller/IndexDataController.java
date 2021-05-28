package cn.self.controller;

import cn.self.pojo.IndexData;
import cn.self.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * freshIndexData/000300
 * getIndexData/000300
 * removeIndexData/000300
 */

@RestController
public class IndexDataController {
    @Autowired
    IndexDataService indexDataService;

    @GetMapping("/freshIndexData/{code}")
    public String fresh(@PathVariable("code") String code) throws Exception {
        indexDataService.fresh(code);
        return "已成功刷新数据";
    }

    @GetMapping("/getIndexData/{code}")
    public List<IndexData> get(@PathVariable("code") String code) throws Exception {
        return indexDataService.get(code);
    }

    @GetMapping("/removeIndexData/{code}")
    public String remove(@PathVariable("code") String code) throws Exception{
        indexDataService.remove(code);
        return "已成功删除";
    }
}
