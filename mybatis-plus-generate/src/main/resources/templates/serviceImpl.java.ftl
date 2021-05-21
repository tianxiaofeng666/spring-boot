package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.example.plus.model.${table.entityName}QueryModel;
import com.example.plus.utils.QueryWrapperUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ${cfg.author}
 * @since ${cfg.currentDate}
 */
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Resource
    private ${table.mapperName} mapper;

    public int removeByModel(${table.entityName}QueryModel queryModel) {
        QueryWrapper<${table.entityName}> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        int i = mapper.delete(queryWrapper);
        return i;
    }

    public Page<${table.entityName}> queryByModel(${table.entityName}QueryModel queryModel) {
        QueryWrapper<${table.entityName}> queryWrapper = QueryWrapperUtil.createQueryWrapper(queryModel);
        Page<${table.entityName}> page = new Page<>(queryModel.getPageNum(), queryModel.getPageSize());
        Page<${table.entityName}> queryPage = mapper.selectPage(page, queryWrapper);
        return queryPage;
    }

}

