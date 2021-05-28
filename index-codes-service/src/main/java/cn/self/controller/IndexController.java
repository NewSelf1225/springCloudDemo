package cn.self.controller;

import cn.self.config.IpConfiguration;
import cn.self.pojo.Index;
import cn.self.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 返回指数代码集合，并通过 IpConfiguration 获取当前接口并打印。
 * 注： @CrossOrigin 表示允许跨域，因为后续的回测视图是另一个端口号的，访问这个服务是属于跨域了。
 *  http://127.0.0.1:8011/codes
 * @author ck
 */
@RestController
public class IndexController {

    @Autowired
    IndexService indexService;
    @Autowired
    IpConfiguration ipConfiguration;

    @GetMapping("/codes")
    @CrossOrigin
    public List<Index> get() throws Exception{
        System.out.println("当前实例端口为" + ipConfiguration.getPort());
        return indexService.get();
    }
}
