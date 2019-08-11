package com.biscuits.wallet.system;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author biscuits
 * @date 2019-08-10
 */
@UtilityClass
public class CodeUtils {
    private static AtomicInteger userNo = new AtomicInteger(10);
    private static AtomicInteger typeInfoNo = new AtomicInteger(10);
    private static AtomicInteger billNo = new AtomicInteger(10);
    public static String userNo(){
        String a = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int num = userNo.get();
        int val;
        if (num > 50){
            val = userNo.getAndSet(0);
        }else {
            val = userNo.incrementAndGet();
        }
        return "U".concat(a)+val;
    }

    public static String typeInfoNo(){
        String a = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int num = typeInfoNo.get();
        int val;
        if (num > 50){
            val = typeInfoNo.getAndSet(0);
        }else {
            val = typeInfoNo.incrementAndGet();
        }
        return "T".concat(a)+val;
    }

    public static String billNo() {
        String a = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int num = billNo.get();
        int val;
        if (num > 50){
            val = billNo.getAndSet(0);
        }else {
            val = billNo.incrementAndGet();
        }
        return "B".concat(a)+val;
    }
}
