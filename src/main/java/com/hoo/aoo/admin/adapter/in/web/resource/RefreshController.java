package com.hoo.aoo.admin.adapter.in.web.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RefreshController {

    @GetMapping({"/login","/users/**", "/houses/**", "/sound-sources/**"})
    public String refresh() {
        return "forward:/index.html";
    }
}
