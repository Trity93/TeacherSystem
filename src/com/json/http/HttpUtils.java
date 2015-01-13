package com.json.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/*
 * 从服务器请求获取到JSON数据格式的字符串
 * 
 */
public class HttpUtils {
	public HttpUtils() {  
    }  
  
    public static String getJsonContent(String url_path) {  
        try {  
            URL url = new URL(url_path);  
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
            connection.setConnectTimeout(3000); // 请求超时时间3s  
            connection.setRequestMethod("GET");  
            connection.setDoInput(true);  
            int code = connection.getResponseCode(); // 返回状态码  
            if (code == 200) {  
                // 或得到输入流，此时流里面已经包含了服务端返回回来的JSON数据了,此时需要将这个流转换成字符串  
                return changeInputStream(connection.getInputStream());  
            }  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return "";  
    }  
  
    private static String changeInputStream(InputStream inputStream) {  
        // TODO Auto-generated method stub  
        String jsonString = "";  
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
        int length = 0;  
        byte[] data = new byte[1024];  
        try {  
            while (-1 != (length = inputStream.read(data))) {  
                outputStream.write(data, 0, length);  
            }  
            // inputStream流里面拿到数据写到ByteArrayOutputStream里面,  
            // 然后通过outputStream.toByteArray转换字节数组，再通过new String()构建一个新的字符串。  
            jsonString = new String(outputStream.toByteArray());  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return jsonString;  
    }  
}
