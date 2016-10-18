package day.myfulishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.views.FlowIndicator;
import cn.ucai.fulicenter.views.SlideAutoLoopView;
import day.myfulishe.R;

public class Deails extends AppCompatActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_main_cart)
    ImageView ivMainCart;
    @Bind(R.id.iv_collect)
    ImageView ivCollect;
    @Bind(R.id.iv_share)
    ImageView ivShare;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deails);
        ButterKnife.bind(this);

        // Toast.makeText(Deails.this, goodId, Toast.LENGTH_SHORT).show();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        goodId = intent.getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
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
        tvGoodsDsc.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getCount(GoodsDetailsBean details) {
        return details.getProperties()[0].getAlbums().length;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] url = new String[]{};
        AlbumsBean[] albumsBeen = details.getProperties()[0].getAlbums();
        url = new String[albumsBeen.length];
        for (int i = 0; i < albumsBeen.length; i++) {
            url[i] = albumsBeen[i].getImgUrl();
        }
        return url;
    }


    @OnClick(R.id.iv_back)
    public void onClick() {
    }
}
