package com.veleno.microservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.veleno.microservice.vo.Member;

@RestController
@RequestMapping({"/api/member"})
@Component
public class MembershipController  {
	
	@Autowired
	private Tracer tracer;
	@Autowired
	private SpanAccessor accessor;

  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipController.class);
  //private static final Logger LOGGER = Logger.getLogger(MembershipController.class.getName());

  private final Random rand = new Random();
  private final Map<String, Member> memberStore = new HashMap<String, Member>();
  @PostConstruct
  public void init(){
	  this.memberStore.put( "Pablo", new Member("Pablo", 
			    Integer.valueOf(30)));
	  this.memberStore.put( "Fernando", new Member("Fernando", 
			    Integer.valueOf(10)));
  }
		  
		 
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Member register(@RequestBody Member member)
  {
    this.memberStore.put(member.getUser(), member);
    
    return member;
  }
  
  @Autowired
	private SpanMetricReporter spanMetricReporter;
  
  @RequestMapping(value = "/{user}", method = RequestMethod.GET, produces = "application/json")
  @HystrixCommand(fallbackMethod="membershipFallback", commandProperties={@com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")})
  public Member login(@PathVariable String user)
  {
	
    delay();
//    this.accessor.isTracing();
//    Span span = this.accessor.getCurrentSpan();
    this.tracer.addTag("callable-sleep-millis", String.valueOf(10));
    Member member = (Member)this.memberStore.get(user);
    
    LOGGER.info(member.toString());
    return member;
  }
  
  public Member membershipFallback(String user)
  {
    LOGGER.info("membershipFallback");
    return (Member)this.memberStore.get(user);
  }
  
  private void delay()
  {
    try
    {
      Thread.sleep((int)(Math.abs(2.0D + this.rand.nextGaussian() * 15.0D) * 100.0D));
    }
    catch (InterruptedException e)
    {
      throw new RuntimeException(e);
    }
  }
  

	
}
