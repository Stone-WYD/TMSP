package com.njxnet.service.tmsp.config.constant.mybatis;

import com.njxnet.service.tmsp.constants.FreezeIEnum;
import com.njxnet.service.tmsp.constants.IEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = {FreezeIEnum.class})
public class MyEnumTypeHandler extends BaseTypeHandler<IEnum> {
    private final Class<IEnum> type;
    private final IEnum[] enums;

    public MyEnumTypeHandler(Class<IEnum> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
            this.enums = type.getEnumConstants();
            if (this.enums == null) {
                throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
            }
            if (!IEnum.class.isAssignableFrom(type)){
                throw new IllegalArgumentException(type.getSimpleName() + " does not implements IEnum.");
            }
        }
    }

    public void setNonNullParameter(PreparedStatement ps, int i, IEnum parameter, JdbcType jdbcType) throws SQLException {
        // 将枚举类中的字典值（value）sql语句中
        Integer ordinal = parameter.getCode();
        ps.setInt(i, ordinal);
    }

    public IEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        return ordinal == 0 && rs.wasNull() ? null : this.toOrdinalEnum(ordinal);
    }

    public IEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return ordinal == 0 && rs.wasNull() ? null : this.toOrdinalEnum(ordinal);
    }

    public IEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return ordinal == 0 && cs.wasNull() ? null : this.toOrdinalEnum(ordinal);
    }

    private IEnum toOrdinalEnum(int ordinal) {
        for (IEnum e : this.enums) {
            if (e.getCode() == ordinal) return e;
        }
        // 如果没找到字典值（value）对应的枚举类，则报错
        throw new IllegalArgumentException("Cannot convert " + ordinal + " to " + this.type.getSimpleName() + " by ordinal value.");
    }
}
