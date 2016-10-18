package day.myfulishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import day.myfulishe.R;

public class Deails extends AppCompatActivity {

    @Bind(R.id.tv_good_EnglishName)
    TextView tvGoodEnglishName;
    @Bind(R.id.tv_good_deails_name)
    TextView tvGoodDeailsName;
    @Bind(R.id.tv_good_Color)
    TextView tvGoodColor;
    @Bind(R.id.tv_good_prize)
    TextView tvGoodPrize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deails);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        tvGoodPrize.setText(intent.getStringExtra("Price"));
        tvGoodEnglishName.setText(intent.getStringExtra("EnglishName"));
        tvGoodColor.setText(intent.getStringExtra("colorName"));
        tvGoodDeailsName.setText(intent.getStringExtra("goodName"));
    }
}
