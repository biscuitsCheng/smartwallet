package com.biscuits.wallet.system;

import lombok.Builder;
import lombok.Getter;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@Getter
@Builder
public class ResultEntity<T> {
    private T data;
    private boolean success;
    private String msg;
}
