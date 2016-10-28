package myFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.myAdapter.CartAdapter;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;
import day.myfulishe.activity.FuLiCenterApplication;

public class Fragment_cart extends Fragment {

    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;


    ArrayList<CartBean> cartBeanArrayList;
    public CartAdapter mAdapter;
    public LinearLayoutManager layoutManger;
    @Bind(R.id.tv_sums)
    TextView tvSums;
    @Bind(R.id.iv_save)
    TextView tvSave;
    @Bind(R.id.layout_cart)
    LinearLayout layoutCart;
    @Bind(R.id.tv_nothing)
    TextView tvNothing;
    @Bind(R.id.fag_rlv_newgoods)
    RecyclerView fagRlvNewgoods;

    updateCartReceiver mReceiver;

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
        initView();
        return view;
    }

    private void initView() {
        setCarLayout(false);
    }

    private void setCarLayout(boolean hasCart) {
        layoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        tvNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        fagRlvNewgoods.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        sumPrice();
    }

    private void setListener() {
        OnRefresh();
        IntentFilter flter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mReceiver = new updateCartReceiver();
        getContext().registerReceiver(mReceiver, flter);


    }

    private void OnRefresh() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setColorSchemeColors(getResources().getColor(R.color.red));
                srl.setRefreshing(true);
                srl.setEnabled(true);
                tvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    private void initData() {
        downloadCart();
    }

    private void downloadCart() {
        final UserBean userBean = FuLiCenterApplication.getUserBean();
        if (userBean != null) {
            NetDao.downUserCart(getContext(), userBean.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    OkHttpUtils utils = new OkHttpUtils(getContext());
                    ArrayList list = utils.array2List(result);
                    tvRefresh.setVisibility(View.GONE);
                    srl.setRefreshing(false);
                    if (list != null && list.size() > 0) {
                        cartBeanArrayList.clear();
                        cartBeanArrayList.addAll(list);
                        mAdapter.inintContact(list);
                        setCarLayout(true);
                    } else {
                        setCarLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCarLayout(false);
                    tvRefresh.setVisibility(View.GONE);
                    srl.setRefreshing(false);
                }

            });

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    private void sumPrice() {
        int sumPrice = 0;
        int ranPrice = 0;
        if (cartBeanArrayList != null && cartBeanArrayList.size() > 0) {
            for (CartBean c : cartBeanArrayList) {
                if (c.isChecked()) {
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    ranPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            tvSums.setText("合计:￥" + Double.valueOf(sumPrice));
            tvSave.setText("节省:￥" + Double.valueOf(sumPrice - ranPrice));
        } else {
            tvSums.setText("合计:￥0");
            tvSave.setText("节省:￥0");
        }
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @OnClick(R.id.Buy)
    public void onClick() {
        MFGT.goPayActivity(getContext());
    }

    class updateCartReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            sumPrice();
            setCarLayout(cartBeanArrayList != null && cartBeanArrayList.size() > 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        downloadCart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            getContext().unregisterReceiver(mReceiver);
        }
    }
}
