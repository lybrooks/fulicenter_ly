package myFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.myAdapter.CategoryAdapter;
import day.myfulishe.R;

public class Fragment_category extends Fragment {


    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.ELV)
    ExpandableListView ELV;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    CategoryAdapter categoryAdapter;
    ArrayList<CategoryGroupBean> categoryList;
    public LinearLayoutManager layoutManger;
    public Fragment_category() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        categoryList = new ArrayList<>();

        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
