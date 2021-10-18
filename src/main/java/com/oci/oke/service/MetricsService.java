package com.oci.oke.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oci.oke.config.ApplicationConfigProperties;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.monitoring.MonitoringClient;
import com.oracle.bmc.monitoring.model.Datapoint;
import com.oracle.bmc.monitoring.model.MetricDataDetails;
import com.oracle.bmc.monitoring.model.PostMetricDataDetails;
import com.oracle.bmc.monitoring.requests.PostMetricDataRequest;

@Component
public class MetricsService {
	private MonitoringClient monitoringClient;

	private ApplicationConfigProperties ociConfig;

	@Autowired
	public MetricsService(ApplicationConfigProperties ociConfig) throws IOException {

		this.ociConfig = ociConfig;
//		System.out.println("Read the application configuration properties: " + ociConfig);
		BasicAuthenticationDetailsProvider provider = null;
		if(this.ociConfig.getUseInstancePrincipal()) {
			System.out.println("Using the instance principal for OCI authentication");
			provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
		}
		else {
			try {
				System.out.println("Using the config profile for OCI authentication");
				provider = new ConfigFileAuthenticationDetailsProvider(ociConfig.getOciConfigPath(), ociConfig.getOciProfile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		monitoringClient = MonitoringClient.builder().endpoint(this.ociConfig.getMetricsEndpoint()).build(provider);
	}

	public void publishMetrics() {
		List<MetricDataDetails> metricDataDetailsList = new ArrayList<>();
		MetricsBean metricsBean = getMetricsBean();
		// MetricDataDetails loadMetricDataDetails1 =
		// getMetricDataDetails(metricsBean.getNumberOfInvocationsPerMinute(),
		// "NumberOfInvocationsPerMinute");
		MetricDataDetails loadMetricDataDetails2 = getMetricDataDetails(metricsBean.getAverageLatency(), "API_Latency");
		// metricDataDetailsList.add(loadMetricDataDetails1);
		metricDataDetailsList.add(loadMetricDataDetails2);

		PostMetricDataDetails postMetricDataDetails = PostMetricDataDetails.builder().metricData(metricDataDetailsList)
				.build();
		PostMetricDataRequest postMetricDataRequest = PostMetricDataRequest.builder()
				.postMetricDataDetails(postMetricDataDetails).build();
		monitoringClient.postMetricData(postMetricDataRequest);
	}

	private MetricDataDetails getMetricDataDetails(double metricValue, String metricName) {

		Datapoint dataPoint = Datapoint.builder().value(Double.valueOf(metricValue)).timestamp(new Date()).count(1)
				.build();

		Map<String, String> metricMetadataMap = new HashMap<String, String>();
		metricMetadataMap.put("unit", "time_ms");
		metricMetadataMap.put("displayName", "Process_Latency_ms");
		Map<String, String> metricDimensionMap = new HashMap<String, String>();
		metricDimensionMap.put("appName", "oke-springboot");
		MetricDataDetails loadMetricDataDetails = MetricDataDetails.builder()
				.compartmentId(this.ociConfig.getMetricsCompartmentOcid())
				.namespace(this.ociConfig.getMetricsNamespace()).dimensions(metricDimensionMap)
				.metadata(metricMetadataMap).resourceGroup("oke-app-metrics").name(metricName)
				.datapoints(new ArrayList<>(Arrays.asList(new Datapoint[] { dataPoint }))).build();
		System.out.println("Collected metrics: " + dataPoint);
		return loadMetricDataDetails;
	}

	private MetricsBean getMetricsBean() {
		// TODO
		Random random = new Random();
		IntStream ints = random.ints(10, 100);
		Integer nextInt = ints.iterator().next();
		double time = Math.floor(Math.random() * 3000);
		return new MetricsBean(nextInt, time);
	}
}
