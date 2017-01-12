package cn.ucai.welfarecentre.controller.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.adapter.BoutiqueAdapter;
import cn.ucai.welfarecentre.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    private final static String TAG = NewGoodsFragment.class.getSimpleName();//源代码中的基础类的简单名称

    @BindView(R.id.mRefreshHint)
    TextView mRefreshHint;
    @BindView(R.id.mrvBoutique)
    RecyclerView mrvBoutique;
    @BindView(R.id.mRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;

    GridLayoutManager mlayout;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;
    ModelNewGoods model;

    final static int ACTION_LOADING = 0;
    final static int ACTION_UP = 1;//上拉加载
    final static int ACTION_DOWN = 2;//下拉刷新
    int mNewState;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelNewGoods();
        initData(ACTION_LOADING);//下载数据
        setListener();
        return view;
    }

    private void setListener() {
        setRefreshDown();
    }

    private void setRefreshDown() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);//转动东西开始
                mRefreshLayout.setEnabled(true);
                mRefreshHint.setVisibility(View.VISIBLE);
                initData(ACTION_DOWN);
            }
        });
    }

    private void initData(final int action) {
        model.downBoutique(getActivity(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mAdapter.setMore(result !=null && result.length>0);//不到最后全为true，
                if (!mAdapter.isMore()){//最后一页
                    if (action == ACTION_UP){
                        mAdapter.setFooterText("没有数据可加载了");
                    }
                    return;
                }
                    mAdapter.setFooterText("加载数据");
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    L.i(TAG,"List.size =  "+ list.size());
                    switch (action){
                        case ACTION_LOADING://第一次加载
                            mAdapter.initData(list);
                            break;
                        case ACTION_UP://上拉加载
                            mAdapter.addinitData(list);
                            break;
                        case ACTION_DOWN://下拉刷新
                            mAdapter.initData(list);
                            mRefreshHint.setVisibility(View.GONE);
                            mRefreshLayout.setRefreshing(false);//不可见
                            ImageLoader.release();
                            break;
                    }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        mrvBoutique.setHasFixedSize(true);//自动，保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数.
        mList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(getActivity(),mList);
        mrvBoutique.setAdapter(mAdapter);
        mrvBoutique.addItemDecoration(new SpaceItemDecoration(40));
        mlayout = new GridLayoutManager(getActivity(), 1);//每行显示2个
        mrvBoutique.setLayoutManager(mlayout);
    }

}
