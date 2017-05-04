package it.sourcesense.microservice.controller;

import com.google.common.collect.ImmutableMap;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import it.sourcesense.microservice.vo.Member;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/member"})
@Component
public class MembershipController
{
  private static final Logger LOGGER = LoggerFactory.getLogger(MembershipController.class);
  private final Random rand = new Random();
  private final Map<String, Member> memberStore = ImmutableMap.of("Fernando", new Member("Fernando", Integer.valueOf(10)), "Pablo", new Member("Pablo", 
    Integer.valueOf(30)));
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public Member register(@RequestBody Member member)
  {
    this.memberStore.put(member.getUser(), member);
    
    return member;
  }
  
  @RequestMapping({"/{user}"})
  @HystrixCommand(fallbackMethod="membershipFallback", commandProperties={@com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000000")})
  public Member login(@PathVariable String user)
  {
    delay();
    
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
