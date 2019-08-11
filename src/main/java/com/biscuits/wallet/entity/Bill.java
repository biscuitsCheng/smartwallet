package com.biscuits.wallet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author biscuits
 * @since 2019-08-10
 */
@Data@ToString
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private BigDecimal price;

    private String remark;

    private Integer type;

    private String typeInfoNo;

    private String userNo;
    private String billNo;
}
