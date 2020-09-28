package uk.co.jorgegaldino.searchrepository.bean;

import java.util.List;

import lombok.Data;

@Data
public class RepositoryGitHubBean {
	public int total_count;
	public boolean incomplete_results;
	public List<Item> items;
}
