package com.example.smtpclient.tools;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

/*
JSON知识点请参考

https://www.cnblogs.com/lifexy/p/12030880.html

*/

public class JsonFile {
    public String mJsonObject;
    public JsonFile(){
        mJsonObject = "";
    }
    public JsonFile(String obj){
        mJsonObject = obj;
    }
    public void setJsonObject(String mJsonObject) {
        this.mJsonObject = mJsonObject;
    }

    public void init() throws IOException, JSONException {
        if (Objects.equals(mJsonObject, "")){
            System.out.println("lack file object!\n");
            System.exit(-1);
        }
        File file = new File(mJsonObject);
        if (!file.exists()){//如果文件不存在
            file.createNewFile();
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(mJsonObject),"UTF-8");
            JSONObject jsonObject=new JSONObject();//创建JSONObject对象
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("Users",jsonArray);
        } else {
            System.out.println("文件已存在。\n");
        }
    }

    public void writeJson(String key, String value) throws IOException, JSONException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(mJsonObject),"UTF-8");
        JSONObject jsonObject=new JSONObject();//创建JSONObject对象
        jsonObject.put(key,value);
        System.out.println(jsonObject.toString());
        osw.write(jsonObject.toString());
        osw.flush();//清空缓冲区，强制输出数据
        osw.close();//关闭输出流

    }
    public void readJson(String key) throws IOException, JSONException {
        char[] cbuf = new char[10000];
        InputStreamReader input =new InputStreamReader(new FileInputStream(mJsonObject),"UTF-8");
        int len =input.read(cbuf);
        String text =new String(cbuf,0,len);
        //1.构造一个json对象
        JSONObject jsonObject = new JSONObject(text.substring(text.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取

        //2.通过getXXX(String key)方法获取对应的值
        String str = jsonObject.getString(key);
        JSONArray jsonArray = jsonObject.getJSONArray("Users");
    }
}
