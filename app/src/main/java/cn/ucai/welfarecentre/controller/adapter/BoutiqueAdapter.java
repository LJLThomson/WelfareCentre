package cn.ucai.welfarecentre.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.FooterViewHodler;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<BoutiqueBean> contactList;
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

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> contactList) {
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
                view = layoutInflater.inflate(R.layout.item_boutique, parent, false);
                holder = new BoutiqueViewHolder(view);
                break;
            case TYPE_FOOTER:
                view = layoutInflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHodler(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHodler footerViewHodler = (FooterViewHodler) holder;
            footerViewHodler.footer.setText(footerText);
//            footerViewHodler.footer.setText(context.getString(getFooterText()));
//            footerViewHodler.footer.setText(context.getString(id));//可以获取string.xml中的文件
            return;
        }
        BoutiqueBean boutiques = contactList.get(position);//
        BoutiqueViewHolder boutiqueViewHolder = (BoutiqueViewHolder) holder;
        boutiqueViewHolder.mtitle.setText(boutiques.getTitle());
        boutiqueViewHolder.mdescription.setText(boutiques.getDescription());
//        http://101.251.196.90:8000/FuLiCenterServerV2.0/downloadImage?imageurl=23，下载图片的接口
        ImageLoader.downloadImg(context,boutiqueViewHolder.imageurl,boutiques.getImageurl());
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (this.contactList != null) {
            this.contactList.clear();
        }
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    public void addinitData(ArrayList<BoutiqueBean> list) {
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
         ImageView imageurl;
         TextView mtitle, mdescription, mname;

        public BoutiqueViewHolder(View itemView) {
            super(itemView);
            imageurl = (ImageView) itemView.findViewById(R.id.mimageurl);
            mtitle = (TextView) itemView.findViewById(R.id.mtitle);
            mdescription = (TextView) itemView.findViewById(R.id.mdescription);
            mname = (TextView) itemView.findViewById(R.id.mname);
        }
    }
}