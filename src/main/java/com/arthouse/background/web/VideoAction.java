package com.arthouse.background.web;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.service.VideoInfoService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 微电影
 * 
 * @author wanggq
 * 
 */
public class VideoAction extends BaseAction {

	private VideoInfoService videoInfoService;

	private File file_upload1;
	
	private File file_upload;

	private String file_upload1FileName;
	
	private String file_uploadFileName;//Struts2拦截器获得的文件名  

	private String url;

	private String intro;

	private VideoInfo videoinfo;

	public VideoInfo getVideoinfo() {
		return videoinfo;
	}

	public void setVideoinfo(VideoInfo videoinfo) {
		this.videoinfo = videoinfo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String video_init() {

		return SUCCESS;
	}

	public String video_add() throws Exception {

		return SUCCESS;
	}

	public String film_edit_init() throws NumberFormatException, Exception {

		String id = request.getParameter("id");
		videoinfo = videoInfoService.getVideoInfoById(Long.parseLong(id));
		request.setAttribute("id", id);
		session.put("videoId", id);

		return SUCCESS;
	}

	public String film_edit_save() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String intro = request.getParameter("intro");
		String name = request.getParameter("name");
		String shootingDate = request.getParameter("shootingDate");
		VideoInfo film = (VideoInfo) session.get("film");
		film.setIntro(intro);
		film.setName(name);
		film.setShootingDate(sdf.parse(shootingDate));
		videoInfoService.updateVideo(film);
		request.setAttribute("msg", "1000");
		session.remove("videoId");
		session.clear();
		return SUCCESS;
	}

	/**
	 * 微电影数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getVideoInfo() throws Exception {
		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String code = request.getParameter("code");

		videoInfoService.getVideoData(page, null);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<VideoInfo> photoworkslist = page.getRecord();

		JSONArray rows = JSONArray.fromObject(photoworkslist, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;
	}

	/**
	 * 点击保存返回当前上传页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String film_save() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("msg", "0000");
		System.out.println("intro--------" + intro);
		String videoType = request.getParameter("videoType");
		String name = request.getParameter("name");
		String shootingDate = request.getParameter("shootingDate");
		VideoInfo film = (VideoInfo) session.get("film");
		film.setIntro(intro);
		film.setType(videoType);
		film.setName(name);
		film.setShootingDate(sdf.parse(shootingDate));
		videoInfoService.saveVideo(film);
		session.remove("film");
		session.clear();

		return SUCCESS;
	}
	
	
	/**
	 * 上传微电影缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String  videosuoluepic_upload() throws Exception {

		String errorMessage = "";
		String extName = "";//扩展名
		String id = request.getParameter("id");
		


		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String picUrlData = sdf.format(date);

		try {
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "suolue_pic/" + picUrlData;
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

				VideoInfo film = videoInfoService.getVideoInfoById(Long.parseLong(id));
				film.setSuolueurl("suolue_pic/" + picUrlData+"/"+ name + extName);
				videoInfoService.updateVideo(film);
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
	 * 上传视频（微电影）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String weifilm_upload() throws Exception {

		// String intro = request.getParameter("intro");

		String errorMessage = "0000";
		String extName = "";// 扩展名
		String id = (String) session.get("videoId");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String filmUrlData = sdf.format(date);

		try {
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "wei_film/" + filmUrlData;

			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();

			}
			System.out.println("文件名**************" + file_upload1FileName);
			String name = new SimpleDateFormat("yyyymmddHHmmss")
					.format(new Date());// 当前时间
			// 扩展名格式：
			if (file_upload1FileName.lastIndexOf(".") >= 0) {
				extName = file_upload1FileName.substring(file_upload1FileName
						.lastIndexOf("."));
			}
			file_upload1.renameTo(new File(savePath + "/" + name + extName));

			if (StringUtils.isNotEmpty(id)) {
				VideoInfo film = videoInfoService.getVideoInfoById(Long.parseLong(id));
				File oldfile = new File(savePath+"/"+film.getName());
				if(oldfile.exists()){
					oldfile.delete();
				}
				film.setStatus("0");
				film.setName(name + extName);
				film.setUploadTime(new Date());
				film.setUrl("wei_film/" + filmUrlData + "/" + name + extName);
				session.put("film", film);
				

			} else {

				VideoInfo film = new VideoInfo();
				film.setName(name + extName);
				film.setStatus("0");
				film.setUploadTime(new Date());
				film.setUrl("wei_film/" + filmUrlData + "/" + name + extName);
				session.put("film", film);
			}

			// errorMessage = "0000";

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
	
	
	/**
	 * 删除video
	 * 
	 * @return
	 * @throws Exception
	 */
	public String videoInfo_delete() throws Exception {

		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		
		String filmUrlData = sdf.format(date);

		String savePath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "wei_film/" + filmUrlData;
		try {
			
			for (String vid : ids) {
				
				VideoInfo film = videoInfoService.getVideoInfoById(Long
						.parseLong(vid));
				String filePath = savePath +"/"+film.getName();
				File file = new File(filePath);
				if (file.exists()) {

					file.delete();
				}

			}

			videoInfoService.delete(ids);
			errorMessage = "0000";
		} catch (Exception e) {

			errorMessage = e.getMessage();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;

	}

	public VideoInfoService getVideoInfoService() {
		return videoInfoService;
	}

	public void setVideoInfoService(VideoInfoService videoInfoService) {
		this.videoInfoService = videoInfoService;
	}

	public File getFile_upload1() {
		return file_upload1;
	}

	public void setFile_upload1(File file_upload1) {
		this.file_upload1 = file_upload1;
	}

	public String getFile_upload1FileName() {
		return file_upload1FileName;
	}

	public void setFile_upload1FileName(String file_upload1FileName) {
		this.file_upload1FileName = file_upload1FileName;
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
	
	

}
