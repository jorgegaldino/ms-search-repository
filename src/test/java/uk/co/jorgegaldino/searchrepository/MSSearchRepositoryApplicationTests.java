package uk.co.jorgegaldino.searchrepository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import uk.co.jorgegaldino.searchrepository.bean.RepositoryGitHubBean;

@SpringBootTest
public class MSSearchRepositoryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void successPath() {
				
		RepositoryGitHubBean bean = runRestService("java",10);
		assertEquals(bean.getItems().size(),100);
	}
	
	private RepositoryGitHubBean runRestService(String language, int page) {
			
		RepositoryGitHubBean bean = restTemplate.getForObject(
				 String.format("https://api.github.com/search/repositories?sort=stars&order=desc&q=language:%s&per_page=100&page=%d",language,page), RepositoryGitHubBean.class);
		
		return bean;
	}

}
