package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.*;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author Edward
 */
@AllArgsConstructor
@RestController
@RequestMapping("/wx/msg/{appid}")
public class WxMsgController {

    private final WxMpService wxService;


    @GetMapping("/send")
    public void testSendTemplateMsg(@PathVariable String appid) throws WxErrorException {
        if (!wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
            .toUser("o5fJ6sx8Drrpf-V-mTdy6czBT6wM")
            .templateId("mYkiF_yTFhtqWnKk52i9HYabjpoB5EcAeP48Mey4Pyg")
            .url("")
            .build();
        templateMessage.addData(new WxMpTemplateData("first", dateFormat.format(new Date()), "#FF00FF"))
            .addData(new WxMpTemplateData("remark", RandomStringUtils.randomAlphanumeric(100), "#FF00FF"));
        String msgId = wxService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        System.out.println(msgId);
    }

    public void getIndustry() throws Exception {
        final WxMpTemplateIndustry industry = wxService.getTemplateMsgService().getIndustry();
        System.out.println(industry);
    }


    public void setIndustry() throws Exception {
        WxMpTemplateIndustry industry = new WxMpTemplateIndustry(WxMpTemplateIndustryEnum.findByCode(29),
            WxMpTemplateIndustryEnum.findByCode(8));
        boolean result = wxService.getTemplateMsgService().setIndustry(industry);
    }

    @GetMapping("/add")
    public void addTemplate(@PathVariable String appid) throws Exception {
        if (!wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        String result = wxService.getTemplateMsgService().addTemplate("TM00015");
        System.err.println(result);
    }

    /**
     * 获取模板消息
     * @param appid
     * @return
     * @throws Exception
     */
    @GetMapping("/get")
    public List<WxMpTemplate> getAllPrivateTemplate(@PathVariable String appid) throws Exception {
        if (!wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        List<WxMpTemplate> result = wxService.getTemplateMsgService().getAllPrivateTemplate();
        return result;
    }


    /**
     * 删除指定模板消息
     * @param appid
     * @param templateId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/del")
    public boolean delPrivateTemplate(@PathVariable String appid, String templateId) throws Exception {
        if (!wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        return wxService.getTemplateMsgService().delPrivateTemplate(templateId);
    }


}
