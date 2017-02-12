package com.ybj.myshopping.adapter.found;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.HotPostBean;
import com.ybj.myshopping.utils.Constants;
import com.ybj.myshopping.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 杨阳洋 on 2017/2/12.
 */

public class HotPostListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotPostBean.ResultBean> mResultBeen;

    public HotPostListViewAdapter(Context context, List<HotPostBean.ResultBean> result) {
        this.mContext = context;
        this.mResultBeen = result;
    }

    @Override
    public int getCount() {
        return mResultBeen == null ? 0 : mResultBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mResultBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_hotpost_listview, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        //设置数据
        HotPostBean.ResultBean resultBean = mResultBeen.get(i);

        //设置名字
        holder.tvHotUsername.setText(resultBean.getUsername());

        //设置时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-DD HH:mm");
        holder.tvHotAddtime.setText(simpleDateFormat.format(Integer.parseInt(resultBean.getAdd_time())));

        // 设置图片
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getFigure())
                .into(holder.ivHotFigure);

        // 设置圆形头像
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getAvatar())
                .into(holder.ibNewPostAvatar);

        //设置内容
        holder.tvHotSaying.setText(resultBean.getSaying());
        holder.tvHotLikes.setText(resultBean.getLikes());
        holder.tvHotComments.setText(resultBean.getComments());

        // 设置类型
        String is_top = resultBean.getIs_top();
        if ("1".equals(is_top)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mContext);
            textView.setText("置顶");
            textViewLp.setMargins(UIUtils.dp2px(10), 0, UIUtils.dp2px(5), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.is_top_shape);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            holder.llHotPost.removeAllViews();
            holder.llHotPost.addView(textView, textViewLp);
        }
        String is_hot = resultBean.getIs_hot();
        if ("1".equals(is_hot)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mContext);
            textViewLp.setMargins(0, 0, UIUtils.dp2px(5), 0);
            textView.setText("热门");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            textView.setBackgroundResource(R.drawable.is_hot_shape);
            holder.llHotPost.addView(textView, textViewLp);
        }
        String is_essence = resultBean.getIs_essence();
        if ("1".equals(is_essence)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLp.setMargins(0, 0, UIUtils.dp2px(5), 0);
            TextView textView = new TextView(mContext);
            textView.setText("精华");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            textView.setBackgroundResource(R.drawable.is_essence_shape);
            holder.llHotPost.addView(textView, textViewLp);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_hot_username)
        TextView tvHotUsername;
        @BindView(R.id.tv_hot_addtime)
        TextView tvHotAddtime;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.iv_hot_figure)
        ImageView ivHotFigure;
        @BindView(R.id.ll_hot_post)
        LinearLayout llHotPost;
        @BindView(R.id.tv_hot_saying)
        TextView tvHotSaying;
        @BindView(R.id.tv_hot_likes)
        TextView tvHotLikes;
        @BindView(R.id.tv_hot_comments)
        TextView tvHotComments;
        @BindView(R.id.ib_new_post_avatar)
        CircleImageView ibNewPostAvatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
