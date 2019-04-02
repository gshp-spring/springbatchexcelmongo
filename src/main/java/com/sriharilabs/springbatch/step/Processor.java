package com.sriharilabs.springbatch.step;
 
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.sriharilabs.springbatch.model.Summary;
 
@Service
public class Processor implements ItemProcessor<Summary,Summary>{
 
    @Override
    public Summary process(Summary content) throws Exception {
    	
    	//System.out.println(content.getTdmTableName()+"ITS..... process.."+content.getModelId());
    	
    	//System.out.println(content.getModelId()+ " Processor...."+content.getTdmTableName());
        return content;
    }
 
}