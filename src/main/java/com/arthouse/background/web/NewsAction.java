package com.arthouse.background.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.service.NewsInfoService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.VideoInfo;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 新闻信息管理
 * 
 * @author wgq
 * 
 */
public class NewsAction extends BaseAction {

	private NewsInfoService newsInfoservice;

	private News mynewsinfo;

	private String myid;
	
	private String file_uploadFileName;//Struts2拦截器获得的文件名  
	
	private File file_upload;


	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	public News getMynewsinfo() {
		return mynewsinfo;
	}

	public void setMynewsinfo(News mynewsinfo) {
		this.mynewsinfo = mynewsinfo;
	}

	public NewsInfoService getNewsInfoservice() {
		return newsInfoservice;
	}

	public void setNewsInfoservice(NewsInfoService newsInfoservice) {
		this.newsInfoservice = newsInfoservice;
	}

	public String news_init() {

		return SUCCESS;
	}

	public String news_add() {

		return SUCCESS;
	}

	public String news_save() throws IOException {

		String errorMessage = "";

		String title = request.getParameter("name");
		String author = request.getParameter("author");
		String source = request.getParameter("resource");
		String content = request.getParameter("editor1");
		String descr = request.getParameter("descr");

		News news = new News();
		news.setTitle(title);
		news.setAuthor(author);
		news.setSource(source);
		news.setReleaseTime(new Date());
		news.setContent(content);
		news.setDescr(descr);

		try {
			newsInfoservice.newsInfo_save(news);
			request.setAttribute("msg", "0000");
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}
		return SUCCESS;
	}

	/**
	 * 保存编辑内容
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public String newsinfo_edit_save() throws NumberFormatException, Exception {

		String id = request.getParameter("id");
		String title = request.getParameter("name");
		String author = request.getParameter("author");
		String source = request.getParameter("resource");
		String content = request.getParameter("editor1");
		String descr = request.getParameter("descr");

		News newsinfo = newsInfoservice.getnewInfo(Long.parseLong(id));
		newsinfo.setTitle(title);
		newsinfo.setAuthor(author);
		newsinfo.setReleaseTime(new Date());
		newsinfo.setContent(content);
		newsinfo.setSource(source);
		newsinfo.setDescr(descr);

		try {
			newsInfoservice.update(newsinfo);
			request.setAttribute("msg", 1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String getnewsInfo() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(10, currentPage, true);

		newsInfoservice.getnewsInfo(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<News> newsList = page.getRecord();

		JSONArray rows = JSONArray.fromObject(newsList, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;

	}

	public String newInfo_edit() throws Exception {
		String id = request.getParameter("id");
		mynewsinfo = newsInfoservice.getnewInfo(Long.parseLong(id));
		request.setAttribute("id", id);

		return SUCCESS;
	}

	/**
	 * 删除新闻
	 * 
	 * @return
	 * @throws Exception
	 */
	public String newsInfo_delete() throws Exception {

		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";

		try {
			newsInfoservice.delete(ids);
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
	 * 
	 * 新闻发布状态
	 * @return
	 * @throws Exception
	 */
	public String news_release()throws Exception{
		
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		
		String errorMessage = "";
		
		try {
			for (int i = 0; i < ids.length; i++) {

				mynewsinfo = newsInfoservice.getnewInfo(Long.parseLong(id));
				mynewsinfo.setStatus("1");
				newsInfoservice.update(mynewsinfo);
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
	
	
	/**
	 * 上传新闻列表缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String  newssuoluepic_upload() throws Exception {

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

				News news = newsInfoservice.getnewInfo(Long.parseLong(id));
				news.setUrl("suolue_pic/" + picUrlData+"/"+ name + extName);
				newsInfoservice.update(news);
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

	public String getFile_uploadFileName() {
		return file_uploadFileName;
	}

	public void setFile_uploadFileName(String file_uploadFileName) {
		this.file_uploadFileName = file_uploadFileName;
	}

	public File getFile_upload() {
		return file_upload;
	}

	public void setFile_upload(File file_upload) {
		this.file_upload = file_upload;
	}
	
	
	

}
