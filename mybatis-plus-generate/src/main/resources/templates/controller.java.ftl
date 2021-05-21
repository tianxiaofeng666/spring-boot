package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.example.plus.bean.RestfulResponse;
import com.example.plus.utils.Common;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import com.example.plus.model.${table.entityName}QueryModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ${cfg.author}
 * @since ${cfg.currentDate}
 */
@RestController
@RequestMapping("${cfg.path}${package.ModuleName}")
public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} service;

    @PostMapping("/add")
    public RestfulResponse add(@RequestBody ${table.entityName} entity) {
        try {
            boolean b = service.save(entity);
            if (!b) {
                throw new Exception("add异常");
            }
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("add", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/delete")
    public RestfulResponse delete(@RequestBody ${table.entityName}QueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            service.removeByModel(queryModel);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("delete ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/update")
    public RestfulResponse update(@RequestBody ${table.entityName} entity) {
        try {
            service.updateById(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("update ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/query")
    public RestfulResponse query(@RequestBody ${table.entityName}QueryModel queryModel) {
        try {
            Common.letFieldNullIfEmpty(queryModel);
            Page<${table.entityName}> page = service.queryByModel(queryModel);
            return RestfulResponse.success(page);
        } catch (Exception ex) {
                Common.logger.error("query", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }
}

