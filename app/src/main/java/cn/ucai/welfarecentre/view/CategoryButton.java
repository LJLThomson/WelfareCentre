package cn.ucai.welfarecentre.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class CategoryButton extends ImageView {
    boolean isExpand = false;
    PopupWindow mPopupWindow;
    ArrayList<CategoryChildBean> Categorylist;
    Context mcontext;
    ClassiFicationAdapter mAdapter;
    GridView mGridView;

    public CategoryButton(Context context) {
        super(context);
    }

    public CategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
    }

    public void initCatFilterButton(ArrayList<CategoryChildBean> list) {
        mAdapter = new ClassiFicationAdapter(mcontext, list);
        initGridView();
        setCatFilterButtonListener();
    }

    private void initGridView() {
        mGridView = new GridView(mcontext);//自定义view,GridView(网格视图)是按照行列的方式来显示内容的，
        mGridView.setVerticalSpacing(10);//垂直控件之间的空间距离
        mGridView.setHorizontalSpacing(10);//水平控件之间的空间距离
        mGridView.setNumColumns(2);
//        mGridView.setStretchMode();//设置缩放模式
        mGridView.setAdapter(mAdapter);
    }

    public void setCatFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpand) {
                    initPopup();
                } else {
                    if (mPopupWindow.isShowing()) {//再次点击按钮，弹出窗口消失
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPopup() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setAnimationStyle(R.style.style_popup_window);
        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }

    private void setArrow() {
        Drawable right;
        if (isExpand) {
            right = getResources().getDrawable(R.drawable.arrow2_up);
        } else {
            right = getResources().getDrawable(R.drawable.arrow2_down);
        }
//        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setImageDrawable(right);
        isExpand = !isExpand;
    }

    class ClassiFicationAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> category_list;

        public ClassiFicationAdapter(Context context, ArrayList<CategoryChildBean> category_list) {
            this.context = context;
            this.category_list = category_list;
        }

        @Override
        public int getCount() {
            return category_list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            ClassiFicationViewHolder holder = null;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_classification, viewGroup, false);
                holder = new ClassiFicationViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ClassiFicationViewHolder) convertView.getTag();
            }
            CategoryChildBean mlist = category_list.get(position);
            holder.tv7.setText(mlist.getName());
            ImageLoader.downloadImg(context,holder.img4,mlist.getImageUrl());
            return convertView;
        }

         class ClassiFicationViewHolder {
            @BindView(R.id.img4)
            ImageView img4;
            @BindView(R.id.tv7)
            TextView tv7;

             ClassiFicationViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
/*    class ClassiFicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        ArrayList<CategoryChildBean> category_list;

        public ClassiFicationAdapter(Context context, ArrayList<CategoryChildBean> category_list) {
            this.context = context;
            this.category_list = category_list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            View view = layoutInflater.inflate(R.layout.item_classification, parent, false);
            ClassiFicationViewHolder holder = new ClassiFicationViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ClassiFicationViewHolder classiFicationViewHolder = (ClassiFicationViewHolder) holder;
            CategoryChildBean categoryChildBean = category_list.get(position);
            classiFicationViewHolder.tv7.setText(categoryChildBean.getName());
            ImageLoader.downloadImg(context, classiFicationViewHolder.img4, categoryChildBean.getImageUrl());
        }

        @Override
        public int getItemCount() {
            return category_list.size();
        }

        class ClassiFicationViewHolder extends RecyclerView.ViewHolder {
            ImageView img4;
            TextView tv7;

            ClassiFicationViewHolder(View view) {
                super(view);
                img4 = (ImageView) view.findViewById(R.id.img4);
                tv7 = (TextView) view.findViewById(R.id.tv7);
            }
        }
    }*/
}
