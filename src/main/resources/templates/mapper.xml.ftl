<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
    <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.name},
</#list>
        ${table.fieldNames}
    </sql>
</#if>

    <!-- 串表查询 -->
    <select id="getTablesByContition" parameterType="${cfg.prefix}.base.model.QueryModel${entity}" resultType="${cfg.prefix}.base.entity.${entity}" >
        select main.id,main.code,main.type_code,slave.name as type_name from ${entity} main
        left join equipment_type slave
        on main.type_code=slave.code
        <where>
            1=1
            <if test="condition.code!=null and condition.code!=''" >
                and main.code = # {condition.code}
            </if>
            <if test="condition.name!=null and condition.name!=''">
                and main.name like CONCAT('%',# {condition.name},'%')
            </if>
            and main.is_deleted=false and slave.is_deleted=false
        </where>
    </select>
</mapper>
