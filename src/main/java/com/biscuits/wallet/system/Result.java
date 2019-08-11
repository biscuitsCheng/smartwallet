package com.biscuits.wallet.system;

import lombok.experimental.UtilityClass;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@UtilityClass
public class Result {
  public static <T> Object success(T data) {
    return ResultEntity.builder().success(true).data(data).build();
  }

  public static Object fail(String msg) {
    return ResultEntity.builder().success(true).msg(msg).build();
  }
}
