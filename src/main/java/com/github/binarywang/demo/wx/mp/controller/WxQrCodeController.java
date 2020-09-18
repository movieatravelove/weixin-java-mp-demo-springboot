package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Edward
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wx/code/{appid}")
public class WxQrCodeController {

    private final WxMpService wxService;

    private static String QR_CODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    /**
     * 创建带参二维码
     *
     * @param appid
     * @param scene 二维码参数
     * @param expire 过期时间 默认3600s = 1h
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public String greetUser(@PathVariable String appid,
                            @RequestParam String scene,
                            @RequestParam(required = false, defaultValue = "3600") Integer expire) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        try {
            // 1. 获得二维码ticket
            // 1.1 临时ticket，过期时间单位秒
            WxMpQrCodeTicket ticket = wxService.getQrcodeService().qrCodeCreateTmpTicket(scene, expire);
            // 1.2 永久ticket
            // WxMpQrCodeTicket ticket2 = wxService.getQrcodeService().qrCodeCreateLastTicket(scene);

            // 2. 换取二维码图片
            // 2.1 获得在系统临时目录下的文件，需要自己保存使用，注意：临时文件夹下存放的文件不可靠，不要直接使用
            // File file = wxService.getQrcodeService().qrCodePicture(ticket);
            // 2.2 拼接二维码地址
            String qrCodeUrl = String.format(QR_CODE_URL, ticket.getTicket());
            System.out.println("过期："+ticket.getExpireSeconds());
            // 3. 长链接转成短链接
            return wxService.shortUrl(qrCodeUrl);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }


}
