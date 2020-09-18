package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Edward
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wx/redirect")
public class WxRedirectController2 {

    @RequestMapping("/index")
    public String greetUser() {
        return "index";
    }


}
