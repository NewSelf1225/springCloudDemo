package cn.self.controller;


import cn.self.config.IpConfiguration;
import cn.self.pojo.IndexData;
import cn.self.service.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexDataController {
    @Autowired
    IpConfiguration ipConfiguration;
    @Autowired
    IndexDataService indexDataService;

    @GetMapping("/data/{code}")
    public List<IndexData> get(@PathVariable("code") String code) throws Exception{
        System.out.println("当前实例为" + ipConfiguration.getPort());
        return indexDataService.get(code);
    }

}
