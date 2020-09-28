package uk.co.jorgegaldino.searchrepository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "RepositoryModel", description = "Repository Model")
public class RepositoryDTO {
	
	@ApiModelProperty(value = "Project ID", example = "83222441")
	private Integer projectId;
	
	@ApiModelProperty(value = "Name", example = "system-design-primer")
	private String name;
	
	@ApiModelProperty(value = "URL", example = "https://github.com/donnemartin/system-design-primer.git")
	private String url;
	
	@ApiModelProperty(value = "Owner Login", example = "donnemartin")
	private String ownerLogin;
}
