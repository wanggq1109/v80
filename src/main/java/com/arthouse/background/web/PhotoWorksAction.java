package com.arthouse.background.web;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.background.service.PhotoWorksService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.dto.PhotoTypeDTO;
import com.arthouse.common.dto.PhotoWorksDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 
 * 
 * 
 * @author wgq
 * 
 * 
 *         摄影作品
 * 
 */
public class PhotoWorksAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private PhotoTypeService photoTyperService;

	private PhotoWorksService photoWorksService;
	
	private File file_upload;
	
	private String file_uploadFileName;//Struts2拦截器获得的文件名  
	
	

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
	
	
	

	public File getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(File file_upload) {
		this.file_upload = file_upload;
	}
	
	
	
	


	public String getFile_uploadFileName() {
		return file_uploadFileName;
	}

	public void setFile_uploadFileName(String file_uploadFileName) {
		this.file_uploadFileName = file_uploadFileName;
	}

	/**
	 * 页面初始化
	 * 
	 * @return
	 */
	public String photoworks_Init() {
		
		String code = request.getParameter("code");
		request.setAttribute("code", code);

		return SUCCESS;
	}

	/**
	 * 初始化摄影作品相关类型树
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotoWorksTreeData() throws Exception {

		String code = request.getParameter("code");
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		String treeData = "";

		// 取得所有父节点
		List<PhotoType> photoTypeParentList = photoTyperService
				.getParentPhotoType4Lever(code);

		if (photoTypeParentList.size() > 0) {
			for (PhotoType photoType : photoTypeParentList) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", photoType.getCode());
				jsonObject.put("text", photoType.getName());
				// 获取子节点
				List<PhotoTypeDTO> children = photoTyperService
						.getOtherChildrenNode(photoType.getCode());
				if (children.size() > 0) {
					jsonObject.put("state", "closed");
					jsonObject.put("children", children);
				}
				treeData += jsonObject.toString() + ",";
			}
		} else {
			treeData = "{\"id\":\"null\",\"text\":\"根目录\",\"children\":[{\"id\":\"null\",\"text\":\"无分类\"}]},";
		}

		String json = "[" + treeData.substring(0, treeData.length() - 1) + "]";
		out.print(json);
		System.out.println("-------" + json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 获取上传作品信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotoWorksDataList() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String id = request.getParameter("id");

		photoWorksService.getPhotoWorksDataByType(page, Long.parseLong(id));

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
			String type = photowork.getPhotoType();
			if(type.equals("0101")&&type.startsWith("01", 0)){
				
				type = "作品赏析-->外景";
				
			}else if(type.equals("0102")&&type.startsWith("01", 0)){
				
				type = "作品赏析-->内景";
				
			}else if(type.equals("0103")&&type.startsWith("01", 0)){
				
				type = "作品赏析-->古典";
				
			}else if(type.equals("0104")&&type.startsWith("01", 0)){
				
				type = "作品赏析-->现代";
			}else if(type.equals("0201")&&type.startsWith("02", 0)){
				
				type = "礼服赏析-->传统";
				
			}else if(type.equals("0202")&&type.startsWith("02", 0)){
				
				type = "礼服赏析-->现代";
			}
			else if(type.startsWith("09", 0)){
				
				type = "热门景点";
			}
			dto.setPhotoType(type);
			dto.setUploadTime(photowork.getUploadTime());
			dto.setBreviaryName(photowork.getBreviaryUrl());
			dto.setBreviaryUploadTime(photowork.getBreviaryUploadTime());
			String releaseStatus = photowork.getReleaseStatus();
			if(StringUtils.isNotEmpty(releaseStatus)&&releaseStatus.equals("0")){
				releaseStatus = "未发布";
			}else{
				
				releaseStatus = "已发布";
			}
			dto.setReleaseStatus(releaseStatus);
			
			String weekrecommend = photowork.getWeekWorkRecommend();
			if(StringUtils.isEmpty(weekrecommend)){
				
				weekrecommend = "未推荐";
				
			}else {
				
				weekrecommend = "已推荐";
				
			}
			dto.setWeekrecommend(weekrecommend);
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
	 * 上传缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String Photoworkssuoluepic_upload() throws Exception {

		String errorMessage = "";
		String extName = "";//扩展名
		String id = request.getParameter("id");
		


		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String picUrlData = sdf.format(date);

		try {
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "suolue_pic\\" + picUrlData;
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();

			}
			System.out.println("文件名**************" + file_uploadFileName);
			String name = new SimpleDateFormat("yyyymmddHHmmss")
					.format(new Date());//当前时间
			//扩展名格式：
			if (file_uploadFileName.lastIndexOf(".") >= 0) {
				extName = file_uploadFileName.substring(file_uploadFileName
						.lastIndexOf("."));
			}
			file_upload.renameTo(new File(savePath +"/"+ name + extName));
			if (StringUtils.isNotEmpty(id)) {

			PhtotoGraphyWroks photoworks = photoWorksService.getPhotoWroks(Long
					.parseLong(id));
			photoworks.setBreviaryName(name + extName);
			photoworks.setBreviaryUploadTime(new Date());
			photoworks.setBreviaryUrl("suolue_pic\\" + picUrlData+"/"+ name + extName);
			photoWorksService.updatePhotoWorks(photoworks);

		}
			errorMessage  = "0000";
			
		} catch (Exception e) {
			
			  errorMessage = "上传失败"+e.getMessage();
		}
		

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();

		return null;
	}
	
	/**
	 * 删除摄影作品
	 * @return
	 * @throws Exception
	 */
	public String photoWorks_delete()throws Exception{
		
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
				File file2 = new File(path+suolueurl);
				if(file.exists()){
					file.delete();
				}
				if(file2.exists()){
					
					file2.delete();
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
	public String photoWorks_release()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		
		String errorMessage = "";
		
		try {
			for (int i = 0; i < ids.length; i++) {

				PhtotoGraphyWroks photoworks = photoWorksService
						.getPhotoWroks(Long.parseLong(ids[i]));
				photoworks.setReleaseStatus("1");
				photoWorksService.updatePhotoWorks(photoworks);
			}
			errorMessage = "001";
		} catch (Exception e) {
			
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
	 * 将摄影作品置为一周推荐
	 * @return
	 * @throws Exception
	 */
	public String photoWorks_weekrecommend()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		
		String errorMessage = "";
		
		try {
			for (int i = 0; i < ids.length; i++) {

				PhtotoGraphyWroks photoworks = photoWorksService
						.getPhotoWroks(Long.parseLong(ids[i]));
				photoworks.setWeekWorkRecommend("1");
				photoWorksService.updatePhotoWorks(photoworks);
			}
			errorMessage = "001";
		} catch (Exception e) {
			
			errorMessage = e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 整体发布作品
	 * @return
	 * @throws Exception
	 */
	public String Photoworks_all_release()throws Exception{
		
		String errorMessage = "";
		String code = request.getParameter("code");
		
		try {
			List<PhtotoGraphyWroks> photoworksList = photoWorksService
					.getPhtotoGraphyWroksByCode(code);
			for (PhtotoGraphyWroks photoworks : photoworksList) {

				photoworks.setReleaseStatus("1");
				photoWorksService.updatePhotoWorks(photoworks);

			}
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
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String photoworksedit_facephots() throws NumberFormatException, Exception{
		
		String id = request.getParameter("id");
		String errorMessage = "";
		
		PhtotoGraphyWroks photoworks = photoWorksService
				.getPhotoWroks(Long.parseLong(id));
		
		try {
			photoWorksService.updatefacephotos(photoworks);
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
	
	

}
