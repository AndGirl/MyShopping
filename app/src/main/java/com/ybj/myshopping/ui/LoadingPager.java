package com.ybj.myshopping.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ybj.myshopping.R;
import com.ybj.myshopping.utils.DownLoaderUtils;
import com.ybj.myshopping.utils.UIUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 杨阳洋 on 2017/2/10.
 */

public abstract class LoadingPager extends FrameLayout{

    // 四个状态
    private final int STATE_LOADING = 1;//加载
    private final int STATE_ERROR = 2;//错误
    private final int STATE_EMPTY = 3;//空页面
    private final int STATE_SUCCESS = 4;//成功

    // 当前状态
    private int state_current = STATE_LOADING;

    // 四个View
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;

    private Context mContext;

    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始化四种状态
     */
    private void init(){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (view_loading == null) {
            view_loading = UIUtils.getView(R.layout.page_loading);
            addView(view_loading, params);
        }
        if (view_error == null) {
            view_error = UIUtils.getView(R.layout.page_error);
            // 重试功能
            LinearLayout ll_retry = (LinearLayout) view_error.findViewById(R.id.ll_retry);
            ll_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            });
            addView(view_error, params);
        }
        if (view_empty == null) {
            view_empty = UIUtils.getView(R.layout.page_empty);
            addView(view_empty, params);
        }

        // 根据当前state_current的值，决定显示哪个具体的view
        showSafePage();
    }

    /**
     * 更新页面
     */
    private void showSafePage(){
        //更新界面在主线程中进行
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    /**
     * 在主线程中显示哪个具体的view
     */
    private void showPage(){
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.GONE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.GONE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.GONE);

        if (view_success == null) {
            view_success = View.inflate(mContext, layoutId(), null);
            addView(view_success);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }

    /**
     * 具体加载页面由子类决定
     * @return
     */
    public abstract int layoutId();

    private ResultState resultState;

    /**
     * 联网操作
     */
    public void show(){
        
        final String url = url();
        
        if(TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.setContent(null);
            loadPage();
            return;
        }

        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * subscribeOn的调用切换之前的线程。
                    observeOn的调用切换之后的线程。
                    observeOn之后，不可再调用subscribeOn 切换线程
                 */
                DownLoaderUtils loaderUtils = new DownLoaderUtils();
                loaderUtils.getJsonResult(url())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                loadPage();
                            }

                            @Override
                            public void onError(Throwable e) {
                                resultState = ResultState.ERROR;
                                resultState.setContent(null);
                                loadPage();
                            }

                            @Override
                            public void onNext(String s) {
                                if (!TextUtils.isEmpty(s)) {
                                    resultState = ResultState.SUCCESS;
                                    resultState.setContent(s);
                                } else {
                                    resultState = ResultState.EMPTY;
                                    resultState.setContent(null);
                                }
                            }
                        });
            }
        },2000);
    }

    /**
     * 加载状态
     */
    private void loadPage() {
        switch (resultState) {
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
        }

        showSafePage();

        if (state_current == STATE_SUCCESS) { // 联网成功
            onSuccess(resultState, view_success);
        }

    }

    protected abstract void onSuccess(ResultState resultState, View view_success);

    protected abstract String url();

    // 提供一个枚举类,将当前联网以后的状态以及可能返回的数据，封装在枚举类中
    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;

        private String content;

        ResultState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
