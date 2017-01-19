package cn.ucai.welfarecentre.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.FooterViewHodler;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CartBean> contactList;
    final int TYPE_ITEM = 0;
    final int TYPE_FOOTER = 1;
    boolean isMore;//由来判断是否到最后了，这时没有数据可加载了
    String footerText;
    int count = 1;
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

    public CartAdapter(Context context, ArrayList<CartBean> contactList) {
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
                view = layoutInflater.inflate(R.layout.item_cart, parent, false);
                holder = new CartViewHolder(view);
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
        final CartBean cartBean = contactList.get(position);//
        final CartViewHolder CartViewHolder = (CartViewHolder) holder;
        CartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetailsActitivity(context, contactList.get(position).getGoodsId());
            }
        });
        CartViewHolder.chkSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartViewHolder.chkSelect.setChecked(true);//点击之后为true
            }
        });
        CartViewHolder.tvCartCount.setText(cartBean.getCount());
        CartViewHolder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                点击一次，增加一件，count为1;
                for(int i=0;i<=count;i++){
                    count = cartBean.getCount();
                    CartViewHolder.tvCartCount.setText(i+count+"");//i+count+""先计算
                }
                count++;
            }
        });
        CartViewHolder.ivReduceCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          点击一次，减少一件，直到为0时，结束

            }
        });
//        CartViewHolder.Newgoods.setText(goods.getColorName());
//        CartViewHolder.good_price.setText(goods.getCurrencyPrice());
//        ImageLoader.downloadImg(context,CartViewHolder.ipicture,goods.getGoodsThumb());
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

    public void initData(ArrayList<CartBean> list) {
        if (this.contactList != null) {
            this.contactList.clear();
        }
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    public void addinitData(ArrayList<CartBean> list) {
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chkSelect)
        CheckBox chkSelect;
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.ivAddCart)
        ImageView ivAddCart;
        @BindView(R.id.tvCartCount)
        TextView tvCartCount;
        @BindView(R.id.ivReduceCart)
        ImageView ivReduceCart;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
