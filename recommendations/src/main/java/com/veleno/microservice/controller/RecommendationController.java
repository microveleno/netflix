package com.veleno.microservice.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.veleno.microservice.exception.UserNotFoundException;
import com.veleno.microservice.vo.Book;
import com.veleno.microservice.vo.Member;

@RestController
@RequestMapping("/api/recommendations")
@Component
public class RecommendationController {
	
	//private static final Logger LOG = Logger.getLogger(RecommendationsApplication.class.getName());

	  private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationController.class);
	  //private static final Logger LOGGER = Logger.getLogger(MembershipController.class.getName());

  
	 @Autowired
	    private RestTemplate restTemplate;
	Set<Book> kidRecommendations = Sets.newHashSet(new Book("Harry Potter"), new Book("Pinocchio"));
	Set<Book> adultRecommendations = Sets.newHashSet(new Book("Fondazione anno zero"), new Book("Stand by me"));
	Set<Book> familyRecommendations = Sets.newHashSet(new Book("il piccolo principe"),
			new Book("il piccolo lord"));
	
	

 
    @RequestMapping(value = "/{user}", method = RequestMethod.GET, produces = "application/json")
    @HystrixCommand(fallbackMethod = "recommendationsFallback",
        ignoreExceptions  = UserNotFoundException.class,
        commandProperties =  {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
        })
    public Set<Book> findRecommendationsForUser(@PathVariable String user) throws UserNotFoundException {
        Member member = restTemplate.getForObject("http://membership/api/member/{user}", Member.class, user);

        if(member == null) throw new UserNotFoundException();
        return member.getAge() < 17 ? kidRecommendations : adultRecommendations;
    }
    public Set<Book> recommendationsFallback(String user) {
        return familyRecommendations;
    }

	
	
}


