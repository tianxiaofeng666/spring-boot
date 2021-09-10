package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.example.plus.bean.RestfulResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.example.plus.utils.Common;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ${cfg.author}
 * @since ${cfg.currentDate}
 */
@Api(tags = "${package.ModuleName}")
@RestController
@RequestMapping("${cfg.path}${package.ModuleName}")
public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} service;

    @PostMapping("/add")
    @ApiOperation(value = "add", notes = "新增")
    public RestfulResponse add(@RequestBody ${table.entityName} entity) {
        try {
            service.save(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("add", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "delete", notes = "删除")
    public RestfulResponse delete(@RequestBody JSONObject json) {
        try {
            long id = json.getLong("id");
            service.removeById(id);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("delete ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "update", notes = "更新")
    public RestfulResponse update(@RequestBody ${table.entityName} entity) {
        try {
            service.updateById(entity);
            return RestfulResponse.success();
        } catch (Exception ex) {
            Common.logger.error("update ", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }

    /**
    * 根据条件查询并分页,自定义QueryWrapper
    */
    @PostMapping("/queryByCondition")
    @ApiOperation(value = "query", notes = "查询")
    public RestfulResponse queryByCondition(@RequestBody JSONObject json) {
        try {
            Page<${table.entityName}> page = service.queryByCondition(json);
            return RestfulResponse.success(page);
        } catch (Exception ex) {
            Common.logger.error("query", ex);
            return RestfulResponse.failed(ex.getMessage());
        }
    }
}

