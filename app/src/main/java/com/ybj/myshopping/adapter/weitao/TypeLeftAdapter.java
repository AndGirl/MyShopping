package com.ybj.myshopping.adapter.weitao;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ybj.myshopping.R;

/**
 * Created by 杨阳洋 on 2017/2/11.
 */

public class TypeLeftAdapter extends BaseAdapter {

    private Context mContext;
    private int mSelect = 0;//选中项
    private String[] titles = new String[]{"小裙子", "上衣", "下装", "外套", "配件", "包包", "装扮", "居家宅品", "办公文具", "数码周边", "游戏专区"};

    public TypeLeftAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = View.inflate(mContext, R.layout.item_type, null);
            holder =   new ViewHolder();

            holder.mTvTitle = (TextView) view.findViewById(R.id.tv_title);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.mTvTitle.setText(titles[i]);

        if(mSelect == i) {
            view.setBackgroundResource(R.drawable.type_item_background_selector);//其它项背景色
            holder.mTvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        }else{
            view.setBackgroundResource(R.drawable.bg2);//其它项背景色
            holder.mTvTitle.setTextColor(Color.parseColor("#323437"));
        }

        return view;
    }

    /**
     * 刷新方法
     * @param position
     */
    public void changeSelected(int position){
        if(position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        private TextView mTvTitle;
    }
}
