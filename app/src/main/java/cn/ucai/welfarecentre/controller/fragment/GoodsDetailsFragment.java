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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.GoodsDetailsBean;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailsFragment extends Fragment {


    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.mwebView)
    WebView tv3;
    ModelNewGoods model;
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
        int good_id = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,0);
        model.downGoodsDetails(getActivity(),good_id,new OnCompleteListener<GoodsDetailsBean>(){
            @Override
            public void onSuccess(GoodsDetailsBean result) {
              if (result != null){
                  showGoodsDetail(result);
              }else{
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
        ImageLoader.downloadImg(getActivity(),image,goods.getGoodsThumb());//下载图片
        tv3.loadDataWithBaseURL(null,goods.getGoodsBrief(), I.TEXT_HTML,I.UTF_8,null);
    }

    @OnClick(R.id.backClickArea)
    public void onClick() {

    }
}
