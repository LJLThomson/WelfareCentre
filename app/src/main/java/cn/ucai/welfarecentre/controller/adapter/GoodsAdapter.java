package cn.ucai.welfarecentre.controller.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.FooterViewHodler;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<NewGoodsBean> contactList;
    final int TYPE_ITEM = 0;
    final int TYPE_FOOTER = 1;
    boolean isMore;//由来判断是否到最后了，这时没有数据可加载了
    String footerText;

    public int getFooterText() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = layoutInflater.inflate(R.layout.item_newgoods, parent, false);
                holder = new NewGoodsViewHolder(view);
                break;
            case TYPE_FOOTER:
                view = layoutInflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHodler(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {//页脚
            FooterViewHodler footerViewHodler = (FooterViewHodler) holder;
//            footerViewHodler.footer.setText(footerText);
            footerViewHodler.footer.setText(context.getString(getFooterText()));
//            footerViewHodler.footer.setText(context.getString(id));//可以获取string.xml中的文件
            return;
        }
        NewGoodsBean goods = contactList.get(position);//
        NewGoodsViewHolder newGoodsViewHolder = (NewGoodsViewHolder) holder;
        newGoodsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetailsActitivity(context, contactList.get(position).getGoodsId());
            }
        });

        newGoodsViewHolder.Newgoods.setText(goods.getColorName());
        newGoodsViewHolder.good_price.setText(goods.getCurrencyPrice());
        ImageLoader.downloadImg(context, newGoodsViewHolder.ipicture, goods.getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size() + 1;//contactList总长度
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (this.contactList != null) {
            this.contactList.clear();
        }
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    public void addinitData(ArrayList<NewGoodsBean> list) {
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    static class NewGoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView ipicture;
        @BindView(R.id.Newgoods)
        TextView Newgoods;
        @BindView(R.id.good_price)
        TextView good_price;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
