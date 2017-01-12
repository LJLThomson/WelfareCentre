package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.GoodsDetailsFragment;
import cn.ucai.welfarecentre.controller.fragment.NewGoodsFragment;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class GoodsDetailsAcitivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actovity_boutique);
        getSupportFragmentManager().beginTransaction().
                add(R.id.frameLayout2,new GoodsDetailsFragment())
                .commit();
    }
}
