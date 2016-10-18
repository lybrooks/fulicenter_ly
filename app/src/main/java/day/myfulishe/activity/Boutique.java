package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
