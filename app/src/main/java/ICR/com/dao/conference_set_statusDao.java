package ICR.com.dao;



import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static ICR.com.activity.BaseActivity.static_connect;

public class conference_set_statusDao {
    static JSONObject jsoncontainer=new JSONObject();
    public static void statuspost(String room_name,String is_ok){


        try {
            jsoncontainer.put("room_name",room_name);
            jsoncontainer.put("authority",is_ok);

        } catch (JSONException e) {
            e.printStackTrace();
        };
        executeHttpPost();
    }
    private static void executeHttpPost() {
        HttpURLConnection con=null;
        String path=static_connect+"conference_set_status.php";
        try {
            URL url=new URL(path);
            con= (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "keep-alive");
            // con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//setRequestProperty设置http的请求头
            //con.setRequestProperty("Accept-Language", "zh-CN");

//           con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();

            DataOutputStream out=new DataOutputStream(con.getOutputStream());
            //  InputStream in;
            //  in=out.OutputStream();
            // out.writeBytes(jsoncontainer.toString());
            out.write(jsoncontainer.toString().getBytes());     //上传中文数据
            out.flush();
            out.close();
            //    int i=out.size();
            // Log.isLoggable("out.flush", i);
            System.out.println("jsoncontainer's data in connect"+jsoncontainer);
            if (con.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream in=con.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(in));
                StringBuilder sb=new StringBuilder();
                String line=null;
                while ((line=br.readLine())!=null){
                    sb.append(line+"\n");
                }
                // Log.i(TAG, "parseInfo: sb:"+sb.toString());//把错误输出来
                //   byte [] buf=new byte[in.available()];
                //in.read(buf);
                //  String str=new String(buf);
                //      Log.d("return message",str);


                Log.d("connect is ok?","success connect");

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
