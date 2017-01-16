package cn.ucai.welfarecentre.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class CategoryButton extends ImageView {
    boolean isExpand = false;

    public CategoryButton(Context context) {
        super(context);
    }

    public CategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initCatFilterButton(String groupName, ArrayList<CategoryChildBean> list) {
        setCatFilterButtonListener();
    }

    public void setCatFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpand) {

                } else {

                }
                setArrow();
            }
        });
    }

    private void setArrow() {
        Drawable right;
        if (isExpand) {
            right = getResources().getDrawable(R.drawable.arrow2_up);
        } else {
            right = getResources().getDrawable(R.drawable.arrow2_down);
        }
//        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setImageDrawable(right);
        isExpand = !isExpand;
    }
}
