package com.easymeeting.utils;

import com.easymeeting.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyTools {
    public static <T,S> T copy(S source, Class<T> clazz) {
        T target = null;
        try{
            target = clazz.newInstance();
        }catch (Exception e){
            throw new BusinessException("对象copy失败");
        }
        BeanUtils.copyProperties(source,target);
        return target;
    }
    public static <T,S> List<T> copyList(List<S> sList, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        for (S s : sList) {
            T t = copy(s, clazz);
            list.add(t);
        }
        return list;
    }
}
