package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.myAdapter.CollectAdapter;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;

public class Collections extends AppCompatActivity {
    public GridLayoutManager layoutManger;
    ArrayList<CollectBean> Collectionlist;
    public CollectAdapter mAdapter;
    public RecyclerView mrv;

    int mNewState;
    int PageId = 1;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    Collections mContext;
    UserBean userBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        ButterKnife.bind(this);
        userBean = FuLiCenterApplication.getUserBean();
        initView();
        initData(I.ACTION_DOWNLOAD, PageId);
        setListener();
    }

    private void initView() {
        mContext = this;
        mrv = (RecyclerView) findViewById(R.id.fag_rlv_newgoods);
        Collectionlist = new ArrayList<>();
        mAdapter = new CollectAdapter(mContext, Collectionlist);
        layoutManger = new GridLayoutManager(mContext, I.COLUM_NUM, GridLayoutManager.VERTICAL, false);
        layoutManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        mrv.setLayoutManager(layoutManger);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);

    }

    private void initData(final int action, int PageId) {
        NetDao.downloadCollections(this, userBean.getMuserName(), PageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                if (result != null && result.length > 0 && mAdapter.isMore) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
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
                srl.setRefreshing(false);
                srl.setEnabled(false);
            }
        });
    }


    private void setListener() {

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setEnabled(true);
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_DOWNLOAD, 1);
            }
        });

        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    initData(I.ACTION_PULL_UP, PageId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstposition = layoutManger.findFirstVisibleItemPosition();
                srl.setEnabled(firstposition == 0);
            }
        });

        mAdapter.setMyOnClick(new CollectAdapter.MyOnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                int goodsId = mAdapter.contactList.get(position).getGoodsId();
                MFGT.gotoGoodsDtails(mContext, goodsId);
            }
        });

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        MFGT.finish(mContext);
    }
}
