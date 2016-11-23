package controller.myFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import controller.myAdapter.CategoryAdapter;
import model.net.ModelCategory;
import model.net.IModelCategory;
import model.utils.ConvertUtils;
import model.utils.OkHttpUtils;
import day.myfulishe.R;

public class Fragment_category extends Fragment {


    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.ELV)
    ExpandableListView ELV;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    CategoryAdapter categoryAdapter;
    ArrayList<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> chillist;

    IModelCategory category;

    public Fragment_category() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        initData();
        groupList = new ArrayList<>();
        chillist = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), groupList, chillist,ELV);
        ELV.setAdapter(categoryAdapter);
        setListener();
        return view;

    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                srl.setEnabled(true);
            }
        });
    }

    private void initData() {
        category = new ModelCategory();
        category.downloadCategoryGroup(getContext(), new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                ArrayList<CategoryGroupBean> categorylist = ConvertUtils.array2List(result);
                categoryAdapter.addlist(categorylist);
                srl.setRefreshing(false);
                srl.setEnabled(false);
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
