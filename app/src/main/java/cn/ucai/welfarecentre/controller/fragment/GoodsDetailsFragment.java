package cn.ucai.welfarecentre.controller.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.GoodsDetailsBean;
import cn.ucai.welfarecentre.Model.bean.MessageBean;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.CommonUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.view.FlowIndicator;
import cn.ucai.welfarecentre.view.MFGT;
import cn.ucai.welfarecentre.view.SlideAutoLoopView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailsFragment extends Fragment {


    ModelNewGoods model;
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.GoodsDetails)
    SlideAutoLoopView GoodsDetails;
    /*    @BindView(R.id.image)
        ImageView image;*/
    @BindView(R.id.flowIndicator)
    FlowIndicator flowIndicator;
    @BindView(R.id.mwebView)
    WebView mwebView;
    int good_id;
    boolean isCollect = false;
    int Collectcount;
    @BindView(R.id.imgCart)
    ImageView imgCart;
    boolean isCart = false;
    public GoodsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_details, container, false);
        ButterKnife.bind(this, view);
        model = new ModelNewGoods();
        good_id = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, 0);
        initData();
        return view;
    }

    private void initData() {
        model.downGoodsDetails(getActivity(), good_id, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                } else {
                    MFGT.finish((AppCompatActivity) getContext());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    //展示GoodsDetails
    private void showGoodsDetail(GoodsDetailsBean goods) {
        tv1.setText(goods.getGoodsName());
        tv2.setText(goods.getCurrencyPrice());
        ArrayList<String> mGoodsList = new ArrayList<>();
        mGoodsList.add(goods.getGoodsThumb());
        mGoodsList.add(goods.getGoodsImg());
//        webView可以下载本地的图片和data
        mwebView.loadDataWithBaseURL(null, goods.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
//        ImageLoader.downloadImg(getActivity(), image, goods.getGoodsThumb());//下载图片
        GoodsDetails.startPlay(mGoodsList, flowIndicator);
        //            webView.loadDataWithBaseURL——针对本地的数据图片加载，
//        (String baseUrl, String data, String mimeType, String encoding

    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
//        强转只能大——小，FragmentActivity>AppCompatActivity>MainActivity
//       MFGT.startAcitivity((AppCompatActivity) getActivity(), MainActivity.class);
        MFGT.finish((AppCompatActivity) getActivity());
    }

    @OnClick(R.id.img2)
    public void onCollect() {//收藏
        User user = FuLiCentreApplication.getUser();
        setCollect(user);
        getCollectCount(user);//
        if (user == null) {//如果user等于null，说明没有登录，转到登录界面
            MFGT.gotoLoginActivity(getActivity());
        }
    }
    @OnClick(R.id.imgCart)
    public void addCart(){//购物车
        User user = FuLiCentreApplication.getUser();
        if(user == null){//没有登录，则跳到登录界面
            MFGT.gotoLoginActivity(getActivity());
        }else{
            model.addCart(getActivity(), good_id, user.getMuserName(), 1, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result!=null && result.isSuccess()){//添加成功
                        isCart = true;
                    }else{
                        isCart = false;
                    }
                    setCartStatus();
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void setCartStatus() {
        if (isCart){
          imgCart.setBackgroundColor(Color.RED);//被点击时，设置为红色，确定为被放入购物车
        }else{
            imgCart.setImageResource(R.mipmap.bg_cart_selected);
        }
    }

    private void getCollectCount(User user) {
        model.getCollectCount(getActivity(), user.getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {//计数成功
                    Collectcount = Integer.parseInt(result.getMsg());
//             发送广播getActivity().sendBroadcast();
                    MFGT.sendtoPersonalActivity(getActivity());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = FuLiCentreApplication.getUser();
        initCollectStatus(user);//用于判断是否被收藏，收藏之后，显示红色,这是从数据库中得来的，这边不会有影响
    }

    private void initCollectStatus(User user) {
        if (user != null) {
            String username = user.getMuserName();
            model.isCollect(getActivity(), good_id, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                }
            });
        }
    }

    private void setCollectStatus() {
        if (isCollect) {//红色
            img2.setImageResource(R.mipmap.bg_collect_out);
        } else {//默认为白色
            img2.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    public void setCollect(User user) {
//        收藏，再次点击，取反，true取消收藏，false添加收藏
        model.setCollect(getActivity(), good_id, user.getMuserName(), isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
//                        判断是否得到数据，
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;//取反，
                            CommonUtils.showLongToast(result.getMsg());
                            setCollectStatus();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error + "有错误");
                    }
                });
    }

}
