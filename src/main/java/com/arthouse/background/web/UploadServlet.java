package com.arthouse.background.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.arthouse.background.service.CustomerService;
import com.arthouse.background.service.PhotoWorksService;
import com.arthouse.background.service.StudioService;
import com.arthouse.common.domain.Customer;
import com.arthouse.common.domain.CustomerPhotos;
import com.arthouse.common.domain.EmployWorksTopic;
import com.arthouse.common.domain.EmployeePhotos;
import com.arthouse.common.domain.PhototTopic;
import com.arthouse.common.domain.PhtotoGraphyWroks;
import com.arthouse.common.domain.StudioInfo;
import com.arthouse.common.domain.StudioPhoto;
import com.arthouse.common.util.ImageScale;

public class UploadServlet extends HttpServlet {

	static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"spring.*.xml");

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	Date date = new Date();
	String picUrlData = sdf.format(date);
	String image_url = "";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("getfile") != null
				&& !request.getParameter("getfile").isEmpty()) {

			// request.getServletContext()必须javaEE6和tomcat7
			String path1 = request.getSession().getServletContext()
					.getRealPath("/");
			String path2 = image_url + "/" + picUrlData + "/";
			File file = new File(path1 + path2, request.getParameter("getfile"));
			if (file.exists()) {
				int bytes = 0;
				ServletOutputStream op = response.getOutputStream();

				response.setContentType(getMimeType(file));
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "inline; filename=\""
						+ file.getName() + "\"");

				byte[] bbuf = new byte[1024];
				DataInputStream in = new DataInputStream(new FileInputStream(
						file));

				while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
					op.write(bbuf, 0, bytes);
				}

				in.close();
				op.flush();
				op.close();
			}
		} else if (request.getParameter("delfile") != null
				&& !request.getParameter("delfile").isEmpty()) {
			File file = new File(request.getSession().getServletContext()
					.getRealPath("/")
					+ image_url + "/" + request.getParameter("delfile"));
			if (file.exists()) {
				file.delete(); // TODO:check and report success
			}
		} else if (request.getParameter("getthumb") != null
				&& !request.getParameter("getthumb").isEmpty()) {
			String path1 = request.getSession().getServletContext()
					.getRealPath("/");
			String path2 = image_url + "/" + picUrlData + "/";
			File file = new File(path1 + path2,
					request.getParameter("getthumb"));
			if (file.exists()) {
				System.out.println(file.getAbsolutePath());
				String mimetype = getMimeType(file);
				if (mimetype.endsWith("png") || mimetype.endsWith("jpeg")
						|| mimetype.endsWith("jpg") || mimetype.endsWith("gif")) {
					BufferedImage im = ImageIO.read(file);
					if (im != null) {
						BufferedImage thumb = Scalr.resize(im, 75);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						if (mimetype.endsWith("png")) {
							ImageIO.write(thumb, "PNG", os);
							response.setContentType("image/png");
						} else if (mimetype.endsWith("jpeg")) {
							ImageIO.write(thumb, "jpg", os);
							response.setContentType("image/jpeg");
						} else if (mimetype.endsWith("jpg")) {
							ImageIO.write(thumb, "jpg", os);
							response.setContentType("image/jpeg");
						} else {
							ImageIO.write(thumb, "GIF", os);
							response.setContentType("image/gif");
						}
						ServletOutputStream srvos = response.getOutputStream();
						response.setContentLength(os.size());
						response.setHeader("Content-Disposition",
								"inline; filename=\"" + file.getName() + "\"");
						os.writeTo(srvos);
						srvos.flush();
						srvos.close();
					}
				}
			} // TODO: check and report success
		} else {
			PrintWriter writer = response.getWriter();
			writer.write("call POST with multipart form data");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PhotoWorksService potoworkserver = (PhotoWorksService) ac
				.getBean("photoWorksService");

		StudioService studioservice = (StudioService) ac
				.getBean("studioService");
		CustomerService customerService = (CustomerService) ac
				.getBean("customerService");

		String code = "";
		String rename = "";
		String id = "";

		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException(
					"Request is not multipart, please 'multipart/form-data' enctype for your form.");
		}
		ServletFileUpload uploadHandler = new ServletFileUpload(
				new DiskFileItemFactory());
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		JSONArray json = new JSONArray();

		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					
					

					// 工作室图片上传
					if (code.startsWith("07", 0)) {

						image_url = "stduio_image";

						System.out.println("工作室图片*******" + code);

						String path1 = request.getSession().getServletContext()
								.getRealPath("/");
						String path2 = image_url + "/" + picUrlData + "/";
						rename = potoworkserver.getPhotoRandom();
						String fileName = item.getName();
						int index = fileName.indexOf(".", 0);
						rename = rename + fileName.substring(index);

						File file = new File(path1 + path2, item.getName());
						File file2 = new File(path1 + path2);
						File targetFile = new File(path1 + path2 + rename);
						file.renameTo(targetFile);
						if (!file2.exists()) {
							file2.mkdirs();
						}
						item.write(targetFile);

						StudioInfo studioInfo = studioservice.getStudioInfo();
						StudioPhoto studioPhoto = new StudioPhoto();
						studioPhoto.setStudioInfo(studioInfo);
						studioPhoto.setPicName(rename);
						studioPhoto.setUrl(path2 + rename);
						studioPhoto.setUploadTime(new Date());
						studioservice.saveStudio_photo(studioPhoto);

					} else {
						// 作品赏析和服饰赏析图片上传

						image_url = "imgs";

						String path1 = request.getSession().getServletContext()
								.getRealPath("/");
						String path2 = image_url + "/" + picUrlData + "/";
						String path3 = "imgs_s" + "/" + picUrlData + "/";

						rename = potoworkserver.getPhotoRandom();
						String fileName = item.getName();
						int index = fileName.indexOf(".", 0);
						rename = rename + fileName.substring(index);

						File file = new File(path1 + path2, item.getName());
						File file2 = new File(path1 + path2);
						File targetFile = new File(path1 + path2 + rename);
						file.renameTo(targetFile);
						if (!file2.exists()) {
							file2.mkdirs();
						}
						item.write(targetFile);

						String suluePath = path1 + path3 + rename;
						File file3 = new File(path1 + path3);
						if (!file3.exists()) {

							file3.mkdirs();
						}

						// 01摄影作品;
						if (code.startsWith("01", 0)) {
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 600, 600);
							PhtotoGraphyWroks pw = new PhtotoGraphyWroks();
							pw.setPhotoName(rename);
							pw.setPhotoType(code);
							pw.setPhotoUrl(path2 + rename);
							pw.setUploadTime(new Date());
							pw.setBreviaryName(rename);
							pw.setBreviaryUploadTime(new Date());
							pw.setBreviaryUrl(path3 + rename);
							pw.setReleaseStatus("0");
							
							PhototTopic topic = potoworkserver.getPhotoTopicInfo(Long
									.parseLong(id));
							pw.setPhototdesc(topic);
							potoworkserver.savePhotoWorksInfo(pw);
							// 02服饰赏析
						} else if (code.startsWith("02", 0)) {
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 600, 600);
							PhtotoGraphyWroks pw = new PhtotoGraphyWroks();
							pw.setPhotoName(rename);
							pw.setPhotoType(code);
							pw.setPhotoUrl(path2 + rename);
							pw.setUploadTime(new Date());
							pw.setReleaseStatus("0");
							pw.setBreviaryName(rename);
							pw.setBreviaryUploadTime(new Date());
							pw.setBreviaryUrl(path3 + rename);
							PhototTopic topic = potoworkserver.getPhotoTopicInfo(Long
									.parseLong(id));
							pw.setPhototdesc(topic);
							potoworkserver.savePhotoWorksInfo(pw);

							// 热门景点
						}else if (code.startsWith("09", 0)) {
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 600, 600);
							PhtotoGraphyWroks pw = new PhtotoGraphyWroks();
							pw.setPhotoName(rename);
							pw.setPhotoType(code);
							pw.setPhotoUrl(path2 + rename);
							pw.setUploadTime(new Date());
							pw.setReleaseStatus("0");
							pw.setBreviaryName(rename);
							pw.setBreviaryUploadTime(new Date());
							pw.setBreviaryUrl(path3 + rename);
							PhototTopic topic = potoworkserver.getPhotoTopicInfo(Long
									.parseLong(id));
							pw.setPhototdesc(topic);
							potoworkserver.savePhotoWorksInfo(pw);
							
						}  
						//客片
						else if(code.startsWith("11", 0)){
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 500, 600);
							CustomerPhotos cphotos = new CustomerPhotos();
							cphotos.setUrl(path2 + rename);
							cphotos.setUploadTime(new Date());
							cphotos.setSl_url(path3 + rename);
							Customer customer =	customerService.getCustomer(Long.parseLong(id));
							cphotos.setCustomer(customer);
							customerService.save(cphotos);
						}
						// 05幻灯片
						else if (code.startsWith("05", 0)) {
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 1680, 600);
							PhtotoGraphyWroks pw = new PhtotoGraphyWroks();
							pw.setPhotoName(rename);
							pw.setPhotoType(code);
							pw.setPhotoUrl(path2 + rename);
							pw.setUploadTime(new Date());
							pw.setReleaseStatus("0");
							pw.setBreviaryName(rename);
							pw.setBreviaryUploadTime(new Date());
							pw.setBreviaryUrl(path3 + rename);
							potoworkserver.savePhotoWorksInfo(pw);
							
							//employee photos
						}else if(code.startsWith("08", 0)){
							
							ImageScale.saveImageAsJpg(targetFile.getPath(),
									suluePath, 600, 600);
							EmployeePhotos  ep = new EmployeePhotos();
							ep.setName(rename);
							ep.setSuolue_url(path3 + rename);
							ep.setUploadTime(new Date());
							ep.setUrl(path2 + rename);
							EmployWorksTopic topic = studioservice.getPhotoTopicInfo(Long.parseLong(id));
							ep.setEmployworkstopic(topic);
							studioservice.save_employeePhotos(ep);
						}
					}

					JSONObject jsono = new JSONObject();
					jsono.put("name", rename);
					jsono.put("size", item.getSize());
					jsono.put("url", "UploadServlet?getfile=" + rename);
					jsono.put("thumbnail_url", "UploadServlet?getthumb="
							+ rename);
					jsono.put("delete_url", "UploadServlet?delfile=" + rename);
					jsono.put("delete_type", "GET");
					json.put(jsono);
					System.out.println(json.toString());
				} else {

					if (item.getFieldName().equals("code")) {

						code = item.getString("utf-8");
					}
					
					if (item.getFieldName().equals("id")) {

						id = item.getString("utf-8");
					}
					

				}
			}
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			writer.write(json.toString());
			writer.close();
		}

	}

	private String getMimeType(File file) {
		String mimetype = "";
		if (file.exists()) {
			if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
				mimetype = "image/png";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
				mimetype = "image/jpg";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
				mimetype = "image/jpeg";
			} else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
				mimetype = "image/gif";
			} else {
				javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
				mimetype = mtMap.getContentType(file);
			}
		}
		return mimetype;
	}

	private String getSuffix(String filename) {
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if (pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		return suffix;
	}

}
