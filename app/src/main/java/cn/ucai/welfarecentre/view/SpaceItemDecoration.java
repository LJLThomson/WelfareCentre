package cn.ucai.welfarecentre.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildLayoutPosition(view);//parent为ViewGroup父布局，
        if(parent.getChildPosition(view) != 0){
            outRect.top = space;
        }
        // 设置左右间距
        outRect.set(space / 2, 0, space / 2, 0);
        outRect.top = space;//设置上下布局,距离上面高度space
//        outRect.bottom = space;
    }
}
