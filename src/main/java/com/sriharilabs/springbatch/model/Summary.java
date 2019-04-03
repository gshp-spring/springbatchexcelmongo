// tag::sample[]
package com.sriharilabs.springbatch.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "summary")
public class Summary {
     @Id
	Integer id;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	Map<String, String> mandatory;

	List<Map<String, String>> filters;


	public Map<String, String> getMandatory() {
		return mandatory;
	}

	public void setMandatory(Map<String, String> mandatory) {
		this.mandatory = mandatory;
	}

	public List<Map<String, String>> getFilters() {
		return filters;
	}

	public void setFilters(List<Map<String, String>> filters) {
		this.filters = filters;
	}

	
}
