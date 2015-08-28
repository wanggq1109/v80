package com.arthouse.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ================================================================== 
 * 共同方法类
 * ==================================================================
 * 
 * @author Dragon
 * 
 */
public class Common {

	public static final Logger logger = LoggerFactory.getLogger(Common.class);

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 字符串转为int
	 * 
	 * @param str
	 * @return
	 */
	public static int StringtoInt(Object str) {

		if (str == null) {
			return 0;
		}

		int retInt = 0;
		try {
			if (isNumeric(str.toString()) == false) {
				retInt = 0;
			} else {
				retInt = Integer.parseInt(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return retInt;
	}

	/**
	 * 获取当前日期yyyyMMdd
	 * 
	 * @return
	 */
	public static String getNowData(String formatType) {
		Date date = new Date();
		java.text.DateFormat df = new java.text.SimpleDateFormat(formatType);
		return df.format(date);
	}

	/**
	 * obj ==> bigDecimal
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal stringtoBigDecimal(Object obj) throws Exception {
		try {
			if (obj == null) {
				return new BigDecimal("0");
			}
			BigDecimal bd = new BigDecimal(obj.toString());
			// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			return bd;
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal("0");
		}

	}

	/**
	 * 解决乱码问题
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String codeToString(String str) {
		String retStr = "";
		if (str != null && str.length() > 0) {
			try {
				retStr = new String(str.getBytes("ISO8859-1"), "GB2312");
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return retStr;
	}

	public static Properties configProperties = null;

	public static Object configLocker = new Object();

	/**
	 * 读取配置文件信息
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getPropertiesValue(String key) {

		synchronized (configLocker) {
			if (configProperties == null) {
				configProperties = new Properties();
				InputStream configStream = null;
				try {
					configStream = Common.class.getClassLoader()
							.getResourceAsStream(Constant.CONFIG_FILE);
					if (configStream == null) {

						logger.info("找不到配置文件:" + Constant.CONFIG_FILE);

					}
					configProperties.load(configStream);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (configStream != null) {
						try {
							configStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}

		String retStr = "";

		retStr = (String) configProperties.get(key);

		return retStr;

	}
	
	/**
	 * 
	 * String[] ==> Set
	 * 
	 * @param strArr
	 * @return
	 */
	public static Set<String> StrArrToSetArr(String[] strArr){
		
		Set<String> retSet = new HashSet<String>();
		
		for(int i=0;i<strArr.length;i++){
			retSet.add(strArr[i]);
		}
		
		return retSet;
		
	}
}
