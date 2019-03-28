// tag::sample[]
package com.sriharilabs.springbatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Summary {

	
   public String getMvmUseCases() {
		return mvmUseCases;
	}
	public void setMvmUseCases(String mvmUseCases) {
		this.mvmUseCases = mvmUseCases;
	}
	public String getTdmTableName() {
		return tdmTableName;
	}
	public void setTdmTableName(String tdmTableName) {
		this.tdmTableName = tdmTableName;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
private String mvmUseCases;
   private String tdmTableName;
   @Id
   private String modelId;
   
    
  

}
