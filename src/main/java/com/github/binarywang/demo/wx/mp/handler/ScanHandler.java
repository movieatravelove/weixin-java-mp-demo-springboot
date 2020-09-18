package com.github.binarywang.demo.wx.mp.handler;

import java.util.Map;

import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) {
        // 扫码事件处理
        if (wxMpXmlMessage.getEvent().equals(WxConsts.EventType.SCAN)) {
            logger.info("+++++++++++++++扫码事件++++++++++++++++");
            return new TextBuilder().build("扫码消息："+wxMpXmlMessage.getEventKey(), wxMpXmlMessage, wxMpService);
        }
        return null;
    }
}
