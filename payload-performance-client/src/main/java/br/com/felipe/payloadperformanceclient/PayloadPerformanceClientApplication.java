package br.com.felipe.payloadperformanceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PayloadPerformanceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayloadPerformanceClientApplication.class, args);
	}

}
