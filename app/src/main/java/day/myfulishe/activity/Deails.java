package day.myfulishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.myAdapter.CollectAdapter;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.views.SlideAutoLoopView;
import day.myfulishe.R;

public class Deails extends AppCompatActivity {


    @Bind(R.id.tv_good_EnglishName)
    TextView tvGoodEnglishName;
    @Bind(R.id.tv_good_deails_name)
    TextView tvGoodDeailsName;
    @Bind(R.id.tv_good_prize)
    TextView tvGoodPrize;
    @Bind(R.id.SALV_goods_dials)
    SlideAutoLoopView SALVGoodsDials;
    @Bind(R.id.FID)
    FlowIndicator FID;
    @Bind(R.id.tv_goods_dsc)
    WebView tvGoodsDsc;


    int goodId;

    Deails mComtext;
    UserBean user;
    @Bind(R.id.iv_main_cart)
    ImageView ivMainCart;
    @Bind(R.id.iv_collect)
    ImageView ivCollect;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.LL)
    RelativeLayout LL;

    boolean isCollected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deails);
        ButterKnife.bind(this);
        mComtext = this;
        user = FuLiCenterApplication.getUserBean();
        Intent intent = getIntent();
        goodId = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        initData();
    }

    private void initData() {

        NetDao.downloadGoodsDetail(this, goodId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetails(result);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showGoodsDetails(GoodsDetailsBean details) {
        tvGoodEnglishName.setText(details.getGoodsEnglishName());
        tvGoodDeailsName.setText(details.getGoodsName());
        tvGoodPrize.setText(details.getCurrencyPrice());
        SALVGoodsDials.startPlayLoop(FID, getAlbumImgUrl(details), getCount(details));
        tvGoodsDsc.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] url = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albumsBeen = details.getProperties()[0].getAlbums();
            url = new String[albumsBeen.length];
            for (int i = 0; i < albumsBeen.length; i++) {
                url[i] = albumsBeen[i].getImgUrl();
            }
        }
        return url;
    }


    @OnClick({R.id.iv_back, R.id.iv_main_cart, R.id.iv_collect, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_main_cart:
                break;
            case R.id.iv_collect:
                if (isCollected) {
                    deleteCollected();
                } else {
                    addCollect();
                }
                break;
            case R.id.iv_share:
                break;
        }
    }

    private void addCollect() {
        NetDao.collectGoods(mComtext, goodId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    isCollected = true;
                } else {
                    isCollected = false;
                }
                updateGoodsCollectStatus();
                CommonUtils.showLongToast("添加收藏成功");
            }
            @Override
            public void onError(String error) {
                CommonUtils.showLongToast("添加收藏失败");
            }
        });
    }

    private void deleteCollected() {
        NetDao.deleteCollect(mComtext, goodId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    isCollected = false;
                } else {
                    isCollected = true;
                }
                updateGoodsCollectStatus();
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            ivCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetDao.isCollected(mComtext, goodId, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.isSuccess()) {
                    isCollected = true;
                } else {
                    isCollected = false;
                }
                updateGoodsCollectStatus();
            }
            @Override
            public void onError(String error) {

            }
        });
    }
}
