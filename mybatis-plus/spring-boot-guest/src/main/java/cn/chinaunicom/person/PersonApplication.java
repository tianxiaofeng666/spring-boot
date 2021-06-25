package cn.chinaunicom.person;

import javax.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@SpringBootApplication()
@MapperScan("cn.chinaunicom.person.mapper")
@ServletComponentScan
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

	@PostConstruct
	public void init()
	{

	}
}
