package com.arthouse.goldfish.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.dto.MicroFilmDTO;
import com.arthouse.common.page.Page;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.Action;

/**
 * 
 * @author wanggq
 * 
 *         微电影前台展示
 * 
 */
public class MicroFilmAction extends BaseAction {

	private IndexService indexService;

	private Page page;

	List<VideoInfo> pageList = new ArrayList<VideoInfo>();

	public String film_init() throws Exception {
		page = new Page(14, pageIndex, true);
		indexService.getMicrofilmListPage(page, null);
		int count = page.getTotalRecordCount();
		request.setAttribute("count", count);

		return SUCCESS;
	}

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

	/**
	 * 分页显示所有微电影
	 * @return
	 * @throws Exception
	 */
	public String ajaxforMicroFilmPage() throws Exception {

		page = new Page(20, pageIndex, true);
		indexService.getMicrofilmListPage(page, null);
		pageList = page.getRecord();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		
		List<MicroFilmDTO> dataList = new ArrayList<MicroFilmDTO>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for(VideoInfo film :pageList){
			
			MicroFilmDTO dto = new MicroFilmDTO();
			
			dto.setId(film.getId());
			dto.setName(film.getName());
			dto.setShootingDate(sdf.format(film.getShootingDate()));
			dto.setSuolueurl(film.getSuolueurl());
			dto.setUrl(film.getUrl());
			dto.setType(film.getType());
			dto.setUploadTime(film.getUploadTime());
			dto.setIntro(film.getIntro());
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
