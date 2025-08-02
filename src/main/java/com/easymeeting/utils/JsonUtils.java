package com.easymeeting.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.easymeeting.entity.enums.ResponseCodeEnum;
import com.easymeeting.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
//封装下fastjson
public class JsonUtils {
    public static SerializerFeature[] FEATURES = new SerializerFeature[]{SerializerFeature.WriteMapNullValue};
    
    // 添加Long类型转字符串的过滤器
    private static final ValueFilter LONG_TO_STRING_FILTER = (object, name, value) -> {
        if (value instanceof Long) {
            return value.toString();
        }
        return value;
    };
    
    public static String convertObj2Json(Object obj){return JSON.toJSONString(obj,FEATURES);}
    
    // 新增方法：处理Long类型精度丢失问题
    public static String convertObj2JsonWithLongToString(Object obj){
        return JSON.toJSONString(obj, LONG_TO_STRING_FILTER, FEATURES);
    }
    
    public static <T> T convertJson2Obj(String json,Class<T> classz){
        try {
            if (json == "ping")
            {
                return null;
            }
            return JSONObject.parseObject(json,classz);
        }catch (Exception e){
            log.error("Json转换异常：{}",json);
            throw new BusinessException(ResponseCodeEnum.CODE_603);
        }

    }
    public static <T> List<T> convertJsonArray2List(String json,Class<T> classz){
        try {
            return JSONArray.parseArray(json,classz);
        }catch (Exception e){
            log.error("convertJsonArray2List,json:{}",json,e);
            throw new BusinessException(ResponseCodeEnum.CODE_603);
        }
    }
}
