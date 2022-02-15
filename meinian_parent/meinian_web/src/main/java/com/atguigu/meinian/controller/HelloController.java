package com.atguigu.meinian.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.meinian.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
public class HelloController {

    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

}
