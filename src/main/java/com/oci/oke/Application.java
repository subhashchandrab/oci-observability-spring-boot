package com.oci.oke;

import com.oci.oke.service.MetricsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = { "com.oci.oke" })
@EnableScheduling
@RestController
public class Application {

	private MetricsService metricService;

	@Autowired
	public void setMetricService(MetricsService metricService) {
		this.metricService = metricService;
	}

	@RequestMapping("/")
	public String home() {
		System.out.println("This is the default path of the application.");
		return "<h1>Spring Boot Hello World!</h1>";
	}

	@RequestMapping("/generateLogs")
	public String generateLogs() {
		System.out.println("Generating some test logs for verification");
		for (int i = 0; i < 10; i++) {
			System.out.println("Test log statement:" + i);
		}
		return "<h1>Generated some sample log statement </h1>";
	}

	@RequestMapping("/sendMetrics")
	public String sendMetrics() {
		System.out.println("Sending application custom metrics to OCI");
		metricService.publishMetrics();
		return "<h1>Sent the metrics to OCI </h1>";
	}	

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}