package html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：趴取网页上的今天的天气
 * @author     zjm    
 * @time     2014/8/6
 */
public class Today {
    
     /** 
     * 发起http get请求获取网页源代码 
     * @param requestUrl     String    请求地址
     * @return                 String    该地址返回的html字符串
     */  
    private static String httpRequest(String requestUrl) {  
        
        StringBuffer buffer = null;  
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;
  
        try {  
            // 建立get请求
            URL url = new URL(requestUrl);  
            httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            // 获取输入流  
            inputStream = httpUrlConn.getInputStream();  
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
  
            // 从输入流读取结果
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally {
            // 释放资源
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrlConn != null){
                httpUrlConn.disconnect();  
            }
        }
        return buffer.toString();  
    }  
  
    /** 
     * 过滤掉html字符串中无用的信息
     * @param html    String    html字符串
     * @return         String    有用的数据
     */ 
    private static String htmlFiter(String html) {  
        
        StringBuffer buffer = new StringBuffer();  
        String str1 = "";
        String str2 = "";
        buffer.append("用户名:");
        
        // 取出有用的范围
        Pattern p = Pattern.compile("(.*)(<div class=\"pf_username\">)(.*?)(</div>)(.*)");  
        Matcher m = p.matcher(html);  
        if (m.matches()) {  
            str1 = m.group(3);//取得<><>中的内容
            // 匹配日期，注：日期被包含在<h2> 和 </h2>中
            p = Pattern.compile("(.*)(<span class=\"username\">)(.*?)(</span>)(.*)");
            m = p.matcher(str1);//在str1中匹配p
            if(m.matches()){
                str2 = m.group(3);//匹配第三个括号
                buffer.append(str2);
                buffer.append("\n天气：");
            }
        }  
        return buffer.toString();
    }
    
    /** 
     *  对以上两个方法进行封装。
     * @return 
     */  
    public static String getTodayTemperatureInfo(String get) {  
        // 调用第一个方法，获取html字符串
        String html = httpRequest(get);  
        // 调用第二个方法，过滤掉无用的信息
        String result = htmlFiter(html);  
        
        return result;  
    }   
}
