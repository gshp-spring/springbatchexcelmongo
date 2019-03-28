package com.sriharilabs.springbatch.step;
 
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.sriharilabs.springbatch.model.Summary;
 
public class Writer implements ItemWriter<List<Summary>> {
 
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    

	@Override
	public void write(List<? extends List<Summary>> items) throws Exception {
		
		items.get(0).forEach(d->{
			System.out.println(d.getTdmTableName()+"   "+d.getMvmUseCases());
		});
	}
     
}