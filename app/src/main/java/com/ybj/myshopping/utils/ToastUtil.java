package com.ybj.myshopping.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 杨阳洋 on 2017/2/10.
 * usg:打印消息
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context,String content){
        if(mToast == null) {
            mToast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(content);
        }
        mToast.show();
    }

}
