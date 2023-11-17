package HealthCare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PrescriptionServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PrescriptionServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
