package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.bean.CategoryGroupBean;
import cn.ucai.welfarecentre.Model.bean.CollectBean;
import cn.ucai.welfarecentre.Model.bean.GoodsDetailsBean;
import cn.ucai.welfarecentre.Model.bean.MessageBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.OkHttpUtils;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class ModelNewGoods implements IModelNewGoods {
    @Override
    public void downData(Context context, int cartId, int pageId, OnCompleteListener listener) {
//        onCompleteListener为
        if (cartId == 0) {
            OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);//可以直接得到pageData中的数据
            utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                    .addParam(I.CategoryGood.CAT_ID, String.valueOf(cartId))
                    .addParam(I.PAGE_ID, String.valueOf(pageId))
//                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                    .addParam(I.PAGE_SIZE, String.valueOf(4))
                    .targetClass(NewGoodsBean[].class)
                    .execute(listener);
        } else {//专门用于得到二级页面的
            OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
            utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                    .addParam(I.GoodsDetails.KEY_CAT_ID, String.valueOf(cartId))
                    .addParam(I.PAGE_ID, String.valueOf(pageId))
                    .addParam(I.PAGE_SIZE, String.valueOf(4))
                    .targetClass(NewGoodsBean[].class)
                    .execute(listener);
        }

    }

    @Override
    public void downBoutique(Context context, OnCompleteListener listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    //    商品详情
    public void downGoodsDetails(Context context, int good_id, OnCompleteListener listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, "" + good_id)
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void downGroupCategory(Context context, OnCompleteListener listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    @Override
    public void downCategory(Context context, int category_id, OnCompleteListener listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(category_id))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    @Override
    public void downSecond_Category(Context context, int Second_CategoryId, int page_id, int page_size, OnCompleteListener listener) {
        OkHttpUtils<GoodsDetailsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.GoodsDetails.KEY_CAT_ID, String.valueOf(Second_CategoryId))
                .addParam(I.PAGE_ID, String.valueOf(page_id))
                .addParam(I.PAGE_SIZE, String.valueOf(page_size))
                .targetClass(GoodsDetailsBean[].class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/isCollect?goods_id=121&userName=131
    @Override
    public void isCollect(Context context, int goodsId, String username, OnCompleteListener listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/addCollect?goods_id=7672&userName=ljl123456
    @Override
    public void setCollect(Context context, int goodsId, String username, int collect, OnCompleteListener listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        switch (collect) {
            case I.ACTION_DELETE_COLLECT://true
                utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                        .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                        .addParam(I.Collect.USER_NAME, username)
                        .targetClass(MessageBean.class)
                        .execute(listener);
                break;
            case I.ACTION_ADD_COLLECT://添加收藏
                utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                        .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                        .addParam(I.Collect.USER_NAME, username)
                        .targetClass(MessageBean.class)
                        .execute(listener);
                break;
        }
    }

    //http://101.251.196.90:8000/FuLiCenterServerV2.0/findCollectCount?userName=ljl123456
    @Override
    public void getCollectCount(Context context, String userName, OnCompleteListener listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    //    http://101.251.196.90:8000/FuLiCenterServerV2.0/findCollects?userName=ljl123456&page_id=1&page_size=2
    @Override
    public void getcollects(Context context, String userName, int pageId, OnCompleteListener listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(4))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    //    添加到购物车中
//    http://101.251.196.90:8000/FuLiCenterServerV2.0/addCart?goods_id=7276&userName=ljl123456&count=1&isChecked=1
    @Override
    public void addCart(Context context, int goodsId, String userName, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Cart.USER_NAME, userName)
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(1))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
//    找到勾选的物品
    //    http://101.251.196.90:8000/FuLiCenterServerV2.0/findCarts?userName=ljl123456
    @Override
    public void getCart(Context context, String userName, OnCompleteListener<CartBean> listener) {
        OkHttpUtils<CartBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(CartBean.class)
                .execute(listener);
    }

    //删除勾选物品
//    http://101.251.196.90:8000/FuLiCenterServerV2.0/deleteCart?id=7276
    @Override
    public void delCart(Context context, int cartId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
//http://101.251.196.90:8000/FuLiCenterServerV2.0/updateCart?id=7276&count=1&isChecked=1
    @Override
    public void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .addParam(I.Cart.COUNT,String.valueOf(cartId))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(count))
                .targetClass(MessageBean.class)
                .execute(listener);
    }
//用于判断删除还是添加
    @Override
    public void updateCart(Context context, int action, String userName,int goodsId ,OnCompleteListener<MessageBean> listener) {
        if (FuLiCentreApplication.getMyCartList().containsKey(goodsId)){
            if (action == I.Cart.ACTION_CART_ADD){
                delCart(context,0,listener);
            }else{
//                updateCart(context,0,listener);
            }
        }
    }
}
