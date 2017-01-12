package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.bean.GoodsDetailsBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class ModelNewGoods implements IModelNewGoods {
    @Override
    public void downData(Context context, int cartId, int pageId, OnCompleteListener listener) {
//        onCompleteListener为
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);//可以直接得到pageData中的数据
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.CategoryGood.CAT_ID, String.valueOf(cartId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
//                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .addParam(I.PAGE_SIZE,String.valueOf(4))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }

    @Override
    public void downBoutique(Context context, OnCompleteListener listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
//    商品详情
    public void downGoodsDetails(Context context,int good_id,OnCompleteListener listener){
        OkHttpUtils<GoodsDetailsBean>  utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID ,""+good_id)
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }
}
