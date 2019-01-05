package net.hycollege.ljl.pleasedfood.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 这是一个网络请求实体类
 * 负责网络数据交互
 */
public class InternetData {
    public static DataListener listener;
    /**
     * 网络请求实体
     * @param path 远程服务器路径
     * @param data  传参,封装的数据
     * @param listener 监听对象
     */
    public static void getRequest(final String path, final String data, final DataListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String request="";
                InternetData.listener=listener;
                try {
                    URL url=new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(5000);
                    conn.setRequestProperty("Content-Type","text/json;charset=gbk");
                    conn.connect();
                    if(data!=null){
                        OutputStream out = conn.getOutputStream();
                        out.write(data.getBytes());
                    }
                    int code = conn.getResponseCode();
                    Log.e("TAG==code",""+code);
                    if(code==200){
                        InputStream in = conn.getInputStream();
                        InputStreamReader re=new InputStreamReader(in);
                        BufferedReader bu=new BufferedReader(re);
                        String line="";
                        while ((line=bu.readLine())!=null){
                            request +=line;
                        }
                        in.close();
                        re.close();
                        bu.close();
                        conn.disconnect();
                        listener.getdata(request);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }).start();
    }
    /**
     * 接口：返回网络相应数据
     */
    public interface DataListener{
        void getdata(String data);
    }
}
