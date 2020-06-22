package ml.ytooo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger API配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

        @Bean
        public Docket api(){
            //添加head参数start
            ParameterBuilder tokenPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<Parameter>();
            tokenPar.name("sid").description("登录凭证").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
            pars.add(tokenPar.build());
            //添加head参数end


            return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(pars);
        }

}
