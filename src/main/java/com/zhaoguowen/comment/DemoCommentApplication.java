package com.zhaoguowen.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhaoguowen.comment.mapper")
public class DemoCommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCommentApplication.class, args);
	}
}
