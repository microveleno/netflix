package it.sourcesense.microservice.restcontroller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import it.sourcesense.microservice.exception.UserNotFoundException;
import it.sourcesense.microservice.vo.Book;
import it.sourcesense.microservice.vo.Member;

@RestController
@RequestMapping("/api/recommendations")
@Component
public class RecommendationController {
  
	 @Autowired
	    private RestTemplate restTemplate;
	Set<Book> kidRecommendations = Sets.newHashSet(new Book("Harry Potter"), new Book("Pinocchio"));
	Set<Book> adultRecommendations = Sets.newHashSet(new Book("Fondazione anno zero"), new Book("Stand by me"));
	Set<Book> familyRecommendations = Sets.newHashSet(new Book("il piccolo principe"),
			new Book("il piccolo lord"));
	
	

    @RequestMapping("/{user}")
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


