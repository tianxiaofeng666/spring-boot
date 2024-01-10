package ${package.Controller};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    private ${table.serviceName} service;

}

