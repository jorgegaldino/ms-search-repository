package uk.co.jorgegaldino.searchrepository.bean;

import lombok.Data;

@Data
public class Item {
	public int    id;
	public String name;
	public Owner owner;
	public String clone_url;
}
