package it.sourcesense.microservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import it.sourcesense.microservice.MembershipApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MembershipApplication.class)
@WebAppConfiguration
public class MembershipApplicationTests {

	@Test
	public void contextLoads() {
	}

}
