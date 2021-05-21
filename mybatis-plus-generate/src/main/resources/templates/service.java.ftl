package ${package.Service};

import ${package.Entity}.${entity};
import com.example.plus.model.${table.entityName}QueryModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ${cfg.author}
 * @since ${cfg.currentDate}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    public int removeByModel(${table.entityName}QueryModel queryModel);

    public Page<${table.entityName}> queryByModel(${table.entityName}QueryModel queryModel);

}
