package cn.self.util;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;

/**
 * @author Administrator
 * 因为要访问的时候，才能看到监控信息，所以做个工具类，不断地访问。
 */
public class AccessService {
    public static void main(String[] args) {

        while(true) {
            ThreadUtil.sleep(1000);
            try {
                String html= HttpUtil.get("http://127.0.0.1:8051/simulate/399975/20/1.01/0.99/0/null/null/");
                System.out.println("html length:" + html.length());
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }

        }

    }
}
