package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.stereotype.Component;
/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${cfg.author}
* @since ${cfg.currentDate}
*/
@Component
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
