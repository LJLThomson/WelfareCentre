package cn.ucai.welfarecentre.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.bean.MessageBean;
import cn.ucai.welfarecentre.Model.bean.NewGoodsBean;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.net.IModelNewGoods;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.view.FooterViewHodler;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<CartBean> contactList;
    IModelNewGoods model = new ModelNewGoods();
    User user = FuLiCentreApplication.getUser();

    public CartAdapter(Context context, ArrayList<CartBean> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        holder = new CartBeanViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CartBeanViewHolder cartBeanViewHolder = (CartBeanViewHolder) holder;
        cartBeanViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();//contactList总长度
    }


    public void initData(ArrayList<CartBean> list) {
        if (this.contactList != null) {
            this.contactList.clear();
        }
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }


    class CartBeanViewHolder extends RecyclerView.ViewHolder {
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
        int listPosition;

        CartBeanViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            listPosition = position;
            NewGoodsBean goods = contactList.get(position).getGoods();
            if (goods != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MFGT.gotoGoodsDetailsActitivity(context, contactList.get(position).getGoodsId());
                    }
                });
                tvGoodsName.setText(goods.getColorName());
                ImageLoader.downloadImg(context, ivGoodsThumb, goods.getGoodsThumb());
                tvGoodsPrice.setText(goods.getCurrencyPrice());
            }
            contactList.get(position).setCount(1);
            tvCartCount.setText("(" + contactList.get(position).getCount() + ")");
//            一开始应该勾选上，这里却没有，
            chkSelect.setChecked(contactList.get(position).isChecked());
        }

        @OnCheckedChanged(R.id.chkSelect)
        public void checkListener(boolean checked) {
/*            chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });*/
            contactList.get(listPosition).setChecked(checked);//为其设置
            context.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
        }

        @OnClick(R.id.ivAddCart)
        public void addCart() {
//            添加商品
            if (user != null) {
                model.updateCart(context, I.Cart.ACTION_CART_ADD,
                        contactList.get(listPosition).getId(), user.getMuserName(),
                        contactList.get(listPosition).getGoodsId(),
                        new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                    contactList.get(listPosition).setCount(contactList.get(listPosition).getCount() + 1);
                                    tvCartCount.setText("(" + contactList.get(listPosition).getCount() + ")");
                                    context.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }
                );
            }
        }

        @OnClick(R.id.ivReduceCart)
        public void delCart() {
            final int count = contactList.get(listPosition).getCount();
            int action;
            if (count > 1) {
                action = I.Cart.ACTION_CART_UPDATE;
            } else {
//                删除勾选物品，两步，delCart——删除商品信息，不然下次重启时，商品信息再次出现
//                第二步，是通过，删除contactList.remove(listPosition);同样可以达到目的
                action = I.Cart.ACTION_CART_DEL;
            }
            if (user != null) {
//                updateCart得到该商品的信息，
                model.updateCart(context, action,
                        contactList.get(listPosition).getId(), user.getMuserName(),
                        contactList.get(listPosition).getGoodsId(),
                        new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
//                                    对CartBean中数据进行设置
                                    contactList.get(listPosition).setCount(contactList.get(listPosition).getCount() - 1);
                                    tvCartCount.setText("(" + contactList.get(listPosition).getCount() + ")");
                                    context.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                    if (contactList.get(listPosition).getCount() < 1) {
//                                        如果小于0，则直接删除掉
                                        contactList.remove(listPosition);
                                        notifyDataSetChanged();
                                        return;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }
                );
            }
        }
    }
}
