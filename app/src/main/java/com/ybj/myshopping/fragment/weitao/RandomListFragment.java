package com.ybj.myshopping.fragment.weitao;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.TagBean;
import com.ybj.myshopping.common.BaseFragment;
import com.ybj.myshopping.ui.randomLayout.StellarMap;
import com.ybj.myshopping.utils.Constants;
import com.ybj.myshopping.utils.ToastUtil;
import com.ybj.myshopping.utils.UIUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by 杨阳洋 on 2017/2/11.
 * usg:随机布局,采用github上现成的代码对立面的功能进行抽取
 */

public class RandomListFragment extends BaseFragment {
    @BindView(R.id.sm_random)
    StellarMap mSmRandom;

    //随机数
    private Random mRandom;

    private List<TagBean.ResultBean> result;

    //随机布局三页的数据
    private List<TagBean.ResultBean> firDatas;
    private List<TagBean.ResultBean> senDatas;
    private List<TagBean.ResultBean> thirdData;

    @Override
    protected String getUrl() {
        return Constants.TAG_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);

        //设置每一页的数据
        firDatas = result.subList(0,result.size() / 3);
        senDatas = result.subList(result.size() / 3 , result.size() * 2 / 3);
        thirdData = result.subList(result.size() * 2 / 3, result.size());

        initStellarMap();
    }

    /**
     * 初始化随机布局的信息
     */
    private void initStellarMap() {

        mSmRandom.setAdapter(new RandomAdapter());
        //随机控件分布网格空间大小
        mSmRandom.setRegularity(11, 11);
        //设置动画效果
        mSmRandom.setGroup(0, true);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_random;
    }

    /**
     * 解析数据
     * @param json
     */
    private void processData(String json){
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
    }

    private class RandomAdapter implements StellarMap.Adapter {
        // 返回显示的组数
        @Override
        public int getGroupCount() {
            return 3;
        }

        // 返回数量
        @Override
        public int getCount(int group) {
            switch (group) {
                case 0:
                    return firDatas.size();
                case 1:
                    return senDatas.size();
                case 2:
                    return thirdData.size();
            }
            return firDatas.size();
        }

        @Override
        public View getView(int group, int position, View convertView) {
            mRandom = new Random();

            final TextView textView = new TextView(getActivity());

            switch (group) {
                case 0:
                    textView.setText(firDatas.get(position).getName());
                    break;
                case 1:
                    textView.setText(senDatas.get(position).getName());
                    break;
                case 2:
                    textView.setText(thirdData.get(position).getName());
                    break;
            }

            // 随机的三色
            int red = mRandom.nextInt(200);
            int green = mRandom.nextInt(200);
            int blue = mRandom.nextInt(200);
            textView.setTextColor(Color.rgb(red, green, blue));

            textView.setTextSize(mRandom.nextInt(UIUtils.dp2px(10)) + UIUtils.dp2px(5));

            //点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(getActivity(), textView.getText().toString());
                }
            });

            return textView;
        }

        /**
         * 摇晃进入效果
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        /**
         * 下一组进入动画
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0) {
                return 1;
            } else if (group == 1) {
                return 2;
            } else if (group == 2) {
                return 0;
            }
            return 0;
        }
    }
}
