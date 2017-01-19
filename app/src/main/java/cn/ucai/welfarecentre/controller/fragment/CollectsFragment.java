package cn.ucai.welfarecentre.controller.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.CollectBean;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.net.IModelNewGoods;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.controller.adapter.CollectAdapter;
import cn.ucai.welfarecentre.view.MFGT;
import cn.ucai.welfarecentre.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectsFragment extends Fragment {

    private final static String TAG = NewGoodsFragment.class.getSimpleName();//源代码中的基础类的简单名称
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rvContact)
    RecyclerView rvContact;
    @BindView(R.id.tvRefreshLayout)
    SwipeRefreshLayout tvRefreshLayout;
    IModelNewGoods model;
    User user;
    int pageId = 1;

    GridLayoutManager mlayout;
    CollectAdapter mAdapter;
    ArrayList<CollectBean> mList;

    final static int ACTION_LOADING = 0;
    final static int ACTION_UP = 1;//上拉加载
    final static int ACTION_DOWN = 2;//下拉刷新
    int mNewState;
    @BindView(R.id.line7)
    LinearLayout line7;

    public CollectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collects, container, false);
        ButterKnife.bind(this, view);
        model = new ModelNewGoods();
        tvCommonTitle.setText("收藏的宝贝");
        line7.setBackgroundColor(Color.YELLOW);
        user = FuLiCentreApplication.getUser();
        initView();
        initData(ACTION_LOADING);
        setListener();
        return view;
    }

    private void setListener() {
        mlayoutSpanSizeLookup();//解决最后页脚不能在中心的原因
        setPulldown();
        setRefreshDown();
    }

    private void mlayoutSpanSizeLookup() {
        mlayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mAdapter.getItemCount() - 1) {
                    return 2;//跨两列
                } else {
                    return 1;
                }
            }
        });
    }

    private void setPulldown() {
        rvContact.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewState = newState;
                int lastPosition = mlayout.findLastVisibleItemPosition();
                if (lastPosition >= mAdapter.getItemCount() - 1 && mNewState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()) {//只有lastPosition>= mAdapter.getItemCount-1时，才会加载，
                    pageId++;
                    initData(ACTION_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);//解决停止的数据
  /*              int firstPostion = mlayout.findFirstVisibleItemPosition();//可见的第一行，判断是否为第一行，
                tvRefreshLayout.setEnabled(firstPostion == 0);//来判断*/
            }
        });
    }

    private void setRefreshDown() {
        tvRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvRefreshLayout.setRefreshing(true);//转动东西开始
                tvRefreshLayout.setEnabled(true);
                tvRefreshHint.setVisibility(View.VISIBLE);
                pageId = 1;
                initData(ACTION_DOWN);
            }
        });
    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish((AppCompatActivity) getActivity());
    }

    private void initData(final int action) {//收藏的宝贝，
        if (user != null) {
            model.getcollects(getActivity(), user.getMuserName(), pageId, new OnCompleteListener<CollectBean[]>() {
                @Override
                public void onSuccess(CollectBean[] result) {
                    if (result != null) {//说明成功了
                        if (!mAdapter.isMore()) {//最后一页
                            if (action == ACTION_UP) {
                                mAdapter.setFooterText("没有数据可加载了");
                            }
                            return;
                        }
                        mAdapter.setFooterText("加载数据");
                        ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                        L.i(TAG, "List.size =  " + list.size());
                        switch (action) {
                            case ACTION_LOADING://第一次加载
                                mAdapter.initData(list);
                                break;
                            case ACTION_UP://上拉加载
                                mAdapter.addinitData(list);
                                break;
                            case ACTION_DOWN://下拉刷新
                                mAdapter.initData(list);
                                tvRefreshHint.setVisibility(View.GONE);
                                tvRefreshLayout.setRefreshing(false);//不可见
                                ImageLoader.release();
                                break;
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    L.i(TAG, "error = " + error);
                }
            });

        } else {
            //            大转小可以，小转大不可以
            MFGT.finish((AppCompatActivity) getActivity());
        }
    }

    private void initView() {
        tvRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        rvContact.setHasFixedSize(true);//自动，保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数.
        mList = new ArrayList<>();
        mAdapter = new CollectAdapter(getActivity(), mList);
        rvContact.setAdapter(mAdapter);
        rvContact.addItemDecoration(new SpaceItemDecoration(40));//每行间隔
        mlayout = new GridLayoutManager(getActivity(), I.COLUM_NUM);//每行显示2个
        rvContact.setLayoutManager(mlayout);
    }
}
