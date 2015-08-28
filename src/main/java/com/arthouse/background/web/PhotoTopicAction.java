package com.arthouse.background.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.background.service.PhotoWorksService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

public class PhotoTopicAction extends BaseAction {

	private PhotoTypeService photoTyperService;

	private PhotoWorksService photoWorksService;
	
	List<PhtotoGraphyWroks> photoList = new ArrayList<PhtotoGraphyWroks>();

	public PhotoTypeService getPhotoTyperService() {
		return photoTyperService;
	}

	public void setPhotoTyperService(PhotoTypeService photoTyperService) {
		this.photoTyperService = photoTyperService;
	}

	public PhotoWorksService getPhotoWorksService() {
		return photoWorksService;
	}

	public void setPhotoWorksService(PhotoWorksService photoWorksService) {
		this.photoWorksService = photoWorksService;
	}

	public String photoworkstopic_Init() {

		String code = request.getParameter("code");
		request.setAttribute("code", code);

		return SUCCESS;
	}
	
	public String showtopicforphotos(){
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		request.setAttribute("id", id);
		request.setAttribute("code", code);
		
		return SUCCESS;
	}
	
	public String showPhotosByTopic() throws NumberFormatException, Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			
			photoList = photoWorksService.getTopicPhotosById(Long.parseLong(id));
		}
	
		
		return SUCCESS;
	}
	

	/**
	 * 新增摄影作品及服装赏析作品主题
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String photoworksTopic_add() throws IOException, ParseException {

		

		String title = request.getParameter("title");
		String shotAddress = request.getParameter("shotAddress");
		String shotTime = request.getParameter("shotTime");
		String cameraman = request.getParameter("cameraman");
		String stylists = request.getParameter("stylists");
		String photTopicDesc = request.getParameter("photTopicDesc");
		String code = request.getParameter("code");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		PhototTopic topic = new PhototTopic();
		topic.setTitle(title);
		topic.setShotAddress(shotAddress);
		topic.setShotTime(sdf.parse(shotTime));
		topic.setCameraman(cameraman);
		topic.setStylists(stylists);
		topic.setPhotTopicDesc(photTopicDesc);
		topic.setPhotoTypeCode(code);
		topic.setTime(new Date());
		String errorMessage = "";
		try {
			photoWorksService.save(topic);
			errorMessage = "1";
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;

	}

	/**
	 * 编辑作品主题信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String photoworksTopic_edit() throws NumberFormatException,
			Exception {

		String errorMessage = "";
		String title = request.getParameter("title");
		String id = request.getParameter("id");
		String shotAddress = request.getParameter("shotAddress");
		String shotTime = request.getParameter("shotTime");
		String cameraman = request.getParameter("cameraman");
		String stylists = request.getParameter("stylists");
		String photTopicDesc = request.getParameter("photTopicDesc");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (StringUtils.isNotEmpty(id)) {
				PhototTopic topic = photoWorksService.getPhotoTopicInfo(Long
						.parseLong(id));
				topic.setTitle(title);
				topic.setShotAddress(shotAddress);
				topic.setShotTime(sdf.parse(shotTime));
				topic.setCameraman(cameraman);
				topic.setStylists(stylists);
				topic.setPhotTopicDesc(photTopicDesc);
				photoWorksService.update(topic);
			}

			errorMessage = "1";
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;

	}

	/**
	 * 作品主题列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotosWorksTopicData() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String code = request.getParameter("code");

		photoWorksService.getPhotoTopicData(page, code);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));

		List<PhototTopic> topicList = page.getRecord();

		List<PhotoTopicDTO> ptList = new ArrayList<PhotoTopicDTO>();

		for (Iterator it = topicList.iterator(); it.hasNext();) {
			PhototTopic worktopic = (PhototTopic) it.next();
			PhotoTopicDTO dto = new PhotoTopicDTO();
			dto.setName(worktopic.getTitle());
			dto.setId(worktopic.getId());
			dto.setCameraman(worktopic.getCameraman());
			dto.setStylists(worktopic.getStylists());
			dto.setPhotTopicDesc(worktopic.getPhotTopicDesc());
			dto.setShotTime(worktopic.getShotTime());
			dto.setShotAddress(worktopic.getShotAddress());
			ptList.add(dto);

		}

		JSONArray rows = JSONArray.fromObject(ptList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		System.out.println(jsonObject.toString());
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 删除摄影作品主题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String photoworkstopic_delete() throws Exception {

		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {

			for (int i = 0; i < ids.length; i++) {
				PhototTopic topic = photoWorksService.getPhotoTopicInfo(Long
						.parseLong(ids[i]));
				int photosize =	photoWorksService.getTopicPhotosById(topic.getId()).size();
				if (photosize > 0) {
					errorMessage = "5555";
				} else {
					photoWorksService.delete(topic);
					errorMessage = "0000";
				}

			}

		} catch (Exception e) {

			errorMessage = e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}

	public List<PhtotoGraphyWroks> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<PhtotoGraphyWroks> photoList) {
		this.photoList = photoList;
	}

	
	
}
