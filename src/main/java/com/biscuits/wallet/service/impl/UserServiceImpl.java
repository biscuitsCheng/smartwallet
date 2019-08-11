package com.biscuits.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biscuits.wallet.service.IUserService;
import com.biscuits.wallet.system.CodeUtils;
import com.biscuits.wallet.entity.User;
import com.biscuits.wallet.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author biscuits
 * @since 2019-08-10
 */
@Service
public class UserServiceImpl implements IUserService {
  @Autowired private UserMapper userMapper;

  @Override
  public String login(User user) {
    String openId = user.getOpenId();
      QueryWrapper<User> wrapper = new QueryWrapper();
      wrapper.lambda().eq(User::getOpenId,openId);
      User entity = userMapper.selectOne(wrapper);
    if (entity == null) {
      String userNo = CodeUtils.userNo();
      user.setUserNo(userNo);
      userMapper.insert(user);
      return userNo;
    }
    return entity.getUserNo();
  }
}
