package com.example.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.activity.MainActivity;
import com.example.mybrowser.R;

public class EditUrlFragment extends Fragment implements OnClickListener,
        OnKeyListener {
    private Button btn_go;
    private EditText edit_url;
    private LinearLayout input_bg;
    private String url;
    private Fbtn_goClickListener fbtn_goClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_url, container,
                false);
        btn_go = (Button) view.findViewById(R.id.btn_go);
        edit_url = (EditText) view.findViewById(R.id.ed_input);
        input_bg = (LinearLayout) view.findViewById(R.id.input_bg);
        btn_go.setOnClickListener(this);
        input_bg.setOnClickListener(this);
        url = ((MainActivity) getActivity()).getUrl();
        edit_url.setText(url);
        return view;
    }

    public interface Fbtn_goClickListener {
        void onFbtn_goClick();

        void onFinput_bgClick();
    }

    // 设置回调接口
    public void setfBtn_goClickListener(
            Fbtn_goClickListener fBtn_goClickListener) {
        this.fbtn_goClickListener = fBtn_goClickListener;
    }

    @Override
    public void onClick(View v) {
        if (fbtn_goClickListener != null) {
            switch (v.getId()) {
                case R.id.btn_go:
                    go();
                    break;
                case R.id.input_bg:
                    fbtn_goClickListener.onFinput_bgClick();
                    break;
            }
        }
    }

    public void go() {
        // 关闭键盘
        InputMethodManager imm = (InputMethodManager) ((MainActivity) getActivity())
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(((MainActivity) getActivity())
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        url = edit_url.getText().toString();
        if (url != "") {
            ((MainActivity) getActivity()).setUrl(url);
        }
        fbtn_goClickListener.onFbtn_goClick();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_ENTER) {// 点击键盘回车键
            go();
        }
        return false;
    }

    public void setEdit_url(EditText edit_url) {
        this.edit_url = edit_url;
    }


}
