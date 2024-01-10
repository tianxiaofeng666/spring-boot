package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
<#--import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;-->
import org.springframework.beans.factory.annotation.Autowired;
<#--import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;-->
import lombok.extern.slf4j.Slf4j;

/**
 * @author ${cfg.author}
 * @date ${cfg.currentDate}
 */
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private ${table.mapperName} mapper;

    <#--@Override
    public Page<${table.entityName}> queryByCondition(JSONObject json) {
        Page<${table.entityName}> page = new Page<>(json.getInteger("pageNum"), json.getInteger("pageSize"));
        QueryWrapper<${table.entityName}> queryWrapper = new QueryWrapper<${table.entityName}>();
        //根据查询条件构建queryWrapper
        if(StringUtils.isNotBlank(json.getString(""))){
            queryWrapper.eq("column",json.getString(""));
        }
        //添加排序字段
        queryWrapper.orderByDesc("id");
        Page<${table.entityName}> queryPage = mapper.selectPage(page, queryWrapper);
        return queryPage;
    }-->

}

