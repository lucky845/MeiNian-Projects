package com.atguigu.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dubbo.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

// dubbo提供的service, 该注解可以将所标识的类作为服务发布
// 参数代表负载均衡为随机
//@Service(loadbalance = "random")
@Transactional
@Service(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }
}
