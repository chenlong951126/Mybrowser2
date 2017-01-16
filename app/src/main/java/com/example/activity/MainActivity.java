package com.example.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.fragment.EditUrlFragment;
import com.example.fragment.EditUrlFragment.Fbtn_goClickListener;
import com.example.fragment.MainFragment;
import com.example.fragment.MainFragment.FMainClickListener;
import com.example.mybrowser.R;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

public class MainActivity extends Activity implements Fbtn_goClickListener,
        FMainClickListener {

    private MainFragment mainF;
    private EditUrlFragment editF;
    private String url;
    final int CODE = 0x717;// 定义一个请求码常量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QbSdk.initX5Environment(MainActivity.this, null);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mainF = new MainFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.id_content, mainF, "mainF");
            tx.commit();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == CODE) {// 点击历史记录的链接后跳到该页面
            Bundle bundle = data.getExtras();
            // Toast.makeText(MainActivity.this, bundle.getString("historyUrl"),
            // Toast.LENGTH_LONG).show();
            mainF.startReadUrl(bundle.getString("historyUrl"));
        }
    }

    ;

    public void onFed_showClick() {
        // 切换到Editfragment
        // if (editF == null) {
        editF = new EditUrlFragment();
        editF.setfBtn_goClickListener(this);// 回调函数
        // }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        // tx.replace(R.id.id_content, editF, "editF");
        tx.add(R.id.id_content, editF, "editF");
        tx.addToBackStack(null);
        tx.commit();
    }


    public void onFbtn_goClick() {
        // 从editF切换mainfragment
        if (mainF == null) {
            mainF = new MainFragment();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        // url = getUrl();
        if (url != null && url != "") {
            if (url.indexOf("http://") < 0 && url.indexOf("https://") < 0) {
                url = "http://" + url;
            }
            url = url.replace(" ", "");// 去掉空格
            mainF.startReadUrl(url);
        }
        tx.remove(editF);
        // tx.add(R.id.id_content, mainF, "mainF");
        // tx.addToBackStack(null);
        tx.commit();

    }

    @Override
    public void onFinput_bgClick() {
        // TODO Auto-generated method stub
        // 在输入url时点击空白事件
        if (mainF == null) {
            mainF = new MainFragment();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.remove(editF);
        tx.commit();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webview = (WebView) findViewById(R.id.webView);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.destroy();
    }
}