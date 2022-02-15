package com.atguigu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('add')")
    public String add() {
        return "success";
    }

    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('update')")
    public String update() {
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAnyAuthority('delete')")
    public String delete() {
        return "success";
    }


}
