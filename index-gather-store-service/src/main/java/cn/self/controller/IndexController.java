package cn.self.controller;

import cn.self.pojo.Index;
import cn.self.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("/freshCodes")
    public List<Index> fresh() throws Exception {
        return indexService.fresh();
    }

    @GetMapping("/getCodes")
    public List<Index> get() throws Exception {
        return indexService.get();
    }

    @GetMapping("/removeCodes")
    public String remove() throws Exception {
        indexService.remove();
        return "已成功删除";
    }
}