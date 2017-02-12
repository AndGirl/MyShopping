package com.ybj.myshopping.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.TabEntity;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.fragment.found.HotFoundFragment;
import com.ybj.myshopping.fragment.found.NewFoundFragment;
import com.ybj.myshopping.ui.customSlidingTabLayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/10.
 * usg：发现页面
 */

public class AskFragment extends BaseFragment {

    //标题
    private final String[] mTitles = {
            "热门", "穿搭", "海淘"
            , "育儿", "装修", "美妆", "数码", "健身"
    };

    //图片
    private final int[] imgIds = {
            R.drawable.hot, R.drawable.looks, R.drawable.seabuy,
            R.drawable.son, R.drawable.zx, R.drawable.mz,
            R.drawable.sm, R.drawable.js
    };

    @BindView(R.id.ib_found_icon)
    ImageButton mIbFoundIcon;
    @BindView(R.id.tv_found_search)
    TextView mTvFoundSearch;
    @BindView(R.id.ib_found_msg)
    ImageButton mIbFoundMsg;
    @BindView(R.id.tl_10)
    SlidingTabLayout mTl10;
    @BindView(R.id.vp_found)
    ViewPager mVpFound;

    //标签栏集合
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        for (int i = 0 ; i < mTitles.length ; i ++){
            mTabEntities.add(new TabEntity(mTitles[i],imgIds[i],imgIds[i]));
        }

        mVpFound.setAdapter(new FoundPagerAdapter(getActivity().getSupportFragmentManager()));

        //tablayout与viewPager绑定
        mTl10.setViewPager(mVpFound);

        mTl10.setTabData(mTabEntities);

        mVpFound.setCurrentItem(0);
    }

    @Override
    protected void initTitle() {
        mIbFoundIcon.setImageResource(R.drawable.android_icon_camera);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ask;
    }

    public class FoundPagerAdapter extends FragmentPagerAdapter {

        public FoundPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NewFoundFragment();
                case 1:
                    return new HotFoundFragment();
                case 2:
                    return new NewFoundFragment();
                case 3:
                    return new HotFoundFragment();
                case 4:
                    return new NewFoundFragment();
                case 5:
                    return new HotFoundFragment();
                case 6:
                    return new NewFoundFragment();
                case 7:
                    return new HotFoundFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 注释掉就取消了预加载
//            super.destroyItem(container, position, object);
        }
    }
}
