package com.jfinteck.dmq.job.step;

import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderWriterDatabaseStepHandler {

	private StepBuilderFactory stepBuilderFactory;
	
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.<String, String> chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	public ItemWriter<String> writer() {
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				items.forEach(item -> log.info("写入数据：" + item));
			}
		};
	}
	public ItemProcessor<String, String> processor() {
		return new ItemProcessor<String, String>() {
			@Override
			public String process(String item) throws Exception {
				return item;
			}
		};
	}
	public ItemReader<? extends String> reader() {
		return new ItemReader<String>() {
			@Override
			public String read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				Thread.sleep(1000);
				return "ppppp";
			}
		};
	}
}
