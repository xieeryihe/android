package com.example.smtpclient.webView;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.smtpclient.HelpActivity;
import com.example.smtpclient.R;

public class GetAuthActivity extends AppCompatActivity {
    private WebView mWvMain;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_auth);
        mWvMain = findViewById(R.id.webview_email);
        String url = "https://blog.csdn.net";
        //加载网络URL
        //注意：直接访问网页是不行的，手机上会显示“网页无法打开”，就像没联网一样。
        //这是因为手机上网页默认是不支持js的，要通过设置使之支持js，否则有些网页加载不了，如下代码
        WebSettings webSettings = mWvMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);//开启DOM，可以在加载出的百度里点击百度的超链接跳转，不设置这个就会点击超链接，然后跳转失败
        //测试后发现即使不开启DOM也可以点击
        webSettings.setAppCacheEnabled(true);

        mWvMain.setWebViewClient(new GetAuthActivity.MyWebViewClient());
        //mWvMain.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);//聚焦，没什么用
        //mWvMain.loadUrl("https://m.baidu.com");//访问移动站点都是"m.xxx"，m是mobile，移动的意思
        mWvMain.loadUrl(url);
        //mWvMain.addJavascriptInterface();//使用js的另一种方法
        //mWvMain.setWebChromeClient(new MyWbeChromeClient());//使用Chrome，当然，My这个也是需要继承自己写的

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //当页面要跳转的时候，就会调用这个被重写的方法
            view.loadUrl(request.getUrl().toString());//意思是不跳转到手机浏览器
            //return super.shouldOverrideUrlLoading(view, request);//默认的返回的内容
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("WebView","onPageStarted...");//在log里面打印一些东西
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("WebView","onPageFinished...");
            //mWvMain.loadUrl("javascript:alert('hello')");//当页面加载完毕后弹出提示"hello"，目的是展示一下js代码的一种实现
            //mWvMain.evaluateJavascript("javascript:alert('hello')",null);//第二种使用js的方式
        }
    }

    //返回要用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode==KeyEvent.KEYCODE_BACK)&&mWvMain.canGoBack()){
            mWvMain.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}