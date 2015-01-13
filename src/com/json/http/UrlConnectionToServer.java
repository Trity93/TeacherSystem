package com.json.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

import com.common.Info;
import com.json.utils.GsonTools;
import com.teachersystem.R;

public class UrlConnectionToServer {
	public final String urlAddress = "http://210.38.206.191:8888/StaffSystem/servlet/JsonAction";
	URL url;
	HttpURLConnection uRLConnection;

	public UrlConnectionToServer() {

	}

	// 向服务器发送get请求
	public String doGet(String username, String password) {
		String getUrl = urlAddress + "?LoginName=" + username + "&LoginPwd="
				+ password;
		try {
			url = new URL(getUrl);
			uRLConnection = (HttpURLConnection) url.openConnection();
			InputStream is = uRLConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = "";
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			br.close();
			uRLConnection.disconnect();
			return response;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int initPostUrlCon() {
		try {
			url = new URL(urlAddress);
			uRLConnection = (HttpURLConnection) url.openConnection();
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			uRLConnection.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true, 默认情况下是false;
			uRLConnection.setDoOutput(true);
			// 设定请求的方法为"POST"，默认是GET
			uRLConnection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			uRLConnection.setUseCaches(false);
			uRLConnection.setInstanceFollowRedirects(false);
			// 防止网络错误
			uRLConnection.setReadTimeout(10000 /* milliseconds */);
			uRLConnection.setConnectTimeout(15000 /* milliseconds */);// 链接超时
			// 设定传送的内容类型是可序列化的java对象
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			uRLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接，上面对urlConn的所有配置必须要在connect之前完成，
			uRLConnection.connect();
		} catch (MalformedURLException e) {
			Log.i("URL链接错误","URL链接错误");
			e.printStackTrace();
			return -1;
		} catch (ProtocolException e) {
			Log.i("post请求错误","post请求错误");
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			Log.i("uRLConnection链接错误","uRLConnection链接错误");
			e.printStackTrace();
			return -1;
		}
		return 1;

	}

	/*
	 * flag:1 基本信息 2 考勤 3 加班 4 请假 5 薪水
	 */
	public String postWhat(int flag,String userId) {
		String content;
		String response = null;
		content="flag="+flag+"&LoginName="+userId;
		response=doPost(content);
		
		return response;
	}
	/*执行考勤，加班，请假
	 * flag:7:添加
	 * wol:1 work 返回数据id， 2 overtime  3 leave --》1成功，-1失败
	 */
	public int postAddWhat(int wol,String userId) {
		String content;
		String response = null;
		content="WOL="+wol+"&flag=7&LoginName="+userId;
		response=doPost(content);
		if(response==null)
			return 0;
		if(!response.equals("null")||!response.equals("")){
			return Integer.parseInt(response);
		}
		return 0;
	}
	/*执行考勤下班的修改，和个人资料的修改
	 * flag:8:修改
	 * wol:1 work 2 info
	 */
	public int postWhat(int wol,int flag,String userId,int dataID) {
		String content;
		Log.i("dataID",dataID+"");
		String response = null;
		content="WOL="+wol+"&flag=8&data="+dataID+"&LoginName="+userId;
		response=doPost(content);
		return 0;
	}

	// 向服务器发送post请求
	public String doPost(String content) {
		try {
			if(initPostUrlCon()==-1){ // 初始化
				return null;
			}
			// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			DataOutputStream out = new DataOutputStream(
					uRLConnection.getOutputStream());
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			out.writeBytes(content);
			// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
			out.flush();
			// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
			// 再调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
			out.close();
			// 判断请求是否成功
			if (uRLConnection.getResponseCode() == 200)
				Log.i("Post", R.string.success+"");
			else
				Log.i("Post",
						R.string.request_error + ""
								+ uRLConnection.getResponseCode());
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream is = uRLConnection.getInputStream();// <===注意，实际发送请求的代码段就在这里
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = "";
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			br.close();
			uRLConnection.disconnect();
			return response;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String doPost(Info mInfo) {
		try {
			if(initPostUrlCon()==-1) // 初始化
				return null;
			String content = "flag=6&LoginName=" + mInfo.getEmpId() + "&LoginPwd="
					+ mInfo.getEmpPwd();
			// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
			// 所以在开发中不调用上述的connect()也可以)。
			DataOutputStream out = new DataOutputStream(
					uRLConnection.getOutputStream());
			// 向对象输出流写出数据，这些数据将存到内存缓冲区中
			out.writeBytes(content);
			// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
			out.flush();
			// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
			// 再调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
			out.close();
			// 判断请求是否成功
			if (uRLConnection.getResponseCode() == 200)
				Log.i("Post", R.string.success+"");
			else
				Log.i("Post",
						R.string.request_error + ""
								+ uRLConnection.getResponseCode());
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream is = uRLConnection.getInputStream();// <===注意，实际发送请求的代码段就在这里
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String response = "";
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			br.close();
			uRLConnection.disconnect();
			return response;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
