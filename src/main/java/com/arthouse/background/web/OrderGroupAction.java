package com.arthouse.background.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.arthouse.background.service.OrderGroupService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.GroupItemsInfo;
import com.arthouse.common.domain.GroupOrder;
import com.arthouse.common.domain.News;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.page.DateJsonValueProcessor;
import com.arthouse.common.page.Page;

/**
 * 团购管理
 * 
 * @author wanggq
 * 
 */
public class OrderGroupAction extends BaseAction {

	private OrderGroupService orderService;
	
	GroupItemsInfo groupItem;
	
	private String file_uploadFileName;//Struts2拦截器获得的文件名  
	
	private File file_upload;

	public OrderGroupService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderGroupService orderService) {
		this.orderService = orderService;
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

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	public String orderGroup_init() {

		String code = request.getParameter("code");
		request.setAttribute("code", code);

		return SUCCESS;
	}
	
	
	/**
	 * 初始化编辑页面
	 * 
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String orderGroupEdit_init() throws NumberFormatException, Exception {

		String id = request.getParameter("id");
		request.setAttribute("id", id);
		if(StringUtils.isNotEmpty(id)){
			
			 groupItem = orderService.getGroupItem(Long
					.parseLong(id));
		}
		

		return SUCCESS;
	}
	

	/**
	 * 订单管理初始化页面
	 * 
	 * @return
	 */
	public String orderManager_init() {

		return SUCCESS;

	}

	/**
	 * 查询团购订单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getGroupOrderData() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		String name = request.getParameter("name");
		String orderNo = request.getParameter("orderNo");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");

		orderService.getGroupOrderData(page, name, orderNo, beginTime, endTime);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<GroupOrder> groupOrderlist = page.getRecord();

		JSONArray rows = JSONArray.fromObject(groupOrderlist, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;
	}

	/**
	 * 显示团购信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showGroupItemInfo() throws Exception {

		int currentPage = Integer.parseInt(request.getParameter("page"));// 当前页
		int currentRows = Integer.parseInt(request.getParameter("rows"));// 每页显示记录数
		Page page = new Page(currentRows, currentPage, true);

		orderService.getGroupItemData(page);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

		List<GroupItemsInfo> groupItemlist = page.getRecord();

		JSONArray rows = JSONArray.fromObject(groupItemlist, cfg);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", page.getTotalRecordCount());
		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
		return null;

	}
	
	/**
	 * 上传团购列表缩略图
	 * 
	 * @return
	 * @throws Exception
	 */
	public String  groupitemsuoluepic_upload() throws Exception {

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

				GroupItemsInfo groupItem = orderService.getGroupItem(Long
							.parseLong(id));
				groupItem.setUrl("suolue_pic/" + picUrlData+"/"+ name + extName);
				orderService.update(groupItem);
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
	 * 新增团购信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public String groupitemsInfo_add() throws IOException, ParseException {
		String errorMessage = "";
		GroupItemsInfo groupItem = new GroupItemsInfo();
		String title = request.getParameter("title");
		String descr = request.getParameter("descr");
		String price = request.getParameter("price");
		String old_price = request.getParameter("old_price");
		String content = request.getParameter("content");

		groupItem.setTitle(title);
		groupItem.setDescr(descr);
		groupItem.setPrice(Double.valueOf(price));
		groupItem.setOld_price(Double.valueOf(old_price));
		groupItem.setContent(content);

		try {
			orderService.save(groupItem);
			request.setAttribute("msg", 1000);
		} catch (Exception e) {
			// TODO: handle exception
			errorMessage = e.getMessage();
		}

		return SUCCESS;

	}

	/**
	 * 编辑团购信息
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String groupitemsInfo_edit() throws NumberFormatException,
			Exception {

		String errorMessage = "";
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String descr = request.getParameter("descr");
		String price = request.getParameter("price");
		String old_price = request.getParameter("old_price");
		String content = request.getParameter("content");


		try {
			if (StringUtils.isNotEmpty(id)) {
				GroupItemsInfo groupItem = orderService.getGroupItem(Long
						.parseLong(id));
				groupItem.setTitle(title);
				groupItem.setDescr(descr);
				groupItem.setPrice(Double.valueOf(price));
				groupItem.setOld_price(Double.valueOf(old_price));
				groupItem.setContent(content);
				orderService.update(groupItem);
				request.setAttribute("msg", 1000);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;

	}

	/**
	 * 删除团购列表信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String groupItems_delete() throws Exception {

		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String errorMessage = "";
		try {
			for (int i = 0; i < ids.length; i++) {
				GroupItemsInfo groupItem = orderService.getGroupItem(Long
						.parseLong(ids[i]));
				orderService.delete(groupItem);
				errorMessage = "0000";
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

	public GroupItemsInfo getGroupItem() {
		return groupItem;
	}

	public void setGroupItem(GroupItemsInfo groupItem) {
		this.groupItem = groupItem;
	}
	
	

}
