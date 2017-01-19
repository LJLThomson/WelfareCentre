package cn.ucai.welfarecentre.controller.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.CollectBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.net.IModelNewGoods;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.FooterViewHodler;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CollectBean> contactList;
    final int TYPE_ITEM = 0;
    final int TYPE_FOOTER = 1;
    boolean isMore;//由来判断是否到最后了，这时没有数据可加载了
    String footerText;
    IModelNewGoods model;

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

    public CollectAdapter(Context context, ArrayList<CollectBean> contactList) {
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
                view = layoutInflater.inflate(R.layout.item_collect, parent, false);
                holder = new CollectViewHolder(view);
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
        final CollectBean goods = contactList.get(position);//
        CollectViewHolder collectViewHolder = (CollectViewHolder) holder;
        collectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetailsActitivity(context, contactList.get(position).getGoodsId());
            }
        });
        collectViewHolder.tv1.setText(goods.getGoodsName());
        collectViewHolder.mwebView.loadDataWithBaseURL(null, goods.getGoodsEnglishName(), I.TEXT_HTML, I.UTF_8, null);
        ImageLoader.downloadImg(context,collectViewHolder.image,goods.getGoodsThumb());
        collectViewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCollectGoods(position);
            }
        });
    }

    private void deleteCollectGoods(int position) {
        notifyItemRemoved(position);
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

    public void initData(ArrayList<CollectBean> list) {
        if (this.contactList != null) {
            this.contactList.clear();
        }
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    public void addinitData(ArrayList<CollectBean> list) {
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

     class CollectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.imgdelete)
        ImageView imgdelete;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv3)
        WebView mwebView;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
