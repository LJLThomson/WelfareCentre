package cn.ucai.welfarecentre.controller.fragment;


import android.content.Intent;
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
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
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
    boolean isCollect;

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
        initData();
        return view;
    }

    private void initData() {
        good_id = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, 0);
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
    public void onCollect() {
        User user = FuLiCentreApplication.getUser();
        setCollect(user);
        initCollectStatus(user);
        MFGT.gotoLoginActivity(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        setCollectStatus();
    }

    private void initCollectStatus(User user) {
       String username= user.getMuserName();
        if (user != null) {
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
        if (isCollect) {
            img2.setImageResource(R.mipmap.bg_collect_out);
        } else {
            img2.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    public void setCollect(User user) {
        model.setCollect(getActivity(), good_id, user.getMuserName(), isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_DELETE_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result !=null &&result.isSuccess()){
                            isCollect = !isCollect;
                            CommonUtils.showLongToast(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

}
