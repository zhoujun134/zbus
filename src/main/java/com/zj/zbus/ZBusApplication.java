package com.zj.zbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zj"})
public class ZBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZBusApplication.class, args);
	}

}
