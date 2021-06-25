package ${package.Service};

import ${package.Entity}.${entity};
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ${cfg.author}
 * @since ${cfg.currentDate}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    public Page<${table.entityName}> queryByCondition(JSONObject json);

}
