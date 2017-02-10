package com.ybj.myshopping.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.ybj.myshopping.R;
import com.ybj.myshopping.adapter.home.ChannelAdapter;
import com.ybj.myshopping.bean.ResultBean;
import com.ybj.myshopping.ui.VerticalViewPager;
import com.ybj.myshopping.utils.Constants;
import com.ybj.myshopping.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.ybj.myshopping.R.id.ib_headline;
import static com.ybj.myshopping.R.id.vvp_headline;

/**
 * Created by 杨阳洋 on 2017/2/10.
 */

public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //上下文
    private Context mContext;

    //数据Bean对象
    private ResultBean mResultBean;

    //加载布局
    private LayoutInflater mLayoutInflater;

    /**
     * 五种类型
     */
    // 横幅广告
    public static final int BANNER = 0;
    // 频道
    public static final int CHANNEL = 1;
    // 淘宝头条
    public static final int HEADLINE = 2;
    // 秒杀
    public static final int SECKILL = 4;
    // 推荐
    public static final int RECOMMEND = 5;
    // 热卖
    public static final int HOT = 6;
    //淘宝头条第二种实现方式
    public static final int HEADLINE2 = 3;

    //当前类型
    public int currentType = BANNER;

    //淘宝头条
    private VerticalViewPager mVvpHeadline;

    //秒杀时间文本
    private TextView tvTime;
    private int dt;
    private boolean isFirst = true;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (mVvpHeadline != null) {
                    int item = (mVvpHeadline.getCurrentItem() + 1) % 3;
                    mVvpHeadline.setCurrentItem(item);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            } else if (msg.what == 1) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));

                handler.removeMessages(1);
                handler.sendEmptyMessageDelayed(1, 1000);

                if (dt == 0) {
                    handler.removeMessages(1);
                }
            }
        }
    };

    public HomeRecycleAdapter(Context context, ResultBean resultBean) {
        this.mContext = context;
        this.mResultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 绑定布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_itemm, null));
        } else if (viewType == HEADLINE) {
            return new HeadLineViewHolder(mLayoutInflater.inflate(R.layout.headline_item, null));
        } else if (viewType == HEADLINE2) {
            return new HeadLineViewHolder2(mLayoutInflater.inflate(R.layout.headline2_item, null));
        } else if (viewType == SECKILL) {
            return new SecKillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    /**
     * 加载数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(mResultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(mResultBean.getChannel_info());
        } else if (getItemViewType(position) == HEADLINE) {
            HeadLineViewHolder headLineViewHolder = (HeadLineViewHolder) holder;
        } else if (getItemViewType(position) == HEADLINE2) {
            HeadLineViewHolder2 headLineViewHolder = (HeadLineViewHolder2) holder;
        } else if (getItemViewType(position) == SECKILL) {
            SecKillViewHolder secKillViewHolder = (SecKillViewHolder) holder;
            secKillViewHolder.setData(mResultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(mResultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(mResultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    /**
     * 对应数据类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case HEADLINE:
                currentType = HEADLINE;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
            case HEADLINE2:
                currentType = HEADLINE2;
                break;
        }
        return currentType;
    }

    //广告条
    class BannerViewHolder extends RecyclerView.ViewHolder {

        public Banner mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<ResultBean.BannerInfoBean> banner_info) {
            mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            //设置图片加载器
            mBanner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imageUris.add(Constants.Base_URl_IMAGE + banner_info.get(i).getImage());
            }
            mBanner.setImages(imageUris);
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.Accordion);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"在线课堂", "抱歉，是真的没座了", "开讲啦"};
            //String[] titles = new String[]{"尚硅谷在线课堂震撼发布", "抱歉，是真的没座了", "尚硅谷新学员做客CCTV"};
            mBanner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            mBanner.isAutoPlay(true);
            //设置轮播时间
            mBanner.setDelayTime(2000);
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);
            //banner设置方法全部调用完毕时最后调用
            mBanner.start();
            //banner的点击事件
            mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (position - 1 < banner_info.size()) {
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (position - 1 == 0) {
                            product_id = "627";
                            cover_price = "320.00";
                            name = "尚硅谷在线课堂";
                        } else if (position - 1 == 1) {
                            product_id = "21";
                            cover_price = "800.00";
                            name = "尚硅谷抢座";
                        } else {
                            product_id = "1341";
                            cover_price = "150.00";
                            name = "尚硅谷讲座";
                        }
                        String image = banner_info.get(position - 1).getImage();

                    }
                }
            });
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //Picasso 加载图片简单用法
            //Picasso.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            //Uri uri = Uri.parse((String) path);
            //imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        //@Override
        //public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        //SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        //return simpleDraweeView;
        //}
    }

    //频道
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private GridView gv_channel;

        public ChannelViewHolder(View inflate) {
            super(inflate);
            gv_channel = (GridView) inflate.findViewById(R.id.gv_channel);
        }

        public void setData(List<ResultBean.ChannelInfoBean> channel_info) {
            gv_channel.setAdapter(new ChannelAdapter(mContext, channel_info));

            //单机事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i <= 8) {
                        ToastUtil.showToast(mContext, "加载中");
                    } else {
                        ToastUtil.showToast(mContext, "期待更多!");
                    }
                }
            });
        }
    }

    //淘宝头条
    class HeadLineViewHolder extends RecyclerView.ViewHolder {

        private ImageButton mIbHeadline;

        public HeadLineViewHolder(View inflate) {
            super(inflate);

            mIbHeadline = (ImageButton) itemView.findViewById(ib_headline);
            mVvpHeadline = (VerticalViewPager) itemView.findViewById(vvp_headline);

            mVvpHeadline.setAdapter(new HeadLineAdapter());

            mVvpHeadline.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });

            mIbHeadline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showToast(mContext, "淘宝头条");
                }
            });

            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 3000);


        }

    }

    //淘宝头条
    public class HeadLineAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = View.inflate(mContext, R.layout.headline_content, null);

            TextView tv_headline_1 = (TextView) view.findViewById(R.id.tv_headline_1);
            TextView tv_headline_2 = (TextView) view.findViewById(R.id.tv_headline_2);

            switch (position) {
                case 0:
                    tv_headline_1.setText("美国当选总统特朗普宣布美国将退出TPP");
                    tv_headline_2.setText("暴雪打造全新第一人称新作 能否超越《守望先锋》？");
                    break;
                case 1:
                    tv_headline_1.setText("人工智能重大进展！全球首个光电子神经网络问世");
                    tv_headline_2.setText("中国禁止电视台播放韩国明星代言广告？外交部回应");
                    break;
                case 2:
                    tv_headline_1.setText("美军鹰派认为中美或随时开战：南海只是开胃菜");
                    tv_headline_2.setText("专家：中国需紧盯美俄关系新动向 要做及时改变");
                    break;
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "淘宝头条");
                }
            });

            container.addView(view);

            return view;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //淘宝头条第二种实现方式
    class HeadLineViewHolder2 extends RecyclerView.ViewHolder {

        private ViewFlipper vf;

        public HeadLineViewHolder2(View itemView) {
            super(itemView);

            vf = (ViewFlipper) itemView.findViewById(R.id.vf);
            Log.e("TAG", vf + "");
            vf.addView(View.inflate(mContext, R.layout.view_advertisement01, null));
            vf.addView(View.inflate(mContext, R.layout.view_advertisement02, null));
            vf.addView(View.inflate(mContext, R.layout.view_advertisement03, null));
        }

    }

    //秒杀
    class SecKillViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_seckill_more;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter mAdapter;

        public SecKillViewHolder(View inflate) {
            super(inflate);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            ll_seckill_more = (LinearLayout) itemView.findViewById(R.id.ll_seckill_more);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
        }

        public void setData(ResultBean.SeckillInfoBean seckill_info) {

            //设置秒杀时间
            if (isFirst) {
                dt = (int) (Integer.parseInt(seckill_info.getEnd_time()) - System.currentTimeMillis());
                isFirst = false;
            }

            //设置RecyclerView
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //设置适配器
            mAdapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);
            rv_seckill.setAdapter(mAdapter);

            //倒计时
            handler.sendEmptyMessageDelayed(1, 1000);

            //点击事件
            ll_seckill_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showToast(mContext, "查看更多");
                }
            });
        }
    }

    //推荐
    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_recommend_more;
        private GridView gv_recommend;

        public RecommendViewHolder(View inflate) {
            super(inflate);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            ll_recommend_more = (LinearLayout) itemView.findViewById(R.id.ll_recommend_more);
        }

        public void setData(List<ResultBean.RecommendInfoBean> recommend_info) {
            gv_recommend.setAdapter(new RecommendGridViewAdapter(mContext, recommend_info));

            //设置每一项的点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

            // 更多的单击事件
            ll_recommend_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "查看更多~");
                }
            });
        }
    }

    //热卖
    private class HotViewHolder extends RecyclerView.ViewHolder {

        private GridView gv_hot;
        private LinearLayout ll_hot_more;

        public HotViewHolder(View inflate) {
            super(inflate);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            ll_hot_more = (LinearLayout) itemView.findViewById(R.id.ll_hot_more);
        }

        public void setData(List<ResultBean.HotInfoBean> hot_info) {
            gv_hot.setAdapter(new HotGridViewAdapter(mContext, hot_info));

            // 设置每项的单击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                    String cover_price = data.get(position).getCover_price();
//                    String name = data.get(position).getName();
//                    String figure = data.get(position).getFigure();
//                    String product_id = data.get(position).getProduct_id();
//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
//                    Intent intent = new Intent(mContext, ProductDetailsActivity.class);
//                    intent.putExtra(GOODS_BEAN, goodsBean);
//                    mContext.startActivity(intent);
                }
            });

            // 更多的单击事件
            ll_hot_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "查看更多~");
                }
            });
        }
    }
}
