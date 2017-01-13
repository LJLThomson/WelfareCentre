package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.BoutiqueFragment;
import cn.ucai.welfarecentre.controller.fragment.CategoryFragment;
import cn.ucai.welfarecentre.controller.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    RadioButton[] rad;
    Fragment[] fragments;
    int index;
    int currentIndex = 0;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.new_goods)
    RadioButton newGoods;
    @BindView(R.id.boutique)
    RadioButton boutique;
    @BindView(R.id.category)
    RadioButton category;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.cart)
    RadioButton cart;
    @BindView(R.id.personCentre)
    RadioButton personCentre;

    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mcategoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rad = new RadioButton[5];
        fragments = new Fragment[5];
        mBoutiqueFragment = new BoutiqueFragment();
        mNewGoodsFragment = new NewGoodsFragment();
        mcategoryFragment = new CategoryFragment();
        fragments[0] = mNewGoodsFragment;
        fragments[1] = mBoutiqueFragment;
        fragments[2] = mcategoryFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        使用add，hide，show时默认开始要将它们所有
        transaction.add(R.id.frameLayout, mNewGoodsFragment)
                .add(R.id.frameLayout, mBoutiqueFragment)
                .add(R.id.frameLayout, mcategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .hide(mcategoryFragment)
                .commit();//开始默认页面

        rad[0] = newGoods;
        rad[1] = boutique;
        rad[2] = category;
        rad[3] = cart;
        rad[4] = personCentre;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_goods:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.cart:
                index = 3;
                break;
            case R.id.personCentre:
                index = 4;
                break;
        }

        if (currentIndex != index) {
            setFragment();
            setSingleSelected(index);
        }
    }

    private void setFragment() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (!fragments[index].isAdded()) {//没有添加,则添加
            ft.add(R.id.frameLayout, fragments[index]);
        }
//        ft.show(fragments[index])
//                .hide(fragments[currentIndex]);//显示这次，隐藏上次
        ft.hide(fragments[currentIndex]).show(fragments[index]);
        ft.commit();
    }

    private void setSingleSelected(int index) {
        for (int i = 0; i < rad.length; i++) {
            if (index == i) {
                rad[i].setChecked(true);
            } else {
                rad[i].setChecked(false);
            }
            currentIndex = index;//点击
        }
    }
}
