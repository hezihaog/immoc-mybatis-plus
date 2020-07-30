package com.imooc.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/29 8:44 下午
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mp_user")
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * 注解@TableId，标识字段为主键，如变量名和数据库字段名不一致，可指定
     */
    //@TableId(value = "id")
    /**
     * 主键策略
     * 1.IdType.AUTO：数据库自增
     * 2.IdType.NONE：不配置，默认雪花算法
     * 以下3种策略，主键的Id不能设置值，才会生效
     * 3.IdType.ID_WORKER：雪花算法，数值类型
     * 4.IdType.UUID：UUID，字符串类型
     * 5.IdType.ID_WORKER_STR，雪花算法，字符串类型
     */
    @TableId(value = "id")
    private Long userId;
    /**
     * 姓名
     * 注解@TableField，标识为数据库字段，如变量名和数据库字段名不一致，可指定
     * condition属性，用作在使用实体作为查询条件给MyBatis-Plus直接查询时，可以指定字段是作为等值还是模糊查询，或不等于等条件（默认是等值）
     */
    //@TableField(value = "name", condition = SqlCondition.LIKE)
    /**
     * strategy属性，字段策略，NOT_EMPTY会忽略为null和空字符串的值
     */
    //@TableField(value = "name", strategy = FieldStrategy.NOT_EMPTY)
    @TableField(value = "name")
    private String readName;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 直属上级
     */
    private Long managerId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 备注，非数据库字段，默认MyBatis-Plus会将实体的所有变量名作为数据库字段，如果没有字段就会报错
     * 标识为非数据库字段的3种方式：
     * 1.transient关键字，标识该字段不参与序列化
     * 2.将字段使用static静态标识，但会导致全类共用一个属性，一般不会用
     * 3.使用@TableField注解，将exist属性设置为false，来表示不是数据库字段
     */
    //private transient String remark;
    //private static String remark;
    @TableField(exist = false)
    private String remark;
}