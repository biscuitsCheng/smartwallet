package com.biscuits.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.biscuits.wallet.system.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@Component
@Slf4j
public class WechatUtil {
  @Autowired private RestTemplate rest;

  @Value("#{wechat.appid}")
  private String appid;

  @Value("#{wechat.secret}")
  private String secret;

  public String getOpenId(String code) {
    String str =
        "https://api.weixin.qq.com/sns/jscode2session?appid="
            + appid
            + "&secret="
            + secret
            + "&js_code="
            + code
            + "&grant_type=authorization_code";
    ResponseEntity<String> resp = rest.getForEntity(str, String.class);
    JSONObject jsonObj = JSON.parseObject(resp.getBody());
    String openid = jsonObj.getString("openid");
    if (openid == null || "".equals(openid.trim())) {
      log.error("无法获得openId:{}",resp.getBody());
      throw new CommonException("无法获得openId");
    }
    return openid;
  }
}
