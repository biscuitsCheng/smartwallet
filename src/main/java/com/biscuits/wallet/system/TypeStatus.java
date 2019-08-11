package com.biscuits.wallet.system;

/**
 * @author biscuits
 * @date 2019-08-10
 */

public enum TypeStatus {
    /**
     * 收入
     */
    INCOME(1),
    /**
     * 支出
     */
    EXPENSES(0);
    private Integer code;
    public Integer code(){
        return code;
    }
    TypeStatus(Integer code){
        this.code = code;
    }
}
