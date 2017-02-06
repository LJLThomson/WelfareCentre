package cn.ucai.welfarecentre.controller.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.bean.GoodsDetailsBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.CommonUtils;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.controller.activity.OrderActivity;
import cn.ucai.welfarecentre.controller.adapter.CartAdapter;
import cn.ucai.welfarecentre.controller.adapter.GoodsAdapter;
import cn.ucai.welfarecentre.view.MFGT;
import cn.ucai.welfarecentre.view.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private final static String TAG = NewGoodsFragment.class.getSimpleName();//源代码中的基础类的简单名称

    @BindView(R.id.tv_cart_sum_price)
    TextView tvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView tvCartSavePrice;
    @BindView(R.id.tv_cart_buy)
    TextView tvCartBuy;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    GridLayoutManager mlayout;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    ModelNewGoods model;

    final static int ACTION_LOADING = 0;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelNewGoods();
        initData(ACTION_LOADING);//下载数据
        return view;
    }


    private void initData(final int action) {
//        downData下载 ,I.CAT_ID 默认为0
        int cartId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        User user = FuLiCentreApplication.getUser();
        if (user != null) {
            model.getCart(getActivity(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
            /*        ArrayList<NewGoodsBean> arrayList = new ArrayList<NewGoodsBean>();
                    for (int i = 0; i < list.size(); i++) {
                        arrayList.add(list.get(i).getGoods());
                        Log.i("main", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mList.add(list.get(i).getGoods()));
                    }*/
                    switch (action) {
                        case ACTION_LOADING://第一次加载
                            mAdapter.initData(list);
                            break;
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void initView() {
        srl.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        rv.setHasFixedSize(true);//自动，保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数.
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(getActivity(), mList);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(40));
        mlayout = new GridLayoutManager(getActivity(), 1);//每行显示1个
        rv.setLayoutManager(mlayout);
    }

    @OnClick(R.id.tv_nothing)
    public void onClick() {//数据没下载时，点击重新加载
        initData(ACTION_LOADING);//下载数据
    }

    @OnClick(R.id.tv_cart_buy)
    public void buygoods(){
        if (sumPrice==0){
            Toast.makeText(getActivity(), "你尚未勾选", Toast.LENGTH_SHORT).show();
            return;
        }
        MFGT.gotoOrderActivity(getActivity(),sumPrice);
    }


    int sumPrice = 0;
    private void setPrice() {
        int savaPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean cart : mList) {
                NewGoodsBean goods = cart.getGoods();
                if (cart.isChecked() && goods != null) {
                    sumPrice += cart.getCount() * getPrice(goods.getCurrencyPrice());
//                    当前价格减去打折价——所谓的节省
                    savaPrice += cart.getCount() * (getPrice(goods.getCurrencyPrice()) - getPrice(goods.getRankPrice()));
                }
            }
        }
        tvCartSumPrice.setText("合计：￥" + sumPrice);
        tvCartSavePrice.setText("节省：￥" + savaPrice);
    }

    int getPrice(String price) {
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥") + 1));
        return p;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 广播接受者注册
        UpdateCartReceiver receiver = new UpdateCartReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(I.BROADCAST_UPDATA_CART);
        getActivity().registerReceiver(receiver, filter);
    }

    class UpdateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG, "onReceive");
            setPrice();
        }
    }
}
