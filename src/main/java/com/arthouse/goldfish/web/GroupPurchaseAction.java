package com.arthouse.goldfish.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.dto.GroupItemDTO;
import com.arthouse.common.dto.NewsDTO;
import com.arthouse.common.page.Page;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.Action;
/**
 * 
 * 前台团购管理
 * 
 * @author wanggq
 *
 */
public class GroupPurchaseAction extends BaseAction {
	
	
	private IndexService indexService;
	
	private Page page;
	
	
	List<GroupItemsInfo> pageList = new ArrayList<GroupItemsInfo>();

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public List<GroupItemsInfo> getPageList() {
		return pageList;
	}

	public void setPageList(List<GroupItemsInfo> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 团购列表分页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxgroupItems_page() throws Exception {

		page = new Page(15, pageIndex, true);
		indexService.getGroupitemInfoListPage(page);
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		List<GroupItemDTO> dataList = new ArrayList<GroupItemDTO>();
		for (GroupItemsInfo groupitem : pageList) {
			GroupItemDTO dto = new GroupItemDTO();
			dto.setId(groupitem.getId());
			String title = groupitem.getTitle();
			if(title.length()>6){
				
				title = title.substring(0, 6) + "...";
			}
			dto.setTitle(title);
			String descr = groupitem.getDescr();
			if (descr.length() > 50) {

				descr = descr.substring(0, 50) + "...";
			}
			dto.setDescr(descr);
			dto.setPrice(groupitem.getPrice());
			dto.setOld_price(groupitem.getOld_price());
			dto.setContent(groupitem.getContent());
			dto.setUrl(groupitem.getUrl());
			dataList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(dataList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageCount", page.getPageCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		out.flush();
		out.close();

		return Action.SUCCESS;
	}

	
	


}
