package cn.ucai.welfarecentre.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.bean.CategoryGroupBean;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> childList;
    View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CategoryChildBean categoryChildBean = (CategoryChildBean) view.getTag(R.id.ivChild);
            CategoryGroupBean categoryGroupBean = (CategoryGroupBean) view.getTag(R.id.ivGroup);
            ArrayList<CategoryChildBean> category_list = (ArrayList<CategoryChildBean>) view.getTag(R.id.tv4);
            String groupName = categoryGroupBean.getName();
            int picId = categoryChildBean.getId();
            MFGT.gotoCatagoryActivity(context,picId,groupName,category_list);
        }
    };

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void addInitContactList(ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.groupList.clear();
        this.childList.clear();
        this.groupList.addAll(groupList);
        this.childList.addAll(childList);
        notifyDataSetChanged();
    }

    class GroupViewHolder {
        ImageView ivGroup,ivExpand;
        TextView tv5;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
 /*       for (int i=0;i<getGroupCount();i++){

        }*/
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_group,viewGroup,false);
            holder = new GroupViewHolder();
            holder.ivExpand = (ImageView) convertView.findViewById(R.id.ivExpand);
            holder.ivGroup = (ImageView) convertView.findViewById(R.id.ivGroup);
            holder.tv5 = (TextView) convertView.findViewById(R.id.tv5);
            convertView.setTag(holder);
        }else{
            holder = (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean categoryGroupBean = groupList.get(groupPosition);
        holder.tv5.setText(categoryGroupBean.getName());
        ImageLoader.downloadImg(context,holder.ivGroup,groupList.get(groupPosition).getImageUrl());
        if (isExpanded) {// 若是展开的状态
            holder.ivExpand.setImageResource(R.drawable.expand_off);//
        } else {
            holder.ivExpand.setImageResource(R.drawable.expand_on);
        }
        return convertView;
    }
    class ChildViewHolder{
        ImageView ivChild;
        TextView tv4;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_child, parent, false);
            holder = new ChildViewHolder();
            holder.ivChild = (ImageView) convertView.findViewById(R.id.ivChild);
            holder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
            convertView.setTag(holder);
            convertView.setOnClickListener(onItemClickListener);
        }else{
            View view = convertView;
            holder = (ChildViewHolder) convertView.getTag();
            convertView.setOnClickListener(onItemClickListener);
        }
        CategoryChildBean categoryChildBean = childList.get(groupPosition).get(childPosition);

        convertView.setTag(R.id.tv4,childList.get(groupPosition));

        holder.tv4.setText(categoryChildBean.getName());
        Log.i("main","categoryId"+convertView.getId());
        ImageLoader.downloadImg(context, holder.ivChild, childList.get(groupPosition).get(childPosition).getImageUrl());
        convertView.setTag(R.id.ivChild,getChild(groupPosition,childPosition));
        convertView.setTag(R.id.ivGroup,getGroup(groupPosition));
     /*   convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
