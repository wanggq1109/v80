package com.apple;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.common.domain.Shop;
import com.arthouse.common.domain.ShopInfo;
import com.s2sh.page.service.TemplateManager;

public class NumTest {

	static ApplicationContext ac;

	static TemplateManager templateManager;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ac = new ClassPathXmlApplicationContext("spring.*.xml");
		templateManager = (TemplateManager) ac.getBean("TemplateManager");

		String[] strArray = { "26", "27", "28", "29", "30", "31", "32", "33",
				"34", "35", "36", "37", "38", "39", "40", "41", "42", "43",
				"44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
				"54", "55", "56" };

		List<Shop> list = templateManager.getAllshopInfo();
		for (Shop shop : list) {

			ShopInfo shopinfo = new ShopInfo();
			for (int i = 0; i < strArray.length; i++) {
				shopinfo.setHairId(Integer.parseInt(strArray[i]));
				shopinfo.setBusinessId(shop.getId());
				shopinfo.setIsDel(0);
				templateManager.save(shopinfo);

			}

		}

	}

}
