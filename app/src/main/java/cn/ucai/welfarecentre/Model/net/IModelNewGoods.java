package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.bean.MessageBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelNewGoods {
    void downData(Context context,int cartId,int pageId,OnCompleteListener listener);
    void downBoutique(Context context,OnCompleteListener listener);
    void downGoodsDetails(Context context,int good_id,OnCompleteListener listener);
    void downGroupCategory(Context context,OnCompleteListener listener);
    void downCategory(Context context,int category_id,OnCompleteListener listener);
    void downSecond_Category(Context context,int Second_CategoryId,int page_id,int page_size,OnCompleteListener listener);
    void isCollect(Context context,int goodsId,String username,OnCompleteListener listener);
    void setCollect(Context context,int goodsId,String username,int collect,OnCompleteListener listener);
    void getCollectCount(Context context,String userName,OnCompleteListener listener);
    void getcollects(Context context,String userName,int pageId,OnCompleteListener listener);

    //    购物车
    void addCart(Context context, int goodsId, String userName, int count, OnCompleteListener<MessageBean> listener);
    void getCart(Context context, String userName, OnCompleteListener<CartBean[]> listener);
    void delCart(Context context,int cartId,OnCompleteListener<MessageBean> listener);
    void updateCart(Context context,int cartId,OnCompleteListener<MessageBean> listener);
    void updateCart(Context context,int action,int cartId,String userName,int goodsId,OnCompleteListener<MessageBean> listener);
}
