package com.arthouse.background.web;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.arthouse.background.service.PhotoTypeService;
import com.arthouse.base.web.BaseAction;
import com.arthouse.common.domain.PhotoType;
import com.arthouse.common.dto.PhotoTypeDTO;

/**
 * 
 * 
 * @author wanggq
 * 
 * 
 *         作品类型管理
 * 
 */
public class PhotoTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PhotoTypeService photoTyperService;

	public PhotoTypeService getPhotoTyperService() {
		return photoTyperService;
	}

	public void setPhotoTyperService(PhotoTypeService photoTyperService) {
		this.photoTyperService = photoTyperService;
	}

	/**
	 * 初始化页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String photoType_init() throws Exception {

		return SUCCESS;
	}

	/**
	 * 增加摄影作品类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String photoType_add() throws Exception {

		String code = request.getParameter("code");

		String name = request.getParameter("name");

		String errorMessage = "";
		
		if (StringUtils.isNotEmpty(code)) {

			try {
				if (photoTyperService.isExistPhotoType(code)) {
					errorMessage = "2";
				} else if (code.length() == 1 || code.length() > 6) {

					errorMessage = "3";

				} else {
					photoTyperService.savePhotoType(code, name);
					errorMessage = "1";
				}

			} catch (Exception e) {
				errorMessage = e.getMessage();
				e.printStackTrace();
			}

		}

		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 更新作品类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String photoType_update() throws Exception {

		String code = request.getParameter("code");

		String name = request.getParameter("name");

		String errorMessage = "1";
		try {

			PhotoType photoType = photoTyperService.getPhotoType(code);
			photoType.setName(name);
			photoTyperService.updatePhotoType(photoType);

		} catch (Exception e) {
			errorMessage = e.getMessage();
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 删除类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String photoType_delete() throws Exception {

		String code = request.getParameter("id");
		String errorMessage = "";
		try {
			List childrenNodelist = photoTyperService.getAllChildrenNode(code);
			if (childrenNodelist.size() > 1) {
				errorMessage = "0001";
			} else if (photoTyperService.isExistPhtotoGraphyWroks(code)) {
				errorMessage = "0002";
			} else {
				PhotoType photoType = photoTyperService.getPhotoType(code);
				photoTyperService.deletePhotoType(photoType);
				errorMessage = "0000";
			}

		} catch (Exception ex) {
			errorMessage = "删除失败" + ex.getMessage();
			ex.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(errorMessage);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 加载类型列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String photoTypeData_query() throws Exception {
		// 类型节点编码
		String code = request.getParameter("id");

		List<PhotoTypeDTO> list = photoTyperService.getAllChildrenNode(code);

		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存

		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONArray rows = JSONArray.fromObject(list);
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("rows", rows);
		out.print(jsonObject.toString());
		System.out.println("-------" + jsonObject);

		out.flush();
		out.close();

		return null;
	}

	/**
	 * 获取摄影作品类型树
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getPhotoTypeTreeData() throws Exception {
		// 必须加上,防止前端从JSON中取出的数据成乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=utf-8"); // 输出文本为application
		response.setHeader("Cache-Control", "no-cache"); // 取消浏览器缓存
		String treeData = "";
		// 取得所有父节点
		List<PhotoType> photoTypeParentList = photoTyperService
				.getParentPhotoType();
		if (photoTypeParentList.size() > 0) {
			for (PhotoType photoType : photoTypeParentList) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", photoType.getCode());
				jsonObject.put("text", photoType.getName());
				// 获取子节点
				List<PhotoTypeDTO> children = photoTyperService
						.getPhotoChildrenTypeByCode(photoType.getCode());
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

}
