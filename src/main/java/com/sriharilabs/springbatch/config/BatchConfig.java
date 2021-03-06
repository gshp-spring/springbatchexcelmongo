package com.sriharilabs.springbatch.config;
 
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sriharilabs.springbatch.model.Summary;
import com.sriharilabs.springbatch.step.Processor;
import com.sriharilabs.springbatch.step.Reader;
import com.sriharilabs.springbatch.step.Writer;


 
@Configuration
public class BatchConfig extends DefaultBatchConfigurer{
 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
 
    @Autowired
    Processor processor;
    @Autowired
    Writer writer;
    @Autowired
    Reader reader;
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
 
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .< Summary, Summary> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
