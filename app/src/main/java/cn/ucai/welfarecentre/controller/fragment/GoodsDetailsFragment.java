package cn.ucai.welfarecentre.controller.fragment;


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
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
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
        int good_id = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, 0);
        model.downGoodsDetails(getActivity(), good_id, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                } else {
                    MFGT.finish((AppCompatActivity) getContext());
                }
//                ImageLoader.downloadImg(getActivity(),image,goods_thumb);//下载图片
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
        mwebView.loadDataWithBaseURL(null, goods.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
//        ImageLoader.downloadImg(getActivity(), image, goods.getGoodsThumb());//下载图片
        GoodsDetails.startPlay(mGoodsList,flowIndicator);
        //            webView.loadDataWithBaseURL——针对本地的数据图片加载，
//        (String baseUrl, String data, String mimeType, String encoding

    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
//        强转只能大——小，FragmentActivity>AppCompatActivity>MainActivity
//       MFGT.startAcitivity((AppCompatActivity) getActivity(), MainActivity.class);
        MFGT.finish((AppCompatActivity) getActivity());
    }
}
