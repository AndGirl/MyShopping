package com.ybj.myshopping;

import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ybj.myshopping.common.ActivityManager;
import com.ybj.myshopping.common.BaseActivity;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.fragment.AskFragment;
import com.ybj.myshopping.fragment.CartFragment;
import com.ybj.myshopping.fragment.HomeFragment;
import com.ybj.myshopping.fragment.MeFragment;
import com.ybj.myshopping.fragment.WeiTaoFragment;

import java.util.ArrayList;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.rgMain)
    RadioGroup rgMain;
    // Fragment集合
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    // 列表中对应的Fragment的位置pos
    private int pos;

    //Fragment管理工具类
    private FragmentTransaction mTransaction;

    private Fragment mFra;

    private boolean isExist = false;

    private Handler handler = new Handler();

    @Override
    protected void initView() {
        View view = getCurrentFocus();

    }

    @Override
    protected void initData() {

        // 初始化Fragment并加入集合
        initFragments();

        // 对RadioGroup设置监听
        rgMain.setOnCheckedChangeListener(new MainRadioGroupOncheckedChangeListener());

        // 默认进来是HomeFragment
        rgMain.check(R.id.rb_home);
    }



    class MainRadioGroupOncheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    pos = 0;
                    initWindow(R.color.statusBar_11);
                    break;
                case R.id.rb_weitao:
                    initWindow(R.color.statusBar_00);
                    pos = 1;
                    break;
                case R.id.rb_ask:
                    initWindow(R.color.statusBar_00);
                    pos = 2;
                    break;
                case R.id.rb_cart:
                    initWindow(R.color.statusBar_00);
                    pos = 3;
                    break;
                case R.id.rb_me:
                    initWindow(R.color.statusBar_11);
                    pos = 4;
                    break;
            }

            Fragment toFrag = getFragment(pos);
            switchFragment(mFra, toFrag);
        }
    }

    /**
     * 切换Fragment
     * 在项目中切换Fragment，一直都是用replace()方法来替换Fragment。
     * 但是这样做有一个问题，每次切换的时候Fragment都会重新实列化，
     * 重新加载一次数据，这样做会非常消耗性能和用户的流量。
     * 官方文档解释说：replace()这个方法只是在上一个Fragment不在需要时采用的简便方法。
     * 正确的切换方式是add()，切换时hide()，add()另一个Fragment；
     * 再次切换时，只需hide()当前，show()另一个。
     * 这样就能做到多个Fragment切换不重新实例化：
     */
    private void switchFragment(Fragment fromFrag, Fragment toFrag) {

        mTransaction = getSupportFragmentManager().beginTransaction();

        if (mFra != toFrag) {
            mFra = toFrag;
            if (toFrag != null) {
                // 判断toFrag是否添加
                if (!toFrag.isAdded()) {
                    //如果没有添加
                    if (fromFrag != null) {
                        mTransaction.hide(fromFrag);
                    }
                    mTransaction.add(R.id.main_framelayout, toFrag).commitAllowingStateLoss();
                } else {
                    //如果添加了
                    if (fromFrag != null) {
                        mTransaction.hide(fromFrag);
                    }
                    mTransaction.show(toFrag).commitAllowingStateLoss();
                }
            }

        }


    }

    /**
     * 得到某个Fragment
     *
     * @param pos
     * @return
     */
    public Fragment getFragment(int pos) {
        return mFragments.get(pos);
    }

    /**
     * 将所有fragment加入到集合中并初始化
     */
    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new WeiTaoFragment());
        mFragments.add(new AskFragment());
        // 为cartFragment设置回调，从购物车跳转至主页面
        CartFragment cartFragment = new CartFragment();
        mFragments.add(cartFragment);
//        cartFragment.setOnGoToHome(new CartFragment.onGoToHome() {
//            @Override
//            public void goToHome() {
//                rgMain.check(R.id.rb_home);
//            }
//        });
        mFragments.add(new MeFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 退出软件的功能
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        int size = ActivityManager.getInstance().getAcitivitySize();
        if (keyCode == KeyEvent.KEYCODE_BACK && size == 1) {
            if (0 != pos) {
                // 把主页选中
                rgMain.check(R.id.rb_home);
                return true;
            } else if (!isExist) {
                isExist = true;

                Toast.makeText(this, "再点击一次退出软件", Toast.LENGTH_SHORT).show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExist = false;
                    }
                }, 2000);

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initWindow(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏颜色
            int color = getResources().getColor(id);
            getWindow().setStatusBarColor(color);
        }
    }
}
