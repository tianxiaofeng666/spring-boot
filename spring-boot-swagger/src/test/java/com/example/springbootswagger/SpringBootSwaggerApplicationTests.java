package com.example.springbootswagger;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.net.URL;
import java.nio.file.Paths;

/**
 * 参考：https://blog.csdn.net/fly910905/article/details/105504324
 * 第一步： 生成adoc文件
 * 第二步： 将adoc文件转换成html/pdf 文件
 */
@RunWith(SpringRunner.class)
public class SpringBootSwaggerApplicationTests {

    @Test
    public void generateAsciiDocs() throws Exception {
        //输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        //swagger访问不要设置用户名、密码，否则无权限读取
        Swagger2MarkupConverter.from(new URL("http://localhost:9099/v2/api-docs"))
                .withConfig(config)
                .build()
                //将转换结果输出到一个单一的文件中，这样最终生成html/pdf也是单一的
                .toFile(Paths.get("src/docs/asciidoc/generated/all"));
    }
}

