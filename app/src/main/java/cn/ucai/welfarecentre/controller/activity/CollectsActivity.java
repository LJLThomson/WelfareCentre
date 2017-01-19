package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.CollectsFragment;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class CollectsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actovity_boutique);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout2, new CollectsFragment())
                .commit();
    }
}
