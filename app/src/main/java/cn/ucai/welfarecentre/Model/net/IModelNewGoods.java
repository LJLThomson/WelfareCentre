package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelNewGoods {
    void downData(Context context,int cartId,int pageId,OnCompleteListener<NewGoodsBean[]> listener);
}
