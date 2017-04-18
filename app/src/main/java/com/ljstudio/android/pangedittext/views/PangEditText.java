package com.ljstudio.android.pangedittext.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.ljstudio.android.pangedittext.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guoren on 2017/4/18 15:37
 * Usage
 */

public class PangEditText extends AppCompatEditText {

    private List<String> listCode3 = new ArrayList<>();
    private List<String> listCode4 = new ArrayList<>();


    public PangEditText(Context context) {
        super(context);
        initData();
    }

    public PangEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public PangEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        listCode3 = Arrays.asList(getResources().getStringArray(R.array.phone_code_3));
        listCode4 = Arrays.asList(getResources().getStringArray(R.array.phone_code_4));

        addTextChangedListener(textWatcher);
        setOnKeyListener(keyListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    OnKeyListener keyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                String strTemp = getText().toString();

                if (!TextUtils.isEmpty(strTemp)) {
                    int len = getText().toString().length();
                    if (strTemp.endsWith("-")) {
                        String str = strTemp.substring(0, len - 2);
                        setText(str);
                        setSelection(str.length());
                    }
                }
            }
            return false;
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0)
                return;

            if (!s.toString().endsWith("-")) {
                if (3 == s.length()) {
                    for (String str : listCode3) {
                        if (str.equals(s.toString())) {
                            setText(s + "-");
                            setSelection(getText().toString().length());
                            break;
                        }
                    }
                } else if (4 == s.length()) {
                    for (String str : listCode4) {
                        if (str.equals(s.toString())) {
                            setText(s + "-");
                            setSelection(getText().toString().length());
                            break;
                        }
                    }
                } else {

                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
