package cn.ucai.welfarecentre.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.ucai.welfarecentre.Model.utils.OkImageLoader;
import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class SlideAutoLoopView extends ViewPager {//自定义VIewPager，开始运行了AttributeSet attrs
    private static int ACTION_CHANGE_ITEM=0;
    GoodsDetailsAdapter mAdapter;
    Context mContext;
    Timer mTimer;

    Handler mHandler;
    FlowIndicator mFlowIndicator;
    public SlideAutoLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initHanlder();
        setListener();
    }

    public SlideAutoLoopView(Context mContext) {
        super(mContext);
    }

    private void setListener() {
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFlowIndicator.setmFocus(position%mFlowIndicator.getmCount());//通过这里进行回调
            }
            //            onPageScrollStateChanged页面发生改变时
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initHanlder() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == ACTION_CHANGE_ITEM) {
//                   setCurrentItem 它的意思是跳转到ViewPager的指定页面
//                  getCurrentItem 它的意思是得到当前页面
                    setCurrentItem(getCurrentItem()+1);
//                    SlideAutoLoopView.this.setCurrentItem();
//                    在内部类中，this不起作用，会报错，new ViewPager(mContext).setCurrentItem不会，不过没必要；
                }
            }
        };
    }

    public void startPlay(ArrayList<String> goodsUrlList, FlowIndicator flowIndicator) {
        this.mFlowIndicator = flowIndicator;
        mFlowIndicator.setmCount(goodsUrlList.size());
        mAdapter = new GoodsDetailsAdapter(mContext, goodsUrlList);
        this.setAdapter(mAdapter);//设置图片

        MyScroller scroller = new MyScroller(mContext);
        scroller.setDuration(1500);//设置滑动时间1.5秒，不设置就没有效果
        //通过反射，获取ViewPager.mScroller属性
        Field field = null;
        try {
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);//设置为可访问
            field.set(this, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (mTimer == null) {
            //自动播放
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //向主线程发送翻页的消息
                mHandler.sendEmptyMessage(ACTION_CHANGE_ITEM);
            }
        }, 0, 2000);
    }

    public void stopPlay() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer=null;
        }
    }

    class GoodsDetailsAdapter extends PagerAdapter {
        Context context;
        ArrayList<String> goodsUrlList;

        public GoodsDetailsAdapter(Context context, ArrayList<String> goodsUrlList) {
            this.context = context;
            this.goodsUrlList = goodsUrlList;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            container.addView(imageView);
            OkImageLoader.build(goodsUrlList.get(position%goodsUrlList.size()))//计算
                    .width(160)
                    .height(400)
                    .imageView(imageView)
                    .defaultPicture(R.drawable.nopic)
                    .showImage(context);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView imageView= (ImageView) object;
            container.removeView(imageView);
        }
    }

    class MyScroller extends Scroller {
        int duration;//控制滑动时间

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public MyScroller(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }


}