package com.biscuits.wallet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author biscuits
 * @since 2019-08-10
 */
@Data@ToString
public class TypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Column()
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String name;

    private Integer type;

    private String typeInfoNo;

    private String userNo;


}
