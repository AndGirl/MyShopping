package com.ybj.myshopping.adapter.found;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.ybj.myshopping.R;
import com.ybj.myshopping.bean.NewPostBean;
import com.ybj.myshopping.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ybj.myshopping.R.id.danmakuView;

/**
 * Created by 杨阳洋 on 2017/2/12.
 * usg:新帖的适配器
 */

public class NewPostListViewAdapter extends BaseAdapter {

    @BindView(danmakuView)
    DanmakuView mDanmakuView;
    private Context mContext;
    private List<NewPostBean.ResultBean> mResultBeen;

    // 弹幕内容集合
    private List<String> comment_list;


    public NewPostListViewAdapter(Context context, List<NewPostBean.ResultBean> data) {
        this.mContext = context;
        this.mResultBeen = data;
    }

    @Override
    public int getCount() {
        return mResultBeen == null ? 0 : mResultBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return mResultBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder mHolder;

        if (view == null) {
            view = View.inflate(mContext, R.layout.item_listview_newpost, null);
            mHolder = new ViewHolder(view);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        NewPostBean.ResultBean resultBean = mResultBeen.get(i);
        //设置名字
        mHolder.mTvCommunityUsername.setText(resultBean.getUsername());
        //设置图片
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getFigure())
                .into(mHolder.mIvCommunityFigure);
        //设置圆形头像
        Glide.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getAvatar())
                .into(mHolder.mIbNewPostAvatar);

        //设置内容
        mHolder.mTvCommunitySaying.setText(resultBean.getSaying());
        mHolder.mTvCommunityLikes.setText(resultBean.getLikes());
        mHolder.mTvCommunityComments.setText(resultBean.getComments());

        //设置弹幕
        comment_list = (List<String>) resultBean.getComment_list();
        if (comment_list != null && comment_list.size() > 0) {
            mHolder.mDanmakuView.setVisibility(View.VISIBLE);

            List<IDanmakuItem> list = new ArrayList<>();
            for (int position = 0; position < comment_list.size(); position++) {
                IDanmakuItem item = new DanmakuItem(mContext, comment_list.get(position), mHolder.mDanmakuView.getWidth());
                list.add(item);
            }
            Collections.shuffle(comment_list);
            mHolder.mDanmakuView.addItem(list, true);
            mHolder.mDanmakuView.show();
        } else {
            mHolder.mDanmakuView.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_community_username)
        TextView mTvCommunityUsername;
        @BindView(R.id.tv_community_addtime)
        TextView mTvCommunityAddtime;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.iv_community_figure)
        ImageView mIvCommunityFigure;
        @BindView(R.id.danmakuView)
        DanmakuView mDanmakuView;
        @BindView(R.id.tv_community_saying)
        TextView mTvCommunitySaying;
        @BindView(R.id.tv_community_likes)
        TextView mTvCommunityLikes;
        @BindView(R.id.tv_community_comments)
        TextView mTvCommunityComments;
        @BindView(R.id.ib_new_post_avatar)
        CircleImageView mIbNewPostAvatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
