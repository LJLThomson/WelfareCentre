package cn.ucai.welfarecentre.controller.activity;

import android.content.Intent;
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

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static String URL = "http://218.244.151.190/demo/charge";

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
        initPingPP();
    }

    private void initPingPP() {
        // 设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "bfb"});
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //是否开启日志
        PingppLog.DEBUG = true;
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
        final int[] cityPosition = new int[]{0};
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
        showPay();
    }

    private void showPay() {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());

        // 计算总金额（以分为单位）
        int amount = 0;
        JSONArray billList = new JSONArray();
   /*     for (Good good : mList) {
            amount += good.getPrice() * good.getCount() * 100;
            billList.put(good.getName() + " x " + good.getCount());
        }*/
        // 构建账单json对象
        JSONObject bill = new JSONObject();

        // 自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", amount);
            bill.put("extras", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), null, URL, new PaymentHandler() {

            // 返回支付结果
            // @param data

            @Override
            public void handlePaymentResult(Intent data) {
            }
        });
    }
}
