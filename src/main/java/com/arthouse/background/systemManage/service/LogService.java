package com.arthouse.background.systemManage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.arthouse.common.domain.SystemLog;
import com.arthouse.common.page.Page;

public interface LogService {
	public List<SystemLog> getSystemLogList(HttpServletRequest request, Page page);
	public void logdel(HttpServletRequest request) throws Exception;
}
