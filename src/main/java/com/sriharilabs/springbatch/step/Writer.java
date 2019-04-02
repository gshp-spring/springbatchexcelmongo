package com.sriharilabs.springbatch.step;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sriharilabs.springbatch.model.Summary;
import com.sriharilabs.springbatch.repository.SummaryRepository;
 
@Service
public class Writer implements ItemWriter<Summary> {
 
    Logger logger = LoggerFactory.getLogger(this.getClass());
     
    @Autowired
    SummaryRepository summaryRepository;

	@Override
	public void write(List<? extends Summary> items) throws Exception {
		
		
		items.forEach(d->{
			summaryRepository.save(d);
			//System.out.println(d.getTdmTableName()+" writter  "+d.getMvmUseCases());
		});
	}
     
}