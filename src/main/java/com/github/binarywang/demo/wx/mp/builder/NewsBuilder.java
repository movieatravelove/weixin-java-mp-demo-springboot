package com.github.binarywang.demo.wx.mp.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public class NewsBuilder extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(Object content, WxMpXmlMessage wxMessage,
                                   WxMpService service) {
        List<WxMpXmlOutNewsMessage.Item> newsList = new ArrayList();
        if (content != null) {
            newsList.add((WxMpXmlOutNewsMessage.Item) content);
        } else {
            WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
            item.setDescription("请仔细阅读使用规则后再使用本服务！");
            item.setPicUrl("https://cdn.jsdelivr.net/gh/movieatravelove/PicLibrary/wallpaper/5.jpg");
            item.setTitle("使用须知");
            item.setUrl("http://14l6i14912.qicp.vip/wx/redirect/index");
            WxMpXmlOutNewsMessage.Item item2 = new WxMpXmlOutNewsMessage.Item();
            item2.setDescription("11");
            item2.setPicUrl("https://cdn.jsdelivr.net/gh/movieatravelove/PicLibrary/logo/logo.png");
            item2.setTitle("总之岁月漫长，然而值得等待。");
            item2.setUrl("http://14l6i14912.qicp.vip/wx/redirect/index");
            newsList.add(item);
            newsList.add(item2);
        }
        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
            .fromUser(wxMessage.getToUser())
            .toUser(wxMessage.getFromUser()).articles(newsList)
            .build();
        return m;
    }


}
