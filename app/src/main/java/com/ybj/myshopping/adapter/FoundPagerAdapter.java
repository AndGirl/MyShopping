package com.ybj.myshopping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ybj.myshopping.fragment.found.HotFoundFragment;
import com.ybj.myshopping.fragment.found.NewFoundFragment;

/**
 * Created by 杨阳洋 on 2017/2/12.
 * usg:发现页面
 */

public class FoundPagerAdapter extends FragmentPagerAdapter {

    private final String[] mTitles ;

    public FoundPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
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

    /**
     * 返回指定位置的tab标题
     * @param position
     * @return
     */
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
