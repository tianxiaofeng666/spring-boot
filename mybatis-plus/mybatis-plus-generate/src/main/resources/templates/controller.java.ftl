package ${package.Controller};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ${package.Service}.${table.serviceName};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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

