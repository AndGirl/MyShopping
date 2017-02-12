package com.ybj.myshopping.fragment.found;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ybj.myshopping.R;
import com.ybj.myshopping.adapter.found.HotPostListViewAdapter;
import com.ybj.myshopping.bean.HotPostBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.utils.Constants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/12.
 * usg:热帖
 */

public class HotFoundFragment extends BaseFragment {

    @BindView(R.id.lv_hot_post)
    ListView mLvHotPost;

    private List<HotPostBean.ResultBean> result;

    @Override
    protected String getUrl() {
        return Constants.HOT_POST_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);
        HotPostListViewAdapter adapter = new HotPostListViewAdapter(getActivity(), result);
        mLvHotPost.setAdapter(adapter);

    }

    private void processData(String content) {
        result = JSON.parseObject(content, HotPostBean.class).getResult();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_hot_fragment;
    }

}
