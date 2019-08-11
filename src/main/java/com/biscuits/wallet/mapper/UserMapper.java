package com.biscuits.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biscuits.wallet.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author biscuits
 * @since 2019-08-10
 */
public interface UserMapper extends BaseMapper<User> {


    User findByOpenId(String openId);

}
