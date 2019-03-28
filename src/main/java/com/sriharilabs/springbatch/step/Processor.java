package com.sriharilabs.springbatch.step;
 
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.sriharilabs.springbatch.model.Summary;
 
public class Processor implements ItemProcessor<List<Summary>, List<Summary>>{
 
    @Override
    public List<Summary> process(List<Summary> content) throws Exception {
    	
    	//System.out.println(content.getTdmTableName()+"ITS..... process.."+content.getModelId());
    	
    	System.out.println(content.get(0).getModelId()+ " .."+content.get(0).getTdmTableName());
        return content;
    }
 
}