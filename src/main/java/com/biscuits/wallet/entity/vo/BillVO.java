package com.biscuits.wallet.entity.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author biscuits
 * @date 2019-08-11
 */
@Data@ToString
public class BillVO {
    private String billNo;
    private String typeInfoName;
    private Integer type;
    private String price;
    private String date;
}
