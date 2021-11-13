package com.example.myapplication1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication1.R;


/*
报错
2021-09-07 13:12:04.464 2687-2806/? E/chromium: [ERROR:address_tracker_linux.cc(245)] Could not send NETLINK request: Permission denied (13)
导致百度的进去，点击百度主页的超链接无法点进去，白屏，输入框点击也是白屏
但是换了CSDN主页就没事
*/
public class WebViewActivity extends AppCompatActivity {
    private WebView mWvMain;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url_1 = "https://blog.csdn.net";
        String url_2 = "https://www.baidu.com";
        setContentView(R.layout.activity_web_view);
        mWvMain = findViewById(R.id.wv);
        //加载本地html
        //mWvMain.loadUrl("file:///android_asset/test.html");
        //file:///后面是加载本地app下的资源文件，即asset目录，其中的html文件

        //加载网络URL
        //注意：直接访问网页是不行的，手机上会显示“网页无法打开”，就像没联网一样。
        //这是因为手机上网页默认是不支持js的，要通过设置使之支持js，否则有些网页加载不了，如下代码
        WebSettings webSettings = mWvMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setDomStorageEnabled(true);//开启DOM，可以在加载出的百度里点击百度的超链接跳转，不设置这个就会点击超链接，然后跳转失败
        //测试后发现即使不开启DOM也可以点击
        //webSettings.setAppCacheEnabled(true);
        mWvMain.setWebViewClient(new MyWebViewClient());
        //mWvMain.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);//聚焦，没什么用
        //mWvMain.loadUrl("https://m.baidu.com");//访问移动站点都是"m.xxx"，m是mobile，移动的意思
        mWvMain.loadUrl(url_1);
        //mWvMain.addJavascriptInterface();//使用js的另一种方法
        mWvMain.setWebChromeClient(new MyWbeChromeClient());
    }
    class MyWebViewClient extends WebViewClient{
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
    class MyWbeChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //这个可以实现进度条（如方法名字面含义），newProgress参数只能在0~100之间
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            //可以获取网页标题
            super.onReceivedTitle(view, title);//将当前activity页面的标题显示为 网页的title标签里的内容
            setTitle(title);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode==KeyEvent.KEYCODE_BACK)&&mWvMain.canGoBack()){
            mWvMain.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
