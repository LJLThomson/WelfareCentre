package cn.ucai.welfarecentre.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.bean.CategoryGroupBean;
import cn.ucai.welfarecentre.Model.net.ModelNewGoods;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.L;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.adapter.CategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private final static String TAG = CategoryFragment.class.getSimpleName();//源代码中的基础类的简单名称


    ArrayList<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> childList;
    CategoryAdapter mAdapter;
    ModelNewGoods model;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.elv)
    ExpandableListView elv;

    int group_id =  0;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        model = new ModelNewGoods();
        ButterKnife.bind(this, view);
        initView();
        initData();//获得数据
        setListener();
        return view;
    }

    private void initView(boolean b) {
        elv.setVisibility(b ? View.GONE:View.VISIBLE);
        tv6.setVisibility(b ? View.VISIBLE:View.GONE);
    }

    private void setListener() {
  /*      elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                CategoryGroupBean app = (CategoryGroupBean) expandableListView.getItemAtPosition(groupPosition);
                Toast.makeText(getActivity(), app.getName() + "被点击", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
 /*       elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long childId) {
      *//*          view.setOnClickListener(new View.OnClickListener() {//双击调用,view指的是当前itemView
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "未来的世界不是很好", Toast.LENGTH_SHORT).show();
                    }
                });*//*
                CategoryChildBean app = (CategoryChildBean) expandableListView.getItemAtPosition(childPosition);
                Toast.makeText(getActivity(), app.getName() + "被点击", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
    }

    private void initView() {
//        //去掉前面的箭头图标
        elv.setGroupIndicator(null);
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        mAdapter = new CategoryAdapter(getActivity(), groupList, childList);
        elv.setAdapter(mAdapter);
    }


    private void initData() {

        model.downGroupCategory(getActivity(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
//                    ArrayList<CategoryGroupBean> list2 = ConvertUtils.array2List(result);
                    groupList = ConvertUtils.array2List(result);
//                    groupList.addAll(list2);
                    for (int i = 0; i < groupList.size(); i++) {
//                        Log.e("main", "size:" + childList.size());
                        childList.add(new ArrayList<CategoryChildBean>());
                        childDownLoadData(groupList.get(i).getId(), i);//将改组的图片下下来
//                        第二种方式，就是一次将childlist图片全部下载出来，CategoryChildBean[][];
//                      这种方式，二维数组变成二维集合，采取下面的方式，一层一层剥削，
//                        for(int i=0;i<childArray.length;i++) {//二维数组变成二维集合，策略
//                            List<Integer> list = Arrays.asList(childArray[i]);
//                            ArrayList<Integer> children = new ArrayList<>(list);//得到数组childArray[i]中每张图片
//                            childList.add(children);//
//                        }

                    }
                }else{
                    initView(false);
                }

                Log.i("main", ">>>>>>>>>>>>>>" + childList.size());
                mAdapter.addInitContactList(groupList, childList);
            }

            @Override
            public void onError(String error) {
   /*             group_position++;//倘若上面出错，可以继续下载
                childDownLoadData(groupList.get(group_position).getId(), group_position);*/
                initView(false);
                L.i(TAG, "groupList>>>>>" + error);
            }
        });
    }

    private void childDownLoadData(int category_id, final int index) {
        childList.add(new ArrayList<CategoryChildBean>());
        Log.e("main", "childListsize:" + childList.size());
        model.downCategory(getActivity(), category_id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                if (result != null) {

                    childList.add(new ArrayList<CategoryChildBean>());
                    childList.add(new ArrayList<CategoryChildBean>());
                    childList.add(new ArrayList<CategoryChildBean>());
                    childList.add(new ArrayList<CategoryChildBean>());
                    childList.add(new ArrayList<CategoryChildBean>());
                    Log.e("main", "childListsize:" + childList.size());
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                    group_id++;
//                    childList.add(list);
                    childList.set(index,list);
                    Log.e("main","groupList" + groupList.size());
/*                    if (group_id == groupList.size()){
                        mAdapter.addInitContactList(groupList,childList);
                    }*/
//                    childList.add(index,list);//越界问题，index集合范围，childList.size()<index，就会报错
//                    childList.set(index, list);
                }
            }

            @Override
            public void onError(String error) {
                L.i(TAG, ">>>>>>>>>" + error);
            }
        });
    }

}
