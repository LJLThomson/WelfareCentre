package cn.ucai.welfarecentre.controller.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    @BindView(R.id.iv_persona_center_msg)
    ImageView ivPersonaCenterMsg;
    @BindView(R.id.tv_center_settings)
    TextView tvCenterSettings;
    @BindView(R.id.center_top)
    RelativeLayout centerTop;
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.iv_user_qrcode)
    ImageView ivUserQrcode;
    @BindView(R.id.center_user_info)
    LinearLayout centerUserInfo;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.layout_center_collect)
    LinearLayout layoutCenterCollect;
    @BindView(R.id.center_user_collects)
    RelativeLayout centerUserCollects;
    @BindView(R.id.center_user_order_lis)
    GridView centerUserOrderLis;
    @BindView(R.id.ll_user_life)
    LinearLayout llUserLife;
    @BindView(R.id.ll_user_store)
    LinearLayout llUserStore;
    @BindView(R.id.ll_user_members)
    LinearLayout llUserMembers;

    CollectReceiver collectReceiver;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
//        用户头像http://101.251.196.90:8000/FuLiCenterServerV2.0/downloadAvatar?name_or_hxid=12&avatarType=user_avatar&m_avatar_suffix=.jpg&width=60&height=60
        ButterKnife.bind(this, view);
        downloadDefaultAvatar();
//        广播注册
        collectReceiver = new CollectReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("WelfareCentreCollectCount");
        getActivity().registerReceiver(collectReceiver, filter);
        return view;
    }

    private void downloadDefaultAvatar() {
//          图片下载，这里下载的是默认图片
        User user = FuLiCentreApplication.getUser();
        if (user != null) {
            String nick = user.getMuserNick();
            tvUserName.setText(nick);//设置昵称
            String avatar_img = ImageLoader.getAvatarUrl(user);
            ImageLoader.setAvatar(avatar_img, getActivity(), ivUserAvatar);
        }
    }


    @OnClick({R.id.iv_persona_center_msg, R.id.iv_user_avatar, R.id.tv_collect_count})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_persona_center_msg:
                MFGT.gotoSettingsActivity(getActivity());
                break;
            case R.id.center_user_collects:
                break;
            case R.id.iv_user_avatar:
//          弹出窗口，从相机或者拍照
                break;
        }
    }

    class CollectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (collectReceiver != null) {//注册成功之后，
                int count = getActivity().getIntent().getIntExtra("CollectCount", 0);
                tvCollectCount.setText(count+"");
            }
        }
    }
}
