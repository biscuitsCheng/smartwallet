package com.biscuits.wallet.service;

import com.biscuits.wallet.entity.User;

/**
 * @author biscuits
 * @date 2019-08-10
 */

public interface IUserService {
    /**
     * 登录接口
     * @param user 用户信息
     * @return userNo
     */
    String login(User user);
}
