package com.hoo.aoo.admin.adapter.in.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RefreshController {

    @GetMapping({"/users/**", "/houses/**", "/soundsources/**"})
    public String refresh() {
        return "forward:/index.html";
    }
}
