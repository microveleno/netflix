package com.veleno.microservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableAsync
public class MembershipApplication {
	
	private static final Log log = LogFactory.getLog(MembershipApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MembershipApplication.class, args);
	}
	
	// Use this for debugging (or if there is no Zipkin server running on port 9411)
//		@Bean
//		//@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
//		public ZipkinSpanReporter spanCollector() {
//			return new ZipkinSpanReporter() {
//				@Override
//				public void report(zipkin.Span span) {
//					log.info(String.format("Reporting span [%s]", span));
//				}
//			};
//		}
		//actuator for zipkin
		@Bean
		public AlwaysSampler defaultSampler() {
		  return new AlwaysSampler();
		}
		@LoadBalanced
		@Bean
		RestTemplate restTemplate() {
			return new RestTemplate();
		}
		
		
		
}	



