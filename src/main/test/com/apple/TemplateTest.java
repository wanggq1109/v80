package com.apple;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.common.domain.Shop;
import com.arthouse.common.domain.ShopInfo;
import com.s2sh.page.service.TemplateManager;

//第一次请执行main方法生成�?
public class TemplateTest {

	static ApplicationContext ac;

	static TemplateManager templateManager;


	public static void main(String[] args) {
		//beforeClass();
		ac = new ClassPathXmlApplicationContext("spring.*.xml");
		templateManager = (TemplateManager) ac.getBean("TemplateManager");
		 
		try {
			List<Shop>	list = templateManager.getAllshopInfo();
			ShopInfo shopinfo = null;
			for(Shop shop : list){
				
				System.out.println("id---"+shop.getId()+"--hariId---"+shop.getHairId());
				String[] harids = shop.getHairId().split(";");
				
				for(int i=0;i<harids.length;i++){
					 String myid = harids[i];
					 shopinfo = new ShopInfo();
					 shopinfo.setHairId(Integer.parseInt(myid));
					 shopinfo.setId(shop.getId());
					 shopinfo.setIsDel(0);
					 templateManager.save(shopinfo);
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
