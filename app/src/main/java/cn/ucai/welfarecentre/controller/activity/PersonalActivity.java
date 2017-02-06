package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.fragment.PersonalFragment;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class PersonalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_Layout,new PersonalFragment())
                .commit();
    }
}
