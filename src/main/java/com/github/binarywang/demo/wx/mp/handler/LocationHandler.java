package com.github.binarywang.demo.wx.mp.handler;

import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class LocationHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String content = "";
        if (wxMessage.getMsgType().equals(XmlMsgType.LOCATION)) {
            content = "感谢反馈，您的的地理位置已收到！";

            //上报地理位置事件
            this.logger.info("上报地理位置，纬度 : {}，经度 : {}，精度 : {}",
                wxMessage.getLatitude(), wxMessage.getLongitude(), wxMessage.getPrecision());

            //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用
        }
        return new TextBuilder().build(content, wxMessage, null);
    }


}
