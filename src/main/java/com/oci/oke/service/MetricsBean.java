package com.oci.oke.service;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class MetricsBean {
    
    public MetricsBean(long numberOfInvocationsPerMinute, double averageLatency) {
        this.numberOfInvocationsPerMinute = numberOfInvocationsPerMinute;
        this.averageLatency = averageLatency;
    }

    public long getNumberOfInvocationsPerMinute() {
        return numberOfInvocationsPerMinute;
    }
    public void setNumberOfInvocationsPerMinute(long numberOfInvocationsPerMinute) {
        this.numberOfInvocationsPerMinute = numberOfInvocationsPerMinute;
    }
    public double getAverageLatency() {
        return averageLatency;
    }
    public void setAverageLatency(double averageLatency) {
        this.averageLatency = averageLatency;
    }

    private long numberOfInvocationsPerMinute;
    private double averageLatency;  
}
