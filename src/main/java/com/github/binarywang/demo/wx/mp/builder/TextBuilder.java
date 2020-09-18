package com.github.binarywang.demo.wx.mp.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public class TextBuilder extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(Object content, WxMpXmlMessage wxMessage,
                                   WxMpService service) {
        WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(String.valueOf(content))
            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
            .build();
        return m;
    }

}
