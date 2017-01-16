package com.example.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.MainActivity;
import com.example.mybrowser.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class MainFragment extends Fragment implements OnClickListener {
    private WebView webView;
    private TextView Text_show_url;
    private Button btn_refresh;
    private LinearLayout btn_back;
    private LinearLayout btn_next;
    private LinearLayout btn_home;
    private LinearLayout btn_close;
    private ProgressBar bar;// 进度条
    private String homepage;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getId(view);
        // 对五个按钮和地址栏添加点击监听事件
        btn_refresh.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        Text_show_url.setOnClickListener(this);
        webView.setOnClickListener(this);
        sp = ((MainActivity) getActivity()).getSharedPreferences("settings",
                ((MainActivity) getActivity()).MODE_APPEND);
        homepage = sp.getString("homepage", "http://vip.cl95.cc/CLyjx/homepage.html");
        startReadUrl(homepage);


        SharedPreferences sp = ((MainActivity) getActivity())
                .getSharedPreferences("browser",
                        ((MainActivity) getActivity()).MODE_APPEND);
        return view;
    }


    private void getId(View view) {
        webView = (WebView) view.findViewById(R.id.webView);
        Text_show_url = (TextView) view.findViewById(R.id.Text_show_url);
        btn_refresh = (Button) view.findViewById(R.id.btn_refresh);
        btn_next = (LinearLayout) view.findViewById(R.id.btn_next);
        btn_back = (LinearLayout) view.findViewById(R.id.btn_back);
        btn_home = (LinearLayout) view.findViewById(R.id.btn_home);
        btn_close = (LinearLayout) view.findViewById(R.id.btn_close);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    public void startReadUrl(String url) {


        // 先停止加载当前页面
        webView.stopLoading();
        // webView加载web资源
        webView.loadUrl(url);
        // 覆盖webView默认通过系统或者第三方浏览器打开网页的行为
        // 如果为false调用系统或者第三方浏览器打开网页的行为
        WebSettings settings = webView.getSettings();
        //gitshiyong
        // 启用支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true);
        // web加载页面优先使用缓存加载
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(((MainActivity) getActivity()), "加载失败，请稍候再试",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // 当打开页面时 显示进度
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 加载完毕
                    bar.setVisibility(View.INVISIBLE);
                    ((MainActivity) getActivity()).setUrl(webView.getUrl());
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Text_show_url.setText(title);
            }


        });
    }

    // 设置点击网页标题回调接口
    public interface FMainClickListener {
        void onFed_showClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:// 刷新
                webView.reload();
                break;
            case R.id.btn_back:// 返回
                if (webView.canGoBack()) {
                    webView.goBack();
                    Text_show_url.setText(webView.getTitle());
                } else {
                    Log.i("tag", "error");
                }
                break;
            case R.id.btn_next:// 前进
                if (webView.canGoForward()) {
                    webView.goForward();
                    Text_show_url.setText(webView.getTitle());
                }
                break;
            case R.id.btn_home:
                startReadUrl(homepage);
                break;
            case R.id.Text_show_url:
                // ((MainActivity) getActivity()).setUrl(webView.getUrl());
                // ((MainActivity) getActivity()).setBrowse(browse);
                // 交给点击网页标题宿主Activity处理
                if (getActivity() instanceof FMainClickListener) {
                    ((FMainClickListener) getActivity()).onFed_showClick();
                }
                break;
            case R.id.btn_close:
                this.getActivity().finish();
                break;
            case R.id.webView:
                break;

        }
    }

    public boolean hasLinkWifi() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) ((MainActivity) getActivity())
                .getSystemService(((MainActivity) getActivity()).WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

}


