package com.biscuits.wallet.system;

import com.alibaba.fastjson.JSONObject;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author biscuits
 * @date 2019-08-11
 */
public class Utils {
    private static String wxNull = "null";
    private static String wxUndefined = "undefined";

    private Utils() throws RuntimeException {
        throw new RuntimeException("不允许创建工具类对象");
    }

    public static boolean isWxEmpty(String s) {
        return wxNull.equals(s) || wxUndefined.equals(s) || isBlank(s);
    }

    public static boolean isBlank(String str) {
        if (str == null || "".equals(str.trim())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    private static PropertyDescriptor[] srcBeans;
    private static PropertyDescriptor[] desBeans;

    /**
     * 当两个类所需要转化的字段名称不同的时候
     *
     * @param src 源数据
     * @param des 目标数据
     * @param sameOne 所有字段名不同，但需要转换的映射 Map<src,des> (可选参数)
     */
    public static void parseObj2Obj(Object src, Object des, Map<String, String>... sameOne) {
        String srcName;
        String desName;
        srcBeans = getPropertyDescriptor(src);
        desBeans = getPropertyDescriptor(des);
        try {
            for (PropertyDescriptor desBean : desBeans) {
                for (PropertyDescriptor srcBean : srcBeans) {
                    srcName = srcBean.getName();
                    desName = desBean.getName();
                    if (desName.equals(srcName) || checkStringInMap(srcName, desName, sameOne)) {
                        desBean.getWriteMethod().invoke(des, srcBean.getReadMethod().invoke(src));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当两个类所需要转化的字段名称不同的时候
     *
     * @param src 源数据
     * @param des 目标数据
     */
    public static void parseObj2Obj(Object src, Object des) {
        String srcName;
        String desName;
        srcBeans = getPropertyDescriptor(src);
        desBeans = getPropertyDescriptor(des);
        try {
            for (PropertyDescriptor desBean : desBeans) {
                for (PropertyDescriptor srcBean : srcBeans) {
                    srcName = srcBean.getName();
                    desName = desBean.getName();
                    if (desName.equals(srcName)) {
                        desBean.getWriteMethod().invoke(des, srcBean.getReadMethod().invoke(src));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当两个类即存在名称相同且不需要转换的字段，又存在名称不同，且需要转换的数据的时候
     *
     * @param src 源数据
     * @param des 目标数据
     * @param dif 字段名称相同但是不需要转换的字段名集合
     * @param sameOne 所有字段名不同，但需要转换的映射 Map<src,des> (可选参数)
     */
    public static void parseObj2Obj(
            Object src, Object des, List<String> dif, Map<String, String>... sameOne) {
        String srcName;
        String desName;
        srcBeans = getPropertyDescriptor(src);
        desBeans = getPropertyDescriptor(des);
        try {
            for (PropertyDescriptor desBean : desBeans) {
                for (PropertyDescriptor srcBean : srcBeans) {
                    srcName = srcBean.getName();
                    desName = desBean.getName();
                    if ((desName.equals(srcName) || checkStringInMap(srcName, desName, sameOne))
                            && !dif.contains(desName)) {
                        desBean.getWriteMethod().invoke(des, srcBean.getReadMethod().invoke(src));
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * parseObj2Obj(Object src, Object des, List<String> dif, Map<String, String>... sameOne) 的增强方法
     *
     * @param src
     * @param des
     * @param strs 一个字符串类型的数组，将不需要转换的字段直接存入，需要转换的字段以key=value的形式传入，且src在前
     * @throws Exception
     */
    public static void parseObj2Obj(Object src, Object des, String... strs) {
        List<String> dif = new ArrayList<>();
        Map<String, String> sameOne = new HashMap<>();
        parseString(sameOne, dif, strs);
        parseObj2Obj(src, des, dif, sameOne);
    }

    /**
     * parseObj2Obj(Object src, Object des, List<String> dif, Map<String, String>... sameOne) 的增强方法
     *
     * @param src
     * @param des
     * @param str 一个字符串类型，将不需要转换的字段直接存入，需要转换的字段以key=value的形式传入，且src在前
     * @throws Exception
     */
    public static void parseObj2Obj(Object src, Object des, String str) {
        List<String> dif = new ArrayList<>();
        Map<String, String> sameOne = new HashMap<>();
        String[] strs = str.split(",");
        parseString(sameOne, dif, strs);
        parseObj2Obj(src, des, dif, sameOne);
    }

    public static void parseJsonObj2Obj(JSONObject jObj, Object des, String str) {
        List<String> dif = new ArrayList<>();
        Map<String, String> sameOne = new HashMap<>();
        String[] strs = str.split(",");
        parseString(sameOne, dif, strs);
        parseJsonObj2Obj(jObj, des, dif, sameOne);
    }

    public static void parseJsonObj2Obj(JSONObject obj, Object des) {
        List<String> dif = Collections.EMPTY_LIST;
        Map<String, String> sameOne = Collections.EMPTY_MAP;
        parseJsonObj2Obj(obj, des, dif, sameOne);
    }

    private static void parseJsonObj2Obj(
            JSONObject jObj, Object des, List<String> dif, Map<String, String> sameOne) {
        String desName;

        desBeans = getPropertyDescriptor(des);
        try {
            for (PropertyDescriptor desBean : desBeans) {
                for (String srcName : jObj.keySet()) {
                    desName = desBean.getName();
                    if ((desName.equals(srcName) || checkStringInMap(srcName, desName, sameOne))) {
                        if (!dif.contains(desName)) {
                            desBean.getWriteMethod().invoke(des, jObj.getString(srcName));
                            break;
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void parseString(Map<String, String> sameOne, List<String> dif, String[] strs) {
        for (String s : strs) {
            if (s.contains("=")) {
                String[] sameStr = s.split("=");
                sameOne.put(sameStr[0], sameStr[1]);
            } else {
                dif.add(s);
            }
        }
    }

    /**
     * 判断两个字符串的映射是否属于这个集合
     *
     * @param srcName
     * @param desName
     * @param obj
     * @return
     */
    private static boolean checkStringInMap(
            String srcName, String desName, Map<String, String>... obj) {
        if (obj != null && obj.length > 0) {
            Map<String, String> map = obj[0];
            if (desName.equals(map.get(srcName))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过对象实体获取属性描述器
     *
     * @param obj 对象实体
     * @return 属性描述器
     */
    private static PropertyDescriptor[] getPropertyDescriptor(Object obj) {
        try {
            return Introspector.getBeanInfo(obj.getClass(), Object.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭一个资源
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 获取日期的标准格式 */
    public static String getTimeString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static boolean isEmpty(Collection list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection list) {
        return !isEmpty(list);
    }

    public static boolean isNotBlank(String libProductId) {
        return !isBlank(libProductId);
    }

    public static void checkIsSameSize(List col1, List col2) {
        if (col1 == null && col2 == null) {
            return;
        }
        if (col1 == null || col2 == null) {
            throw new CommonException("两个集合长度不一样");
        }

        if (col1.size() != col2.size()) {
            throw new CommonException("两个集合长度不一样");
        }
    }

    public static void checkIsNotNull(Object obj, String msg) throws CommonException {
        if (obj == null) {
            throw new CommonException("找不到对应的" + msg);
        }
    }

    public static void checkIsNotBlank(String obj, String msg) throws CommonException {
        if (isBlank(obj)) {
            throw new CommonException("找不到对应的" + msg);
        }
    }

    public static void checkIsNotEmpty(Collection obj, String msg) throws CommonException {
        if (isEmpty(obj)) {
            throw new CommonException("找不到对应的" + msg);
        }
    }

    /**
     * @param list1 排序依据的列表
     * @param list2 待排序列表
     * @param fun1 排序依据的列表获取用作key的唯一编码的方法
     * @param fun2 待排序列表获取用作key的唯一编码的方法
     * @param <T> 排序依据的列表的元素的类型
     * @param <A> 待排序列表的元素的类型
     * @return
     */
    public static <T, A> List<A> sortByList(
            List<T> list1, List<A> list2, Function<T, Object> fun1, Function<A, Object> fun2) {
        /*
         * 当元素数量小于等于1的时候 不需要排序，直接返回
         */
        if (list1.size() <= 1 || list2.size() <= 1) {
            return list2;
        }
        Map<Object, Integer> sorted = new HashMap<>(list1.size());
        AtomicInteger i = new AtomicInteger(0);
        list1.forEach(o -> sorted.put(fun1.apply(o), i.getAndIncrement()));
        list2.sort(
                (o1, o2) -> {
                    Object key1 = fun2.apply(o1);
                    Object key2 = fun2.apply(o2);
                    Integer order1 = sorted.get(key1);
                    Integer order2 = sorted.get(key2);
                    if (order1 == null || order2 == null) {
                        throw new CommonException("无法匹配输入");
                    }
                    return order1 > order2 ? 1 : 0;
                });
        return list2;
    }

    public static boolean hasNotBlank(String... strs) {
        for (String str : strs) {
            if (Utils.isNotBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasBlank(String... strs) {
        for (String str : strs) {
            if (Utils.isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNull(Object... strs) {
        for (Object str : strs) {
            if (Utils.isNull(str)) {
                return true;
            }
        }
        return false;
    }
}
