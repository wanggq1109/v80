package com.apple;


import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.security.UserDetailsService;

//第一次请执行main方法生成表
public class CodeType {

    static ApplicationContext ac;


    static PhotoTypeService phototypeservice;


    @BeforeClass
    public static void beforeClass() {

        try {
			ac = new ClassPathXmlApplicationContext("spring.*.xml");
			phototypeservice = (PhotoTypeService) ac
					.getBean("photoTypeService");
			System.out.println(phototypeservice + "---------执行成功!");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }

    @AfterClass
    public static void afterClass() {

    }

    @Test
    public void testSchemaExport() {
        new SchemaExport(new AnnotationConfiguration().configure()).create(
                false, true);
    }
    
    
    @Test
    public void TestCode(){
    	
    	
    	
    }




    public static void main(String[] args) {
        beforeClass();

    }
}
