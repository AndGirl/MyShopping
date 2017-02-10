package com.ybj.myshopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.ResultBean;
import com.ybj.myshopping.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 杨阳洋 on 2017/2/10.
 */

public class RecommendGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResultBean.RecommendInfoBean> data;

    public RecommendGridViewAdapter(Context context, List<ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = context;
        this.data = recommend_info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        ResultBean.RecommendInfoBean recommendInfoBean = data.get(i);
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + recommendInfoBean.getFigure())
                .into(holder.mIvRecommend);
        holder.mTvName.setText(recommendInfoBean.getName());
        holder.mTvPrice.setText("￥" + recommendInfoBean.getCover_price());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_recommend)
        ImageView mIvRecommend;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_price)
        TextView mTvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
