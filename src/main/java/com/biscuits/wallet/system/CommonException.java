package com.biscuits.wallet.system;

import lombok.Getter;
import lombok.Setter;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@Setter
@Getter
public class CommonException extends RuntimeException {
    private String code;
    private String msg;


    public CommonException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CommonException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
