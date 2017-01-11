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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.adapter.GoodsAdapter;

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
    int pageId = 1;//默认是0
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
        initData();//下载数据
        return view;
    }

    private void initData() {
//        ddwnData下载
        model.downData(getContext(), I.CAT_ID, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                L.i(TAG,"List.size =  "+ list.size());
                mAdapter.initData(list);
            }

            @Override
            public void onError(String error) {
                L.i(TAG,"error = "+ error);
            }
        });
    }

    private void initView() {
        tvRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        rvContact.setHasFixedSize(true);//自动，保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数.
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(getActivity(),mList);
        rvContact.setAdapter(mAdapter);

        mlayout = new GridLayoutManager(getActivity(), I.COLUM_NUM);//每行显示2个
        rvContact.setLayoutManager(mlayout);
    }
}
