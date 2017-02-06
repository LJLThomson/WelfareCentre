package cn.ucai.welfarecentre.controller.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.adapter.GoodsAdapter;
import cn.ucai.welfarecentre.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    private final static String TAG = NewGoodsFragment.class.getSimpleName();//源代码中的基础类的简单名称

    @BindView(R.id.tvRefreshHint)
    TextView tvRefreshHint;
    @BindView(R.id.rvContact)
    RecyclerView rvContact;
    @BindView(R.id.tvRefreshLayout)
    SwipeRefreshLayout tvRefreshLayout;

    GridLayoutManager mlayout;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    ModelNewGoods model;

    int pageId = 1;//默认是0，页面pageSize默认是10页，也可以根据自行调整
    final static int ACTION_LOADING = 0;
    final static int ACTION_UP = 1;//上拉加载
    final static int ACTION_DOWN = 2;//下拉刷新
    int mNewState;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelNewGoods();
        initData(ACTION_LOADING);//下载数据
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

    private void initData(final int action) {
//        downData下载 ,I.CAT_ID 默认为0
        int cartId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        model.downData(getContext(), cartId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mAdapter.setMore(result != null && result.length > 0);//不到最后全为true，
                if (!mAdapter.isMore()) {//最后一页，前面都是true，最后一个为false;
                    if (action == ACTION_UP) {
                        mAdapter.setFooterText("没有数据可加载了");
                    }
                    return;
                }
                mAdapter.setFooterText("加载数据");
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
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

            @Override
            public void onError(String error) {
                L.i(TAG, "error = " + error);
            }
        });
    }

    private void initView() {
        tvRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        rvContact.setHasFixedSize(true);//自动，保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数.
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(getActivity(), mList);
        rvContact.setAdapter(mAdapter);
        rvContact.addItemDecoration(new SpaceItemDecoration(40));
        mlayout = new GridLayoutManager(getActivity(), I.COLUM_NUM);//每行显示2个
        rvContact.setLayoutManager(mlayout);
    }

    public void SortGoods(final int sortBy) {
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean firstBean, NewGoodsBean secondBean) {
                int result = 0;
                switch (sortBy) {
                    case I.SORT_BY_ADDTIME_ASC://从低到高
                        result = (int) (Long.parseLong(secondBean.getAddTime()) - Long.parseLong(firstBean.getAddTime()));
                        break;
                    case I.SORT_BY_ADDTIME_DESC://从高到低
                        result = (int) (Long.parseLong(firstBean.getAddTime()) - Long.parseLong(secondBean.getAddTime()));
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result = getPrice(secondBean.getCurrencyPrice()) - getPrice(firstBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result = getPrice(firstBean.getCurrencyPrice()) - getPrice(secondBean.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    //
    public int getPrice(String price) {
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥") + 1));
        L.e("adapter","p="+ p);
        return p;
    }
}
