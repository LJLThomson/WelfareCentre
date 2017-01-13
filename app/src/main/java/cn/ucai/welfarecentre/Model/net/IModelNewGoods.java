package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
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
}
