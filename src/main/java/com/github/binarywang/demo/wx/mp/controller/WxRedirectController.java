package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Edward
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wx/redirect/{appid}")
public class WxRedirectController {
    private final WxMpService wxService;


    /**
     * 网页授权:
     * 获得access token -> 获得用户基本信息
     *
     * 当用户同意授权后，会回调所设置的url并把authorization code传过来，然后用这个code获得access token，其中也包含用户的openid等信息
     *
     * @param appid
     * @param code
     * @param map
     * @return
     */
    @RequestMapping("/greet")
    public String greetUser(@PathVariable String appid, @RequestParam String code, ModelMap map) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        try {
            WxMpOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            WxMpUser user = wxService.getOAuth2Service().getUserInfo(accessToken, null);
            map.put("user", user);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "greet_user";
    }


//    public String greetUser(@PathVariable String appid, @RequestParam String code, ModelMap map) {
//        if (!this.wxService.switchover(appid)) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
//        }
//        try {
//            String lang = "zh_CN"; //语言
//            WxMpUser user = wxService.getUserService().userInfo(code, lang);
//            map.put("user", user);
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//        return "greet_user";
//    }


    @RequestMapping("/index")
    public String greetUser() {
        return "index";
    }


}
