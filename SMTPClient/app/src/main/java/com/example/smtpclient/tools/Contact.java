package com.example.smtpclient.tools;

import static com.example.smtpclient.MainActivity.gFilesPath;
import static com.example.smtpclient.MainActivity.gUsername;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Contact {
    private String mNickname;
    private String mUsername;

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        this.mNickname = nickname;
    }
    public String getUsername(){
        return mUsername;
    }
    public void setUsername(String username){
        this.mUsername = username;
    }

    public ArrayList<Contact> getContacts(String targetFileName){
        ArrayList<Contact> tempContacts = new ArrayList<>();
        String QQaccount = gUsername.substring(0,gUsername.indexOf('@'));//获取QQ号
        String dirPath = gFilesPath + "/"+ QQaccount;
        File jsonFile = new File(dirPath,"/" + targetFileName);
        String tagName = targetFileName.substring(0,targetFileName.indexOf("."));
        char[] buffer = new char[1024];
        StringBuilder allData = new StringBuilder();
        InputStreamReader input = null;
        int len;

        try {
            input = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            while((len =input.read(buffer))>0){
                allData.append(buffer,0,len);
            }
            JSONObject jsonObject = new JSONObject(allData.substring(allData.indexOf("{")));   //过滤读出的utf-8前三个标签字节,从{开始读取
            JSONArray jsonArray = jsonObject.getJSONArray(tagName);
            for (int i = 0 ;i<jsonArray.length();i++){
                Contact tempContact = new Contact();
                JSONObject tempJsonObject;
                tempJsonObject = jsonArray.getJSONObject(i);
                tempContact.setNickname(tempJsonObject.getString("nickname"));
                tempContact.setUsername(tempJsonObject.getString("username"));

                tempContacts.add(tempContact);
            }
        } catch (IOException | JSONException e ) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempContacts;
    }


}