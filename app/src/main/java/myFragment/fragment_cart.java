package myFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.myAdapter.CartAdapter;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;
import day.myfulishe.activity.FuLiCenterApplication;

public class Fragment_cart extends Fragment {
    @Bind(R.id.tv_sums)
    TextView tvSums;
    @Bind(R.id.iv_save)
    TextView ivSave;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.fag_rlv_newgoods)
    RecyclerView fagRlvNewgoods;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;


    ArrayList<CartBean> cartBeanArrayList;
    public CartAdapter mAdapter;
    public LinearLayoutManager layoutManger;

    public Fragment_cart() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        cartBeanArrayList = new ArrayList<>();
        mAdapter = new CartAdapter(getContext(), cartBeanArrayList);
        layoutManger = new LinearLayoutManager(getContext());
        fagRlvNewgoods.setLayoutManager(layoutManger);
        fagRlvNewgoods.setAdapter(mAdapter);
        initData();
        setListener();
        return view;
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setColorSchemeColors(getResources().getColor(R.color.red));
                srl.setRefreshing(true);
                srl.setEnabled(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData();
            }
        });
    }

    private void initData() {
        final UserBean userBean = FuLiCenterApplication.getUserBean();
        if (userBean == null) {
            return;
        } else {
            NetDao.downUserCart(getContext(), userBean.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    for (CartBean c : result) {
                        L.e("Cart:"+c.toString());
                    }
                    OkHttpUtils utils = new OkHttpUtils(getContext());
                    ArrayList list = utils.array2List(result);
                    mAdapter.inintContact(list);
                    tvRefresh.setVisibility(View.GONE);
                    srl.setRefreshing(false);
                }

                @Override
                public void onError(String error) {

                }

            });
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
