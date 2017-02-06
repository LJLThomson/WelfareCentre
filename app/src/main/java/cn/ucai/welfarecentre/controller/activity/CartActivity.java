package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.CartFragment;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_boutique);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_Layout,new CartFragment())
                .commit();
    }

}
