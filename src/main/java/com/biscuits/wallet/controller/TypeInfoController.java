package com.biscuits.wallet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biscuits.wallet.entity.TypeInfo;
import com.biscuits.wallet.entity.vo.TypeInfoVO;
import com.biscuits.wallet.mapper.TypeInfoMapper;
import com.biscuits.wallet.system.CodeUtils;
import com.biscuits.wallet.system.Result;
import com.biscuits.wallet.system.TypeStatus;
import com.biscuits.wallet.system.Utils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author biscuits
 * @since 2019-08-10
 */
@RestController
@RequestMapping("/typeInfo")
public class TypeInfoController {
    @Autowired private TypeInfoMapper typeInfoMapper;
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Object save(String name,Integer type,String userNo){
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setName(name);
        typeInfo.setType(type);
        typeInfo.setUserNo(userNo);
        typeInfo.setTypeInfoNo(CodeUtils.typeInfoNo());
        typeInfoMapper.insert(typeInfo);
        return Result.success(null);
    }
    @RequestMapping(value = "/{userNo}",method = RequestMethod.GET)
    public Object list(@PathVariable("userNo") String userNo){
        QueryWrapper<TypeInfo> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TypeInfo::getUserNo,userNo).or().eq(TypeInfo::getUserNo,"sys");
        List<TypeInfo> typeInfos = typeInfoMapper.selectList(wrapper);
        if ( Utils.isEmpty(typeInfos)){
            return Result.fail("找不到对应的数据");
        }
        List<TypeInfoVO> expenses = typeInfos.stream().filter(o -> TypeStatus.EXPENSES.code().equals(o.getType())).map(o -> {
            TypeInfoVO vo = new TypeInfoVO();
            vo.setTypeInfoNo(o.getTypeInfoNo());
            vo.setName(o.getName());
            return vo;
        }).collect(Collectors.toList());
        List<TypeInfoVO> income = typeInfos.stream().filter(o -> TypeStatus.INCOME.code().equals(o.getType())).map(o -> {
            TypeInfoVO vo = new TypeInfoVO();
            vo.setTypeInfoNo(o.getTypeInfoNo());
            vo.setName(o.getName());
            return vo;
        }).collect(Collectors.toList());
        HashMap<String , List<TypeInfoVO>> map = Maps.newHashMap();
        map.put("expenses",expenses);
        map.put("income",income);
        return Result.success(map);
    }

}

