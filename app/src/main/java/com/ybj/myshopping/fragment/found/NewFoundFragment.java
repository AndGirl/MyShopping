package com.ybj.myshopping.fragment.found;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ybj.myshopping.R;
import com.ybj.myshopping.adapter.found.NewPostListViewAdapter;
import com.ybj.myshopping.bean.NewPostBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.utils.Constants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/12.
 */

public class NewFoundFragment extends BaseFragment {

    @BindView(R.id.lv_new_post)
    ListView mLvNewPost;

    private List<NewPostBean.ResultBean> mResult;

    @Override
    protected String getUrl() {
        return Constants.NEW_POST_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);
        NewPostListViewAdapter adapter = new NewPostListViewAdapter(getActivity(), mResult);
        mLvNewPost.setAdapter(adapter);
    }

    /**
     * 解析数据
     * @param json
     */
    private void processData(String json) {
        mResult = JSON.parseObject(json,NewPostBean.class).getResult();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found_new;
    }

}
