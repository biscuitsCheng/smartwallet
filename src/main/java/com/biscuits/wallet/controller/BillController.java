package com.biscuits.wallet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biscuits.wallet.entity.TypeInfo;
import com.biscuits.wallet.mapper.BillMapper;
import com.biscuits.wallet.mapper.TypeInfoMapper;
import com.biscuits.wallet.system.BigDecimalUtil;
import com.biscuits.wallet.system.CodeUtils;
import com.biscuits.wallet.system.Result;
import com.biscuits.wallet.system.Utils;
import com.biscuits.wallet.entity.Bill;
import com.biscuits.wallet.entity.vo.BillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端控制器
 *
 * @author biscuits
 * @since 2019-08-10
 */
@RestController
@RequestMapping("/bill")
public class BillController {
  @Autowired private BillMapper billMapper;
  @Autowired private TypeInfoMapper typeInfoMapper;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public Object save(Bill bill) {
    bill.setBillNo(CodeUtils.billNo());
    billMapper.insert(bill);
    return Result.success(null);
  }

  @RequestMapping(value = "/{userNo}", method = RequestMethod.GET)
  public Object save(@PathVariable("userNo") String userNo, Page<Bill> page, Integer type) {
    QueryWrapper<Bill> wrapper = new QueryWrapper<>();
    if (type != null){
        wrapper.lambda().eq(Bill::getUserNo, userNo).eq(Bill::getType, type);
    }else {
        wrapper.lambda().eq(Bill::getUserNo, userNo);
    }

    IPage<Bill> result = billMapper.selectPage(page, wrapper);
    List<Bill> records = result.getRecords();
    List<BillVO> list =
        records.stream()
            .map(
                o -> {
                  BillVO vo = new BillVO();
                  QueryWrapper<TypeInfo> w = new QueryWrapper<>();
                  w.lambda().eq(TypeInfo::getTypeInfoNo, o.getTypeInfoNo());
                  vo.setBillNo(o.getBillNo());
                  vo.setType(o.getType());
                  vo.setTypeInfoName(typeInfoMapper.selectOne(w).getName());
                  vo.setPrice(BigDecimalUtil.getFormatString(o.getPrice(), 2));
                  return vo;
                })
            .collect(Collectors.toList());

    return Result.success(list);
  }

  @RequestMapping(value = "/{billNo}", method = RequestMethod.DELETE)
  public Object delete(@PathVariable("billNo") String billNo) {
    Utils.checkIsNotBlank(billNo, "error param");
    QueryWrapper<Bill> wrapper = new QueryWrapper<>();
    wrapper.lambda().eq(Bill::getBillNo, billNo);
    billMapper.delete(wrapper);
    return Result.success(null);
  }
}
