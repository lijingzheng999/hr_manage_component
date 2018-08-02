package com.hr.manage.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 关于字符串的工具类
 * 
 * @author yufeng.che@gmail.com
 * 
 */
public class StringUtil {
	private static Logger logger = Logger.getLogger(StringUtil.class);

	private static String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param length
	 * @param addPoints
	 * @return
	 */
	public static String subString(String str, int length, boolean addPoints) {
		if (StringUtils.isNotBlank(str)) {
			if (str.length() > length) {
				str = str.substring(0, length);
				if (addPoints) {
					str += "...";
				}
				return str;
			}
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 截取字符串 如果字符串超过长度，则增加三个点的...
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String subString(String str, int length) {
		return subString(str, length, true);
	}

	/**
	 * 将 "1,2,4,5,6" 转换成 List<Integer>
	 * 
	 * @param toSpilt
	 * @param separation
	 * @return
	 */
	public static List<Integer> spilt2Int(String toSpilt, String separation) {
		if (toSpilt == null || StringUtils.isBlank(toSpilt)) {
			return Collections.emptyList();
		}

		String[] strArry = toSpilt.split(separation);
		if (strArry != null && strArry.length > 0) {
			List<Integer> retval = new ArrayList<Integer>();
			for (String s : strArry) {
				if (StringUtils.isNotBlank(s) && StringUtils.isNumeric(s)) {
					retval.add(Integer.valueOf(s.trim()));
				}
			}

			return retval;
		}
		return Collections.emptyList();
	}

	public static String addImgTagAltAndTitle(String content, String alt,
			String title) {
		return replaceImgTagAltAndTitle(content, alt, title, false);
	}

	/**
	 * 给富文本中的 img 标签 增加 alt 和 title
	 * 
	 * @param content
	 * @param alt
	 * @param title
	 * @param replace
	 *            是否替换原来的
	 * @return
	 */
	public static String replaceImgTagAltAndTitle(String content, String alt,
			String title, boolean replace) {
		StringBuffer sb = new StringBuffer();

		if (StringUtils.isNotBlank(content)) {
			String regEx_html = "<img[^>]+>"; // 定义HTML标签的正则表达式

			Pattern pattern = Pattern.compile(regEx_html);
			Matcher matcher = pattern.matcher(content);
			boolean result = matcher.find();
			while (result) {
				String html = matcher.group();
				StringBuffer replacement = new StringBuffer();
				replacement.append("<img ");
				if (replace) {
					replacement.append(" alt=\" " + alt + "\" ");
					replacement.append(" title=\" " + title + "\" ");
				} else {
					if (!html.contains("alt")) {
						replacement.append(" alt=\" " + alt + "\" ");
					}

					if (!html.contains("title")) {
						replacement.append(" title=\" " + title + "\" ");
					}
				}
				replacement.append(html.substring(4));
				matcher.appendReplacement(sb, replacement.toString());
				result = matcher.find();
			}

			matcher.appendTail(sb);
		}

		return sb.toString();

	}

	public static String getLikeCondition(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return "%" + name + "%";
	}

	/**
	 * 过滤 html script style标签
	 * 
	 * @param content
	 * @return
	 */
	public static String removeHtmlStyelAndScriptTag(String content) {
		if (StringUtils.isBlank(content)) {
			return "";
		}
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(content);
		content = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(content);
		content = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(content);
		content = m_html.replaceAll(""); // 过滤html标签
		return content;
	}

	public static String urlEncode(String url) {
		if (StringUtils.isNotBlank(url)) {
			try {
				return URLEncoder.encode(url, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("can not encode url :" + url);
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @param defaultVal
	 *            不是数字时默认的值
	 * @return
	 */
	public static int conver2Int(String val, int defaultVal) {
		if (StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)) {
			try {
				return Integer.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return defaultVal;
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @return 非法时或转换不成功时返回 0
	 */
	public static int conver2Int(String val) {
		if (StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)) {
			try {
				return Integer.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return conver2Int(val, 0);
	}
	
	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @param defaultVal
	 *            不是数字时默认的值
	 * @return
	 */
	public static float conver2Float(String val, float defaultVal) {
		if (StringUtils.isNotBlank(val)) {
			try {
				return Float.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return defaultVal;
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @return 非法时或转换不成功时返回 0
	 */
	public static float conver2Float(String val) {
		if (StringUtils.isNotBlank(val)) {
			try {
				return Float.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conver2Float(val, 0);
	}
	
	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @param defaultVal
	 *            不是数字时默认的值
	 * @return
	 */
	public static double conver2Double(String val, double defaultVal) {
		if (StringUtils.isNotBlank(val)) {
			try {
				return Double.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return defaultVal;
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @return 非法时或转换不成功时返回 0
	 */
	public static double conver2Double(String val) {
		if (StringUtils.isNotBlank(val)) {
			try {
				return Double.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conver2Double(val, 0);
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @return 非法时或转换不成功时返回 0
	 */
	public static long conver2Long(String val) {
		if (StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)) {
			try {
				return Long.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return conver2Long(val, 0);
	}

	/**
	 * 字符串转数字
	 * 
	 * @param val
	 * @param defaultVal
	 *            不是数字时默认的值
	 * @return
	 */
	public static long conver2Long(String val, long defaultVal) {
		if (StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)) {
			try {
				return Long.valueOf(val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return defaultVal;
	}

	/**
	 * 将数组链接成字符串
	 * 
	 * @param list
	 * @param separation
	 * @return
	 */
	public static <E> String jion(Collection<E> list, String separation) {
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (E t : list) {
				sb.append(t.toString());
				sb.append(separation);
			}
			if (sb.length() > 0) {
				return sb.substring(0, sb.length() - 1);
			}
		}
		return sb.toString();
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

//	public static String getPinYinHeadChar(String str) {
//		String convert = "";
//		for (int j = 0; j < str.length(); j++) {
//			char word = str.charAt(j);
//			// 提取汉字的首字母
//			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//			if (pinyinArray != null) {
//				convert += pinyinArray[0].charAt(0);
//			} else {
//				convert += word;
//			}
//		}
//		return convert;
//	}

	/**
	 * 右补位，左对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padRight(String oriStr, int len, char alexin) {
		int strlen = oriStr.length();
		String str = "";
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		str = str + oriStr;
		return str;
	}

	/**
	 * 左补位，右对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padLeft(String oriStr, int len, char alexin) {
		int strlen = oriStr.length();
		String str = "";
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		str = oriStr + str;
		return str;
	}
	
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */ 
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}

	public static void main(String[] args) {
//		System.out.println(CwStringUtil.getPinYinHeadChar("c沈楠111"));
	}
}
