package com.ybj.myshopping.adapter.home;

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

public class ChannelAdapter extends BaseAdapter {

    private Context mContext;

    private List<ResultBean.ChannelInfoBean> mChannelInfoBeen;

    public ChannelAdapter(Context context, List<ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = context;
        this.mChannelInfoBeen = channel_info;
    }

    @Override
    public int getCount() {
        return mChannelInfoBeen == null ? 0 : mChannelInfoBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return mChannelInfoBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_channel, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ResultBean.ChannelInfoBean channelInfoBean = mChannelInfoBeen.get(i);
        holder.mTvChannel.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + channelInfoBean.getImage())
                .into(holder.mIvChannel);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_channel)
        ImageView mIvChannel;
        @BindView(R.id.tv_channel)
        TextView mTvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
