package com.atguigu.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.dubbo.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Reference // 引用注册中心提供的服务
    private HelloService helloService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

}
