package com.sriharilabs.springbatch.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sriharilabs.springbatch.model.Summary;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, String> {

	
	List<Summary> findByModelId(String modelId);
}
