package com.arthouse.background.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.PhotoWorksService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.dto.PhotoWorksDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 幻灯片导航区
 * @author wanggq
 *
 */
public class SlideShowAction extends BaseAction {
	
	private PhotoWorksService photoWorksService;
	
	
	public String slideShow_init(){
	
		String code = request.getParameter("code");
		request.setAttribute("code", code);
		
		return SUCCESS;
	}
	
	/**
	 * 获取导航幻灯片信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotoWorksDataList() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String code = request.getParameter("code");

		photoWorksService.getPhotoWorksDataByCode(page, code);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<PhtotoGraphyWroks> photoworkslist = page.getRecord();
		List<PhotoWorksDTO> photodtoList = new ArrayList<PhotoWorksDTO>();

		for (PhtotoGraphyWroks photowork : photoworkslist) {
			PhotoWorksDTO dto = new PhotoWorksDTO();
			dto.setId(String.valueOf(photowork.getId()));
			dto.setPhotoName(photowork.getPhotoName());
			dto.setSuolueurl(photowork.getBreviaryUrl());
			String photoType = photowork.getPhotoType();
			if(StringUtils.isNotEmpty(photoType)&&photoType.equals("06")){
				dto.setPhotoType("幻灯片");
			}
			
			dto.setUploadTime(photowork.getUploadTime());
			String releaseStatus = photowork.getReleaseStatus();
			if(StringUtils.isNotEmpty(releaseStatus)&&releaseStatus.equals("0")){
				releaseStatus = "未发布";
			}else{
				
				releaseStatus = "已发布";
			}
			dto.setReleaseStatus(releaseStatus);

			photodtoList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(photodtoList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;

	}
	
	
	/**
	 * 删除摄影作品
	 * @return
	 * @throws Exception
	 */
	public String slideShow_delete()throws Exception{
		
		String id  =  request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";
		
		String path = request.getSession().getServletContext().getRealPath("/");
		
		try {
			
			List photoList = photoWorksService.getPhotoWorksList(ids);
			for(int i=0;i<photoList.size();i++){
				Object[] object =(Object[])photoList.get(i);
				String photourl = (String)object[0];
				String suolueurl = (String)object[1];
				File file = new File(path+photourl);
				if(file.exists()){
					file.delete();
				}
				
			}
			photoWorksService.PhotoWorksDelete(ids);
			errorMessage = "001";
			
			
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
	 * 
	 * 将摄影作品置为发布状态
	 * @return
	 * @throws Exception
	 */
	public String slideshow_release()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		
		String errorMessage = "";
		
		try {
			for (int i = 0; i < ids.length; i++) {

				PhtotoGraphyWroks photoworks = photoWorksService
						.getPhotoWroks(Long.parseLong(ids[i]));
				photoworks.setReleaseStatus("1");
				photoWorksService.updatePhotoWorks(photoworks);
				errorMessage = "001";
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

	public PhotoWorksService getPhotoWorksService() {
		return photoWorksService;
	}

	public void setPhotoWorksService(PhotoWorksService photoWorksService) {
		this.photoWorksService = photoWorksService;
	}

	

}
