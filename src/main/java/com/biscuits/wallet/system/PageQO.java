package com.biscuits.wallet.system;

import lombok.Data;
import lombok.ToString;

/**
 * @author biscuits
 * @date 2019-08-11
 */
@Data@ToString
public class PageQO {
    private Integer pageIndex;
    private Integer pageSize;
}
