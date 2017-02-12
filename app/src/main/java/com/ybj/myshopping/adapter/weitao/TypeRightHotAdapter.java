package com.ybj.myshopping.adapter.weitao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.TypeBean;
import com.ybj.myshopping.utils.Constants;

import java.util.List;

/**
 * Created by 杨阳洋 on 2017/2/11.
 */

public class TypeRightHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<TypeBean.ResultBean.HotProductListBean> mHotProductListBeen;

    public TypeRightHotAdapter(Context context, List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {
        this.mContext = context;
        this.mHotProductListBeen = hot_product_list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_type_hot, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_hot_price.setText("￥" + mHotProductListBeen.get(position).getCover_price());

        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + mHotProductListBeen.get(position).getFigure())
                .placeholder(R.drawable.tupian_bg_tmall)
                .into(viewHolder.iv_hot_img);

        viewHolder.ll_hot_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.setOnItemClickListener(mHotProductListBeen.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHotProductListBeen == null ? 0 : mHotProductListBeen.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hot_price;
        private ImageView iv_hot_img;
        private LinearLayout ll_hot_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hot_price = (TextView) itemView.findViewById(R.id.tv_hot_price);
            iv_hot_img = (ImageView) itemView.findViewById(R.id.iv_hot_img);
            ll_hot_item = (LinearLayout) itemView.findViewById(R.id.ll_hot_item);
        }
    }

    /**
     * 接口用于调用数据
     */
    public interface OnItemClickListener{
        void setOnItemClickListener(TypeBean.ResultBean.HotProductListBean data);
    }

    public void setOnItemClickListener(TypeRightHotAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private TypeRightHotAdapter.OnItemClickListener onItemClickListener;
}
