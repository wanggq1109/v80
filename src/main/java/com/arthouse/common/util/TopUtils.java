package com.arthouse.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.goldfish.service.IndexService;

public class TopUtils {

	private static TopUtils obj = new TopUtils();

	ApplicationContext ac = new ClassPathXmlApplicationContext("spring.*.xml");

	IndexService indexService = (IndexService) ac
			.getBean("indexService");

	
	private TopUtils() {

	}

	public static TopUtils getInstatnce() {

		return obj;
	}
	
	public 	List<PhtotoGraphyWroks> getallslides(){
		
		List<PhtotoGraphyWroks> slideList = new ArrayList<PhtotoGraphyWroks>();
		try {
			slideList = indexService.getAllslides();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return slideList;
	}
	



}
