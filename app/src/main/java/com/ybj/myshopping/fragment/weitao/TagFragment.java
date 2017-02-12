package com.ybj.myshopping.fragment.weitao;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ybj.myshopping.R;
import com.ybj.myshopping.adapter.weitao.TypeLeftAdapter;
import com.ybj.myshopping.adapter.weitao.TypeRightCommonAdapter;
import com.ybj.myshopping.adapter.weitao.TypeRightHotAdapter;
import com.ybj.myshopping.bean.TypeBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.utils.Constants;
import com.ybj.myshopping.utils.DownLoaderUtils;
import com.ybj.myshopping.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 杨阳洋 on 2017/2/11.
 * usg:普通布局
 */

public class TagFragment extends BaseFragment {

    @BindView(R.id.lv_left)
    ListView mLvLeft;
    @BindView(R.id.rcv_hot)
    RecyclerView mRcvHot;
    @BindView(R.id.rcv_type)
    RecyclerView mRcvType;

    //用到的接口
    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};
    private TypeLeftAdapter mTypeLeftAdapter;

    private List<TypeBean.ResultBean> result;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        mTypeLeftAdapter = new TypeLeftAdapter(getActivity());
        mLvLeft.setAdapter(mTypeLeftAdapter);
        //为左边的listView设置监听事件
        initListener(mTypeLeftAdapter);
        mLvLeft.setSelection(0);
        getDataFromNet(urls[0],0);

    }

    /**
     * 网络获取数据
     * @param url
     * @param i
     */
    private void getDataFromNet(String url, int i) {
        new DownLoaderUtils().getJsonResult(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(getActivity(), "服务器异常,请重试" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        processData(s);
                        TypeRightHotAdapter hotAdapter = new TypeRightHotAdapter(getActivity(), result.get(0).getHot_product_list());
                        //点击事件
                        hotAdapter.setOnItemClickListener(new TypeRightHotAdapter.OnItemClickListener() {
                            @Override
                            public void setOnItemClickListener(TypeBean.ResultBean.HotProductListBean data) {

                                String name = data.getName();
                                String cover_price = data.getCover_price();
                                String figure = data.getFigure();
                                String product_id = data.getProduct_id();

                            }
                        });

                        mRcvHot.setAdapter(hotAdapter);
                        mRcvHot.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

                        TypeRightCommonAdapter commonAdapter = new TypeRightCommonAdapter(getActivity(), result.get(0).getChild());
                        mRcvType.setAdapter(commonAdapter);
                        mRcvType.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    }
                });
    }

    /**
     * 解析数据
     * @param content
     */
    private void processData(String content) {
        result = JSON.parseObject(content, TypeBean.class).getResult();
    }

    /**
     * 初始化ListView的监听
     * @param typeLeftAdapter
     */
    private void initListener(final TypeLeftAdapter typeLeftAdapter) {
        //点击监听
        mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                typeLeftAdapter.changeSelected(i);//刷新
                getDataFromNet(urls[i],i);
                mTypeLeftAdapter.notifyDataSetChanged();
            }
        });

        //选中监听
        mLvLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeLeftAdapter.changeSelected(i);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tag;
    }

}
