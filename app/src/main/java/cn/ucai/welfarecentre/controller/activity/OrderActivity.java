package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.utils.DisplayUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.SharePrefrenceUtils;
import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class OrderActivity extends AppCompatActivity {
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.etReceiverName)
    EditText etReceiverName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.spinArea)
    Spinner spinArea;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.tv_count)
    TextView tvCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this, "添加收货地址");
        int sumPrice = getIntent().getIntExtra(I.Cart.SAVAPRICE, 0);
        tvCount.setText("" + sumPrice);
        readloginfo();
    }

    private void readloginfo() {
//        String CusumerName = etReceiverName.getText().toString();
//        读取信息，让其一开始显示在屏幕上，
        SharePrefrenceUtils utils = SharePrefrenceUtils.getInstance(this);
        String CusumerName = utils.getCusumerName();
        if (!TextUtils.isEmpty(CusumerName)) {
            etReceiverName.setText(CusumerName);
        }

        String phoneNumber = utils.getphoneNumber();
        if (!TextUtils.isEmpty(phoneNumber)) {
            etPhone.setText(phoneNumber);
        }
        int  city = utils.getCity();
        if (city != -1) {
            spinArea.getItemAtPosition(city);
        }
        String address = utils.getAddress();
        if (!TextUtils.isEmpty(address)){
            etAddress.setText(address);
        }
    }

    @OnClick(R.id.btnBuy)
    public void onClick() {
        String CusumerName = etReceiverName.getText().toString();
        if (TextUtils.isEmpty(CusumerName)) {
            etReceiverName.setText("编号不能为空");
            etReceiverName.requestFocus();
            return;
        }
        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            etPhone.setText("编号不能为空");
            etPhone.requestFocus();
            return;
        }
        final int[] cityPosition = new int[1];
        spinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                select_content[0] = spinArea.getItemAtPosition(position).toString();
                cityPosition[0] = position;
                spinArea.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        String Address = etAddress.getText().toString();
        if (TextUtils.isEmpty(Address)) {
            etAddress.setText("编号不能为空");
            etAddress.requestFocus();
            return;
        }
        SharePrefrenceUtils utils = SharePrefrenceUtils.getInstance(this);
        utils.savaAddress(CusumerName, phoneNumber, cityPosition[0], Address);
        
    }
}
