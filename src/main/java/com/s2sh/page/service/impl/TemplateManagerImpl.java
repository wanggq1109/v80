package com.s2sh.page.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.arthouse.common.dao.ICommonDao;
import com.arthouse.common.domain.Shop;
import com.arthouse.common.page.Page;
import com.s2sh.page.model.Template;
import com.s2sh.page.service.TemplateManager;

@SuppressWarnings("unchecked")
public class TemplateManagerImpl implements TemplateManager {

	private ICommonDao dao;

	public ICommonDao getDao() {
		return dao;
	}

	public void setDao(ICommonDao dao) {
		this.dao = dao;
	}

	public void save(Object obj) throws Exception {

		dao.save(obj);
	}

	public void update(Object obj) throws Exception {

		dao.update(obj);
	}

	public void delete(Object obj) throws Exception {

		dao.delete(obj);
	}

	public List<Template> getTemplateList(Template template, Page page) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Template t ");
		List params = new ArrayList();
		hql.append(" where 1=1 ");

		if (template != null) {
			String name = template.getName();
			if (org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
				hql.append("and t.name = ?");
				params.add(name);
			}
		}
		return dao.query(hql.toString(), page, params.toArray());
	}

	public List<Shop> getAllshopInfo() throws Exception {
		String hql = " from Shop p  ";
		List<Shop> shoplist = dao.getListByHql(hql);
		return shoplist;

	}

}
