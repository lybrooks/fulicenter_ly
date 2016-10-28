package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import day.myfulishe.R;

public class BuyActivity extends AppCompatActivity {

    @Bind(R.id.Spin_city)
    Spinner SpinCity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }
}
