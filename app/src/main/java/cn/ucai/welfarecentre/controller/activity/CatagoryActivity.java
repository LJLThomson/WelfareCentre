package cn.ucai.welfarecentre.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.NewGoodsFragment;
import cn.ucai.welfarecentre.view.CategoryButton;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CatagoryActivity extends AppCompatActivity {

    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    NewGoodsFragment mNewGoodsFragment;

    boolean priceArc = false;
    boolean AddTime = false;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.img1)
    CategoryButton img1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_second_layout);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        String groupName = getIntent().getStringExtra("groupName");
        tvCommonTitle.setText(groupName);
        ArrayList<CategoryChildBean> category_list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra("category_list");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout3, mNewGoodsFragment).commit();
//        img1.initCatFilterButton(groupName, );
//        img1.setCatFilterButtonListener();//监听事件
         img1.initCatFilterButton(category_list);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onClick(View view) {
        int sortBy = I.SORT_BY_ADDTIME_ASC;
        switch (view.getId()) {
            case R.id.bt1:
                if (priceArc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    Drawable drawable = getResources().getDrawable(R.drawable.arrow_order_up);
                    int w = drawable.getIntrinsicWidth();
                    int h = drawable.getIntrinsicHeight();
                    drawable.setBounds(0, 0, w, h);
                    bt1.setCompoundDrawables(null, null, drawable, null);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;//降序
                    Drawable drawable = getResources().getDrawable(R.drawable.arrow_order_down);
                    int w = drawable.getIntrinsicWidth();
                    int h = drawable.getIntrinsicHeight();
                    drawable.setBounds(0, 0, w, h);
                    bt1.setCompoundDrawables(null, null, drawable, null);
                }
                priceArc = !priceArc;
                break;
            case R.id.bt2:
                if (AddTime) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                }
                sortBy = I.SORT_BY_ADDTIME_ASC;
                break;
        }
        mNewGoodsFragment.SortGoods(sortBy);

    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }
}

