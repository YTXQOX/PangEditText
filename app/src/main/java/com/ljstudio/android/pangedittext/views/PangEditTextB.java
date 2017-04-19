package com.ljstudio.android.pangedittext.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
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

public class PangEditTextB extends AppCompatEditText {

    private List<String> listCode3 = new ArrayList<>();
    private List<String> listCode4 = new ArrayList<>();

    private boolean isH = false;


    public PangEditTextB(Context context) {
        super(context);
        initData();
    }

    public PangEditTextB(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public PangEditTextB(Context context, AttributeSet attrs, int defStyleAttr) {
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

    PangEditTextB.OnKeyListener keyListener = new PangEditTextB.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                String strTemp = getText().toString();

//                if (!TextUtils.isEmpty(strTemp)) {
//                    int len = getText().toString().length();
//                    if (strTemp.endsWith("-")) {
//                        String str = strTemp.substring(0, len - 2);
//                        setText(str);
//                        setSelection(str.length());
//                    }
//                }
            }
            return false;
        }
    };

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s == null || s.length() == 0)
                return;

            if (s.toString().endsWith("-")) {
                isH = true;
            } else {
                isH = false;
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0)
                return;

            if (!isH) {
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
                    }
                }
            } else {
                String str = s.toString();
                int len = s.toString().length();
                System.out.println("TextWatcher-->str-->" + str);
                System.out.println("TextWatcher-->len-->" + len);

                if (len == 4 || (len == 3 && (str.startsWith("01") || str.startsWith("02")))) {
                    setText(str.substring(0, len - 1));
                    setSelection(getText().toString().length());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private class myTextWatcher implements TextWatcher {
        int beforeTextLength = 0;
        int onTextLength = 0;
        boolean isChanged = false;

        int location = 0;// 记录光标的位置
        private char[] tempChar;
        private StringBuffer buffer = new StringBuffer();
        int konggeNumberB = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            beforeTextLength = s.length();
            if (buffer.length() > 0) {
                buffer.delete(0, buffer.length());
            }
            konggeNumberB = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    konggeNumberB++;
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            onTextLength = s.length();
            buffer.append(s.toString());
            if (onTextLength == beforeTextLength || onTextLength <= 3
                    || isChanged) {
                isChanged = false;
                return;
            }
            isChanged = true;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isChanged) {
                location = getSelectionEnd();
                int index = 0;
                while (index < buffer.length()) {
                    if (buffer.charAt(index) == ' ') {
                        buffer.deleteCharAt(index);
                    } else {
                        index++;
                    }
                }

                index = 0;
                int konggeNumberC = 0;
                while (index < buffer.length()) {
                    if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                        buffer.insert(index, ' ');
                        konggeNumberC++;
                    }
                    index++;
                }

                if (konggeNumberC > konggeNumberB) {
                    location += (konggeNumberC - konggeNumberB);
                }

                tempChar = new char[buffer.length()];
                buffer.getChars(0, buffer.length(), tempChar, 0);
                String str = buffer.toString();
                if (location > str.length()) {
                    location = str.length();
                } else if (location < 0) {
                    location = 0;
                }

                setText(str);
                Editable etable = getText();
                Selection.setSelection(etable, location);
                isChanged = false;
            }
        }

    }
}
