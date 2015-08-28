package com.arthouse.background.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.arthouse.background.service.StudioService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.Employee;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.Recruiment;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.StudioInfofw;
import com.arthouse.common.domain.StudioPhoto;
import com.arthouse.common.dto.EmployeeDTO;
import com.arthouse.common.dto.EmployeePhotosDTO;
import com.arthouse.common.dto.PhotoTopicDTO;
import com.arthouse.common.dto.StudioPhotoDTO;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;
import com.arthouse.goldfish.service.IndexService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 工作室介绍
 * 
 * @author wanggq
 * 
 */
public class StudioIntroAction extends BaseAction {

	private StudioService studioService;
	
	private IndexService indexService;
	

	private File file_upload;

	private String file_uploadFileName;
	

	public StudioService getStudioService() {
		return studioService;
	}

	public void setStudioService(StudioService studioService) {
		this.studioService = studioService;
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
	 * 
	 * @return
	 * @throws Exception
	 */
	public String studio_init() throws Exception {

		String code = request.getParameter("code");

		request.setAttribute("code", code);

		StudioInfo studio2 = studioService.getStudioInfo();

		if (studio2 != null) {

			request.setAttribute("content", studio2.getContent());
		}

		return SUCCESS;
	}

	public String studio_service_init() {

		return SUCCESS;
	}
	
	public String employee_init(){
		
		
		return SUCCESS;
	}
	
	public String zhaopin(){
		
		
		return SUCCESS;
	}
	
	public String phototopic_init(){
		String code = request.getParameter("code");

		request.setAttribute("code", code);
		
		return SUCCESS;
	}
	
	public String show_employeePhoto()throws Exception{
		
		String id = request.getParameter("id");
		if(StringUtils.isNotEmpty(id)){
			
			List<EmployeePhotos> photosList = studioService.getEmployeePhotosById(Long.parseLong(id));
			ActionContext.getContext().put("photosList", photosList);
		}
		
		
		return SUCCESS;
	}

	/**
	 * 新增工作室简介
	 * 
	 * @return
	 * @throws Exception
	 */
	public String studio_add() throws Exception {

		String content = request.getParameter("editor1");

		StudioInfo studio = new StudioInfo();

		studio.setContent(content);

		studioService.save(studio);

		StudioInfo studio2 = studioService.getStudioInfo();

		request.setAttribute("content", studio2.getContent());

		return SUCCESS;
	}

	/**
	 * 工作室图片列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getstudioPhoto() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		studioService.getStudioPhotoData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List studiophotolist = page.getRecord();

		List<StudioPhotoDTO> stList = new ArrayList<StudioPhotoDTO>();

		for (Iterator it = studiophotolist.iterator(); it.hasNext();) {
			StudioPhoto studiophoto = (StudioPhoto) it.next();
			StudioPhotoDTO dto = new StudioPhotoDTO();
			dto.setPicName(studiophoto.getPicName());
			dto.setUploadTime(studiophoto.getUploadTime());
			dto.setId(studiophoto.getId());
			stList.add(dto);

		}

		JSONArray rows = JSONArray.fromObject(stList, cfg);
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
	 * 工作室服务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getstudioDatafw() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		studioService.getStudioInfoData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<StudioInfofw> studioserviceList = page.getRecord();

		JSONArray rows = JSONArray.fromObject(studioserviceList, cfg);
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
	 * 员工信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getEmployeeData() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		studioService.getEmployeeInfoData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<Employee> studioserviceList = page.getRecord();
		List<EmployeeDTO> employeeList = new ArrayList<EmployeeDTO>();
		for(Employee em: studioserviceList ){
			EmployeeDTO dto = new EmployeeDTO();
			dto.setId(em.getId());
			dto.setName(em.getName());
			dto.setIntro(em.getIntro());
			dto.setPhotoUrl(em.getPhotoUrl());
			dto.setPosition(em.getPosition());
			dto.setExperience(em.getExperience());
			
			employeeList.add(dto);
		}

		JSONArray rows = JSONArray.fromObject(employeeList, cfg);
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
	 * 招聘信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRecruimentData() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		studioService.getRecruimentInfoData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<Recruiment> studioserviceList = page.getRecord();

		JSONArray rows = JSONArray.fromObject(studioserviceList, cfg);
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
	 * 作品主题列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPhotoTopicData() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		studioService.getPhotoTopicData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<EmployWorksTopic> topicList = page.getRecord();
		
		List<PhotoTopicDTO> ptList = new ArrayList<PhotoTopicDTO>();

		for (Iterator it = topicList.iterator(); it.hasNext();) {
			EmployWorksTopic worktopic = (EmployWorksTopic) it.next();
			PhotoTopicDTO dto = new PhotoTopicDTO();
			dto.setName(worktopic.getName());
			dto.setId(worktopic.getId());
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
	

	public String studiofw_add() throws IOException {

		String errorMessage = "";

		String title = request.getParameter("title");
		String content = request.getParameter("content");

		StudioInfofw fw = new StudioInfofw();
		fw.setTitle(title);
		fw.setContent(content);

		try {
			studioService.save(fw);
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
	
	
	public String employee_add() throws IOException {

		String errorMessage = "";

		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String intro = request.getParameter("intro");
		String experience = request.getParameter("experience");
		
		Employee employee = new Employee();
		employee.setName(name);
		employee.setIntro(intro);
		employee.setPosition(position);
		employee.setExperience(experience);

		try {
			studioService.save_employee(employee);
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
	 * 招聘管理增加信息
	 * @return
	 * @throws IOException
	 */
	public String recruiment_add() throws IOException {

		String errorMessage = "";

		String jobName = request.getParameter("jobName");
		String sum = request.getParameter("sum");
		String intro = request.getParameter("intro");
		
		Recruiment rm = new Recruiment();
		rm.setJobName(jobName);
		rm.setIntro(intro);
		rm.setSum(Integer.parseInt(sum));

		try {
			studioService.save_recruiment(rm);
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
	 * 新增作品主题
	 * @return
	 * @throws IOException
	 */
	public String photoTopic_add() throws IOException {

		String errorMessage = "";

		String name = request.getParameter("name");
		
		EmployWorksTopic topic = new EmployWorksTopic();
		topic.setName(name);
		topic.setStatus("0");

		try {
			studioService.save_photoTopic(topic);
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
	
	
	public String studiofw_edit() throws NumberFormatException, Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
	
		

		try {
			if(StringUtils.isNotEmpty(id)){
				StudioInfofw fw = studioService.getstudioInfoFw(Long.parseLong(id));
				fw.setTitle(title);
				fw.setContent(content);
				studioService.update(fw);
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
	 * 编辑员工信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String employee_edit() throws NumberFormatException, Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String intro = request.getParameter("intro");
		String experience = request.getParameter("experience");

		try {
			if(StringUtils.isNotEmpty(id)){
				Employee em = studioService.getEmployeeInfo(Long.parseLong(id));
				em.setName(name);
				em.setIntro(intro);
				em.setPosition(position);
				em.setExperience(experience);

				studioService.update_employee(em);
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
	 * 编辑招聘信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String recruiment_edit() throws NumberFormatException, Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String jobName = request.getParameter("jobName");
		String sum = request.getParameter("sum");
		String intro = request.getParameter("intro");

		try {
			if(StringUtils.isNotEmpty(id)){
				Recruiment rm = studioService.getRecruimentInfo(Long.parseLong(id));
				rm.setIntro(intro);
				rm.setJobName(jobName);
				rm.setSum(Integer.parseInt(sum));
				studioService.update_recruiment(rm);
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
	 * 编辑作品主题信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String photoTopic_edit() throws NumberFormatException, Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String name = request.getParameter("name");

		try {
			if(StringUtils.isNotEmpty(id)){
				EmployWorksTopic topic = studioService.getPhotoTopicInfo(Long.parseLong(id));
				topic.setName(name);
				studioService.update_photoTopic(topic);
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
	 * 工作室简介删除
	 * @return
	 * @throws Exception
	 */
	public String studiofw_delete()throws Exception{
		
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			studioService.delete(ids);
			errorMessage = "0000";
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
	
	public String studiophoto_delete()throws Exception{
		
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			studioService.deletePhotostduioPhoto(ids);
			errorMessage = "0000";
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
	 * 删除员工信息
	 * @return
	 * @throws Exception
	 */
	public String employee_delete()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			studioService.delete_employee(ids);
			errorMessage = "0000";
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
	 * 删除招聘职位信息
	 * @return
	 * @throws Exception
	 */
	public String recruiment_delete()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			studioService.delete_recruiment(ids);
			errorMessage = "0000";
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
	 * 删除作品主题
	 * @return
	 * @throws Exception
	 */
	public String phototopic_delete()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			studioService.delete_photoTopic(ids);
			errorMessage = "0000";
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
	 * 上传员工照片
	 * @return
	 * @throws IOException
	 */
	public String employeePhoto_upload() throws IOException{
		
		String id = request.getParameter("id");
		
		String errorMessage = "0000";
		String extName = "";// 扩展名

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String filmUrlData = sdf.format(date);

		try {
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "employee_photo" +"/"+ filmUrlData;

			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();

			}
			String name = new SimpleDateFormat("yyyymmddHHmmss")
					.format(new Date());// 当前时间
			// 扩展名格式：
			if (file_uploadFileName.lastIndexOf(".") >= 0) {
				extName = file_uploadFileName.substring(file_uploadFileName
						.lastIndexOf("."));
			}
			file_upload.renameTo(new File(savePath + "/" + name + extName));
			Employee em = studioService.getEmployeeInfo(Long.parseLong(id));
			em.setPhotoUrl("employee_photo/" + filmUrlData + "/" + name + extName);
			StudioInfo studio  = indexService.getStudioObject();
			em.setStudioInfo(studio);
			studioService.update_employee(em);
			
			errorMessage += name + extName;

		} catch (Exception e) {

			errorMessage = "上传失败" + e.getMessage();
		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();

		return null;
		
		
	}
	
	
	public String toselecttopic_list() throws IOException {
		String errorMessage = "";
		String id = request.getParameter("id");
		try {
			List<EmployeePhotosDTO> topiclist = studioService
					.getEmployeeTopicSelectById(Long.parseLong(id));
			for (int i = 0; i < topiclist.size(); i++) {
				errorMessage += topiclist.get(i).getName() + ","
						+ topiclist.get(i).getId() + ",";
			}

		} catch (Exception ex) {
			errorMessage = "2";
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}
	
	
	public String topicforselected() throws IOException {
		String errorMessage = "";
		String id = request.getParameter("id");
		try {
			List<EmployeePhotosDTO> topiclist = studioService
					.getEmployeeTopicSelectedById(Long.parseLong(id));
			for (int i = 0; i < topiclist.size(); i++) {
				errorMessage += topiclist.get(i).getName() + ","
						+ topiclist.get(i).getId() + ",";
			}

		} catch (Exception ex) {
			errorMessage = "2";
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
    }
	
	public String topicselected_edit() throws IOException {
        String topicId=request.getParameter("topicId");
        String id=request.getParameter("id");
        String errorMessage=" ";
        	String[] topicIdS = topicId.split(",");
        	 try{
             		Employee  employee = studioService.getEmployeeInfo(Long.parseLong(id));
             		List<EmployWorksTopic> topicList =	studioService.getTopicByEmployeeList(Long.parseLong(id));
     				for(EmployWorksTopic topic2 :topicList){
     					topic2.setEmployee(null);
     					topic2.setStatus("0");
             			studioService.update_photoTopic(topic2);
     				}
             		if(topicId!=""){
             			for(int i=0;i<topicIdS.length;i++){
                 			EmployWorksTopic topic = studioService.getPhotoTopicInfo(Long.parseLong(topicIdS[i]));
                         		topic.setEmployee(employee);
                         		topic.setStatus("1");
                         		studioService.update_photoTopic(topic);
                     	}
             		}
             		
             	errorMessage = "1";
             		
              
             }catch (Exception ex){
                 errorMessage = "2";
             }
        
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.write(errorMessage);
        out.flush();
        out.close();
        return  null;
    }

	public IndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}
	
	
	

}
