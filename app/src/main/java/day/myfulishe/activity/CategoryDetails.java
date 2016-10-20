package day.myfulishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.myAdapter.newAdapter;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;

public class CategoryDetails extends AppCompatActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_Title)
    TextView tvTitle;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.fag_rlv_newgoods)
    RecyclerView fagRlvNewgoods;

    SwipeRefreshLayout msrl;


    int mNewState;
    int PageId = 1;
    public newAdapter mAdapter;
    public GridLayoutManager layoutManger;
    ArrayList<NewGoodsBean> NewGoodsBeanlist;
    int CartId;
    @Bind(R.id.bt_SortPrice)
    Button btSortPrice;
    @Bind(R.id.bt_SortTime)
    Button btSortTime;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_details);
        ButterKnife.bind(this);
        msrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        String title = getIntent().getStringExtra("title");
        CartId = getIntent().getIntExtra("cart_id", 0);
        L.i(String.valueOf(CartId) + "cart_id" + "title:" + title);
        initView();
        setListener();
        initData(I.ACTION_DOWNLOAD, CartId, PageId);
        tvTitle.setText(title);
    }

    private void setListener() {

        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setEnabled(true);
                msrl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_DOWNLOAD, CartId, PageId);
            }
        });
        fagRlvNewgoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int Lastposition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewState = newState;
                Lastposition = layoutManger.findLastVisibleItemPosition();
                if (Lastposition >= mAdapter.getItemCount() - 1
                        && newState == RecyclerView.SCROLL_STATE_IDLE &&
                        mAdapter.isMore) {
                    PageId++;
                    initData(I.ACTION_PULL_UP, CartId, PageId);
                    msrl.setRefreshing(false);
                    msrl.setEnabled(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstposition = layoutManger.findFirstVisibleItemPosition();
                msrl.setEnabled(firstposition == 0);
            }
        });


        mAdapter.setMyOnClick(new newAdapter.MyOnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                int goodsId = mAdapter.contactList.get(position).getGoodsId();
                MFGT.gotoGoodsDtails(CategoryDetails.this, goodsId);
            }
        });
    }

    private void initData(final int action, int cartId, int PageId) {
        NetDao.downloadCategoryGoods(this, cartId, PageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                msrl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                if (result != null && result.length > 0 && mAdapter.isMore) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    switch (action) {
                        case I.ACTION_DOWNLOAD:
                            mAdapter.setFooter("加载更多数据");
                            mAdapter.inintContact(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            mAdapter.inintContact(list);
                            break;
                        case I.ACTION_PULL_UP:
                            mAdapter.AddContactList(list);

                            break;
                    }
                } else {
                    mAdapter.setFooter("没有更多数据了");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {

        NewGoodsBeanlist = new ArrayList<>();
        mAdapter = new newAdapter(this, NewGoodsBeanlist);
        layoutManger = new GridLayoutManager(this, I.COLUM_NUM, GridLayoutManager.VERTICAL, false);
        layoutManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        fagRlvNewgoods.setLayoutManager(layoutManger);
        fagRlvNewgoods.setHasFixedSize(true);
        fagRlvNewgoods.setAdapter(mAdapter);
    }

    boolean addTimeAsc = false;
    boolean priceAsc = false;
    int sortBy = I.SORT_BY_ADDTIME_ASC;

    @OnClick({R.id.bt_SortPrice, R.id.bt_SortTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_SortPrice:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                }
                priceAsc = !priceAsc;
                break;
            case R.id.bt_SortTime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                }
                addTimeAsc = !addTimeAsc;
                break;
        }
        mAdapter.setSortBy(sortBy);
    }
}
