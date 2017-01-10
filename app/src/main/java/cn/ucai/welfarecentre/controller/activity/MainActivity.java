package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.R;

public class MainActivity extends AppCompatActivity {
    RadioButton[] rad ;
    int index;
    @BindView(R.id.boutique)
    RadioButton boutique;
    @BindView(R.id.cart)
    RadioButton cart;
    @BindView(R.id.category)
    RadioButton category;
    @BindView(R.id.new_goods)
    RadioButton newGoods;
    @BindView(R.id.personCentre)
    RadioButton personCentre;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
  /*      rad[0] = (RadioButton) findViewById(R.id.boutique);
        rad[1] = (RadioButton) findViewById(R.id.cart);
        rad[3] = (RadioButton) findViewById(R.id.category);
        rad[4] = (RadioButton) findViewById(R.id.new_goods);
        rad[5] = (RadioButton) findViewById(R.id.personCentre);*/
        rad = new RadioButton[5];
        rad[0] = boutique;
        rad[1] = cart;
        rad[2] = category;
        rad[3] = newGoods;
        rad[4] = personCentre;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boutique:
                index = 0;
                break;
            case R.id.cart:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.new_goods:
                index = 3;
                break;
            case R.id.personCentre:
                index = 4;
                break;
        }
        setSingleSelected(index);
    }

    private void setSingleSelected(int index) {
        for (int i = 0; i < 5; i++) {
            if (index == i) {
                rad[i].setChecked(true);
            } else {
                rad[i].setChecked(false);
            }
        }
    }

}
