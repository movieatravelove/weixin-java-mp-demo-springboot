package com.github.binarywang.demo.wx.mp.handler;

import com.github.binarywang.demo.wx.mp.builder.NewsBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MenuHandler extends AbstractHandler {


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        String msg = String.format("type:%s, event:%s, key:%s",
            wxMessage.getMsgType(), wxMessage.getEvent(),
            wxMessage.getEventKey());
        if (EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        if ("V1001_TODAY_ARTICLE".equals(wxMessage.getEventKey())){
            return new NewsBuilder().build(null, wxMessage, weixinService);
        }
        if ("V1001_GOOD".equals(wxMessage.getEventKey())){
            WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
            item.setTitle("斯人若彩虹，遇上方知有。");
            item.setDescription("斯人若彩虹，遇上方知有；伊人若湍水 触及方知柔。");
            item.setUrl("http://14l6i14912.qicp.vip/wx/redirect/index");
            item.setPicUrl("https://cdn.jsdelivr.net/gh/movieatravelove/PicLibrary/logo/logo.png");
            return new NewsBuilder().build(item, wxMessage, weixinService);
        }
        return WxMpXmlOutMessage.TEXT().content(msg)
            .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
            .build();
    }

}
