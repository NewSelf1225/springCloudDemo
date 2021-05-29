package cn.self.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视图控制类
 */
@Controller
public class ViewController {

    @Value("${version}")
    String version;

    @GetMapping("/")
    public String view(Model m) throws Exception{
        m.addAttribute("version",version);
        return "view";
    }
}
