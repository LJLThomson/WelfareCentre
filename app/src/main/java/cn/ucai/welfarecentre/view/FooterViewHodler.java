package cn.ucai.welfarecentre.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class FooterViewHodler extends RecyclerView.ViewHolder {//不同包之间要记得public，不然会报错
    public TextView footer;

    public FooterViewHodler(View itemView) {
        super(itemView);
        footer = (TextView) itemView.findViewById(R.id.footer);
    }
}