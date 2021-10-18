package com.oci.oke.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(("ociConfig"))
@ComponentScan
@ConfigurationProperties(prefix = "oci")
public class ApplicationConfigProperties {

	private Boolean useInstancePrincipal;
	private String ociConfigPath;
	private String ociProfile;
	private String metricsCompartmentOcid;
	private String metricsNamespace;
	private String metricsEndpoint;
	private int metricsPushInterval;

	public String getMetricsEndpoint() {
		return metricsEndpoint;
	}

	public void setMetricsEndpoint(String metricsEndpoint) {
		this.metricsEndpoint = metricsEndpoint;
	}

	public String getMetricsNamespace() {
		return metricsNamespace;
	}

	public int getMetricsPushInterval() {
		return metricsPushInterval;
	}

	public void setMetricsPushInterval(int metricsPushInterval) {
		this.metricsPushInterval = metricsPushInterval;
	}

	public void setMetricsNamespace(String metricsNamespace) {
		this.metricsNamespace = metricsNamespace;
	}

	public String getMetricsCompartmentOcid() {
		return metricsCompartmentOcid;
	}

	public void setMetricsCompartmentOcid(String metricsCompartmentOcid) {
		this.metricsCompartmentOcid = metricsCompartmentOcid;
	}

	public String getOciConfigPath() {
		return ociConfigPath;
	}

	public void setOciConfigPath(String ociConfigPath) {
		this.ociConfigPath = ociConfigPath;
	}

	public String getOciProfile() {
		return ociProfile;
	}

	public void setOciProfile(String ociProfile) {
		this.ociProfile = ociProfile;
	}

	public Boolean getUseInstancePrincipal() {
		return useInstancePrincipal;
	}

	public void setUseInstancePrincipal(Boolean useInstancePrincipal) {
		this.useInstancePrincipal = useInstancePrincipal;
	}

}
