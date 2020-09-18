package com.github.binarywang.demo.wx.mp.handler;

import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        //TODO 组装回复消息
        String content = "收到信息内容：\n" + wxMessage.getContent() + "\n我们将尽快回复您/爱心";

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        // 关键词回复
        for (String key : KEYWORD_REPLY.keySet()) {
            for (String k : key.split("\\|")) {
                if (StringUtils.startsWith(wxMessage.getContent(), k)) {
                    content = KEYWORD_REPLY.get(key);
                    return new TextBuilder().build(content, wxMessage, weixinService);
                }
            }
        }
        return new TextBuilder().build(content, wxMessage, weixinService);
    }


    /**
     * 定义关键词回复内容
     * 多个关键处使用 "|" 分割
     */
    public static Map<String, String> KEYWORD_REPLY = new HashMap<String, String>() {
        {
            put("电话|手机|座机", "18888888888");
            put("微信", "WeChat");
            put("地址|位置", "陕西西安高新一路");
            put("你好|hello|hi", "你好，有什么可以帮助您的呢~");
        }
    };


    public static void main(String[] args) {

    }

}
