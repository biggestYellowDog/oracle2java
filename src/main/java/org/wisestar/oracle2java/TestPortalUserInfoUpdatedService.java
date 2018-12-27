package org.wisestar.oracle2java;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * 测试门户  1.用户3天内变化的数据
 * @author yunsky
 * @date 2018-12-01
 *
 */
public class TestPortalUserInfoUpdatedService {

public static void main(String[] args) throws ParseException {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String appEncodeKey = "96d6f184eb272d5e3a96";

	String callUrl = "http://10.68.26.174:8080/module-appweb/appweb/user/updated/get.shtml";
		
	JSONObject reqObject = new JSONObject();
		
	String timestamp = String.valueOf(System.currentTimeMillis());
	String sourceSign =  timestamp + appEncodeKey;
		
	reqObject.put("sign",DigestUtils.md5Hex(sourceSign));
	reqObject.put("timestamp", timestamp);
	String returnjson = HttpClientUtils.callUrlPostByJson(callUrl, reqObject.toJSONString());

	System.out.println(returnjson);
	JSONObject returnJsonObj = JSONObject.parseObject(returnjson);
	if(returnJsonObj != null) {
		JSONObject validateResultObject = returnJsonObj.getJSONObject("data");
		String resultCode = validateResultObject.getString("returnCode");
		String resultMessage = validateResultObject.getString("returnMessage");
		System.out.println("resultMessage==="+resultMessage);
		JSONArray jsonArray = validateResultObject.getJSONArray("data");
		System.out.println(jsonArray.toString());
		Iterator iterator = jsonArray.iterator();
		while(iterator.hasNext()){
			JSONObject jsonObject = (JSONObject) iterator.next();
			String realName = (String) jsonObject.get("realName");
			String mobileNum = (String) jsonObject.get("mobileNum");
			String loginName = (String) jsonObject.get("loginName");
			String isEnable = (String) jsonObject.get("isEnable");
			String updateDate = (String) jsonObject.get("updateDate");
			String companyName = (String) jsonObject.get("companyName");
			String orgName = (String) jsonObject.get("orgName");
			String email = (String) jsonObject.get("email");
			String wechatOpenId = (String) jsonObject.get("wechatOpenId");
			String wxworkOpenId = (String) jsonObject.get("wxworkOpenId");
			System.out.println("realName--"+realName+";mobileNum--"+mobileNum+";loginName--"+loginName+";isEnable--"+isEnable+";updateDate--"+updateDate+";companyName--"+companyName+";orgName--"+orgName+";email--"+email+";wechatOpenId--"+wechatOpenId+";wxworkOpenId--"+wxworkOpenId);
		}
		if("0000".equals(resultCode)) {
			System.out.println("校验通过");
		} else {
			System.err.println(resultMessage);
		}
	}

	// result:success 表示请求成功 code =0
	//取结果 data 里面的 returnCode 表示校验结果   0000  表示成功
	//{"message":"","id":null,"data":{"data":[{"id":"48538","realName":"胡军华","mobileNum":null,"loginName":"spd.hjh","isEnable":"N","updateDate":"2018-12-03 14:36:08.0","companyName":"运输中心","orgName":"干线发展组","email":"hujunhua@cjspd.com"}],"returnCode":"1005","returnMessage":"success"},"result":"success","code":0}


	}
	
}
