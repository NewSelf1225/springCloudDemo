package cn.self.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视图控制类
 */
@Controller
public class ViewController {

    @RequestMapping("/")
    public String view() throws Exception{
        return "view";
    }
}
