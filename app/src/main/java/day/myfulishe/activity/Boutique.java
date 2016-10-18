package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import day.myfulishe.R;

public class Boutique extends AppCompatActivity {

    @Bind(R.id.back_nomal)
    ImageView backNomal;
    @Bind(R.id.tv_Title)
    TextView tvTitle;
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.fag_rlv_newgoods)
    RecyclerView fagRlvNewgoods;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
    }

    @OnClick(R.id.back_nomal)
    public void onClick() {
        this.finish();
    }
}
