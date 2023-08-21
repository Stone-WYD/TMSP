package com.njxnet.service.tmsp.config.constant.mvc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.njxnet.service.tmsp.constant.IEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;

@Slf4j
public class IEnumJsonDeserializer<T extends IEnum> extends JsonDeserializer<IEnum> {

    @Override
    public IEnum deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {

        // 将传来的 string 字符串反序列化成枚举类
        final String text = parser.getText();
        if (text == null || text.length()==0) return null;
        JsonStreamContext parsingContext = parser.getParsingContext();
        String currentName = parsingContext.getCurrentName();
        // 包含枚举类的对象，即 @RequestBody 对应的传参对象
        Object currentValue = parsingContext.getCurrentValue();

        // 获取当前类枚举类的 Field，用于判断需要转换的对象是否实现了IEnum接口
        Field declaredField;
        try {
            declaredField = currentValue.getClass().getDeclaredField(currentName);
        }catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        Class<?> targetType = declaredField.getType();
        // 目标类需要是实现自定义接口
        if (!IEnum.class.isAssignableFrom(targetType)) return null;

        // 返回正确的枚举类
        // 获取所有枚举对象
        T[] values = (T[]) targetType.getEnumConstants();
        // 目标类需要是枚举类型
        if (values == null) {
            throw new IllegalArgumentException(targetType.getSimpleName() + " does not represent an enum type.");
        }
        for (T value : values) {
            // 如果枚举的 value 与请求体中的内容相同，返回对应的枚举类对象
            if (value.getValue().equals(text)) {
                return value;
            }
        }
        throw new IllegalArgumentException("枚举类转换异常，找不到对应的枚举值，请检查传参！");
    }
}
