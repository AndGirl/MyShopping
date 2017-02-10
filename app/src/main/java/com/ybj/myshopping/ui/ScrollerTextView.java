package com.ybj.myshopping.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 杨阳洋 on 2017/2/5.
 * 作用：滚动文字效果
 */

public class ScrollerTextView extends TextView{
    public ScrollerTextView(Context context) {
        this(context,null);
    }

    public ScrollerTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
