package com.ybj.myshopping.fragment.weitao;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.TagBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.ui.FlowLayout;
import com.ybj.myshopping.utils.Constants;
import com.ybj.myshopping.utils.UIUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/11.
 * usg:流式布局
 */

public class FlowListFragment extends BaseFragment {
    @BindView(R.id.fl_type)
    FlowLayout mFlType;

    //流式布局的结果
    private List<TagBean.ResultBean> result;

    //随机布局
    private Random mRandom;

    @Override
    protected String getUrl() {
        return Constants.TAG_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);

        initFlowLayout();

    }

    /**
     * 初始化流式布局的信息
     */
    private void initFlowLayout() {
        for (int i = 0 ; i < result.size(); i++){
            final TextView mTextView = new TextView(getActivity());

            mTextView.setText(result.get(i).getName());
            mTextView.setTextSize(UIUtils.dp2px(6));

            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            params.leftMargin = UIUtils.dp2px(8);
            params.topMargin = UIUtils.dp2px(8);
            params.rightMargin = UIUtils.dp2px(8);
            params.bottomMargin = UIUtils.dp2px(8);
            mTextView.setLayoutParams(params);

            mRandom = new Random();
            //产生随机颜色
            final int red = mRandom.nextInt(210);
            final int green = mRandom.nextInt(210);
            final int blue = mRandom.nextInt(210);

            Log.e("TAG", "这是第几个i ：" + i);

            //点击事件
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), mTextView.getText()+"" , Toast.LENGTH_SHORT).show();
                }
            });

            //设置背景
            mTextView.setTextColor(Color.rgb(red,green,blue));
            mTextView.setBackgroundResource(R.drawable.tag_bg1);

            //设置内边距
            int padding = UIUtils.dp2px(10);
            mTextView.setPadding(padding,padding,padding,padding);

            mFlType.addView(mTextView);

        }
    }

    /**
     * 解析数据
     * @param content
     */
    private void processData(String content) {
        TagBean tagBean = JSON.parseObject(content, TagBean.class);
        result = tagBean.getResult();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_flow;
    }

}
