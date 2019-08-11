package com.biscuits.wallet.controller;

import com.biscuits.wallet.system.Result;
import com.biscuits.wallet.WechatUtil;
import com.biscuits.wallet.entity.User;
import com.biscuits.wallet.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author biscuits
 * @since 2019-08-10
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired private WechatUtil wechatUtil;
  @Autowired private IUserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public Object login(User userInfo, String code) {
    String openId = wechatUtil.getOpenId(code);
    userInfo.setOpenId(openId);
    String userNo = userService.login(userInfo);
    return Result.success(userNo);
  }
}
