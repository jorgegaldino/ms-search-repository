package uk.co.jorgegaldino.searchrepository.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uk.co.jorgegaldino.searchrepository.bean.Item;
import uk.co.jorgegaldino.searchrepository.bean.RepositoryGitHubBean;
import uk.co.jorgegaldino.searchrepository.dto.RepositoryDTO;

@Service
public class RepositoryService {
	
	Logger Log = Logger.getLogger(RepositoryService.class.getName());
	
	@Autowired
	public RestTemplate restTemplate;
	
	
	public List<RepositoryDTO> findByLanguage(String language, int page) {
		language = languageResolver(language); // for some languages like C# C++;
		RepositoryGitHubBean bean = runRestService(language, page);
	
		return converterToDTO(bean);
	}
	
	private RepositoryGitHubBean runRestService(String language, int page) {
		try {
			
			RepositoryGitHubBean bean = restTemplate.getForObject(
					 String.format("https://api.github.com/search/repositories?sort=stars&order=desc&q=language:%s&per_page=100&page=%d",language,page), RepositoryGitHubBean.class);
			return bean;
		} catch (HttpClientErrorException e) {
			Log.info("Empty result");
		}
		
		return null;
	}
	
	private String languageResolver(String language) {
		if (language.equalsIgnoreCase("C++")) {
			return "CPP";
		} else if (language.equalsIgnoreCase("C#")) {
			return "CSharp";
		}else {
			return language;
		}
		
	}

	private List<RepositoryDTO> converterToDTO(RepositoryGitHubBean bean) {
		List<RepositoryDTO> asDto = new ArrayList<RepositoryDTO>(); 
		
		if (bean != null) {
			for (Item item : bean.getItems()) {
				RepositoryDTO repository1 = new RepositoryDTO();
				
				
				repository1.setName(item.getName());
				repository1.setProjectId(item.getId());
				repository1.setUrl(item.getClone_url());
				repository1.setOwnerLogin(item.getOwner().getLogin());
				asDto.add(repository1);
			}
		}

		
		
	
		return asDto;
	}
	

	public List<RepositoryDTO> findAllByLanguage(String language) {
		List<RepositoryDTO> repositories = new ArrayList<RepositoryDTO>();
		language = languageResolver(language); // for some languages like C# C++;		
		for(int i=1; i <= 10; i++) { //max 1000 results (10 pages of 100)
			RepositoryGitHubBean bean = runRestService(language,i);
			if (bean == null) {
				break;
			}
			repositories.addAll(converterToDTO(bean));
		}
		
		
		return repositories;
	}
	

}
