package com.oci.oke.scheduler;

import java.sql.Timestamp;

import com.oci.oke.service.MetricsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricJobScheduler {
	private MetricsService metricService;

	@Autowired
	public void setMetricService(MetricsService metricService) {
		this.metricService = metricService;
	}
     
    @Scheduled(fixedRateString="${oci.metricsPushInterval}")
    public void pushMetricsToOci(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp + "  Pushing the application specific metrics to OCI");
        metricService.publishMetrics();
    }
}
