package com.ybj.myshopping.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ybj.myshopping.R;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.fragment.weitao.FlowListFragment;
import com.ybj.myshopping.fragment.weitao.RandomListFragment;
import com.ybj.myshopping.fragment.weitao.TagFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/10.
 */

public class WeiTaoFragment extends BaseFragment {

    @BindView(R.id.tl_1)
    SegmentTabLayout mTl1;
    @BindView(R.id.fl_type)
    FrameLayout mFlType;

    //集合类
    private ArrayList<BaseFragment> mList;
    private FlowListFragment mFlowListFragment;
    private TagFragment mTagFragment;
    private RandomListFragment mRandomListFragment;

    private Fragment mFragment;

    //当前标签页
    private int mCurrentTab;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        initFragment();

    }

    /**
     * 初始化对应的Fragment
     */
    private void initFragment() {
        mList = new ArrayList<>();

        mFlowListFragment = new FlowListFragment();
        mTagFragment = new TagFragment();
        mRandomListFragment = new RandomListFragment();

        mList.add(mFlowListFragment);
        mList.add(mTagFragment);
        mList.add(mRandomListFragment);

        switchFragment(mFragment,mList.get(0));
    }

    /**
     * 选择Fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if(mTl1 != null) {
            mCurrentTab = mTl1.getCurrentTab();
        }

        if(mFragment != nextFragment) {
            mFragment = nextFragment;
            if(nextFragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //判断BaseFragemnt是否添加
                if(!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if(fromFragment != null) {
                        fragmentTransaction.hide(fromFragment);
                    }
                    fragmentTransaction.add(R.id.fl_type,nextFragment,"tagFragment").commit();
                }else{
                    //隐藏当前Fragment
                    if(fromFragment != null) {
                        fragmentTransaction.hide(fromFragment);
                    }
                    fragmentTransaction.show(nextFragment).commit();
                }
            }
        }
    }

    @Override
    protected void initTitle() {
        String[] titles = {"流式分类", "普通标签", "随机标签"};

        mTl1.setTabData(titles);

        mTl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(mFragment,mList.get(position));
            }

            @Override
            public void onTabReselect(int position) {
                
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wei;
    }

}
