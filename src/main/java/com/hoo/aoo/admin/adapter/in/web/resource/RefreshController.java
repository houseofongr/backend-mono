package com.hoo.aoo.admin.adapter.in.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RefreshController {

    @GetMapping({"/users/**", "/houses/**", "/soundsources/**", "/users/{path:.+}", "/houses/{path:.+}", "/soundsources/{path:.+}"})
    public String refresh() {
        return "index.html";
    }
}
