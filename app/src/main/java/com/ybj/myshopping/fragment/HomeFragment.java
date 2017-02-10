package com.ybj.myshopping.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ybj.myshopping.adapter.HomeRecycleAdapter;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.ResultBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.ui.ScrollerTextView;
import com.ybj.myshopping.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 杨阳洋 on 2017/2/10.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_scan)
    LinearLayout mHomeScan;
    @BindView(R.id.home_search)
    ImageView mHomeSearch;
    @BindView(R.id.home_search_desc)
    ScrollerTextView mHomeSearchDesc;
    @BindView(R.id.home_camera)
    ImageView mHomeCamera;
    @BindView(R.id.home_point_msg)
    TextView mHomePointMsg;
    @BindView(R.id.home_msg)
    LinearLayout mHomeMsg;
    @BindView(R.id.home_recyclerView)
    RecyclerView mHomeRecyclerView;
    @BindView(R.id.home_fab)
    FloatingActionButton mHomeFab;

    private ResultBean mResultBean;

    private HomeRecycleAdapter mRecycleAdapter;

    @Override
    protected String getUrl() {
        return Constants.HOME_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);

        mRecycleAdapter = new HomeRecycleAdapter(getActivity(), mResultBean);

        //设置适配器
        mHomeRecyclerView.setAdapter(mRecycleAdapter);

        //设置recyclerView的类型
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mHomeRecyclerView.setLayoutManager(manager);

        mHomeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();

                if(lastVisibleItemPosition > 4) {
                    mHomeFab.setVisibility(View.VISIBLE);
                }else{
                    mHomeFab.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 解析数据
     *
     * @param content
     */
    private void processData(String content) {
        JSONObject jsonObject = JSON.parseObject(content);

        //得到状态码
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        String result = jsonObject.getString("result");

        //得到resultBean的数据
        mResultBean = JSON.parseObject(result, ResultBean.class);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @OnClick({R.id.home_scan, R.id.home_search, R.id.home_search_desc, R.id.home_camera, R.id.home_point_msg, R.id.home_msg, R.id.home_recyclerView, R.id.home_fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_scan:
                break;
            case R.id.home_search:
                break;
            case R.id.home_search_desc:
                break;
            case R.id.home_camera:
                break;
            case R.id.home_point_msg:
                break;
            case R.id.home_msg:
                break;
            case R.id.home_recyclerView:
                break;
            case R.id.home_fab:
                break;
        }
    }
}
