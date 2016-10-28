package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import day.myfulishe.R;

public class BuyActivity extends AppCompatActivity {


    @Bind(R.id.Et_UserName)
    EditText EtUserName;
    @Bind(R.id.ET_phoneNumber)
    EditText ETPhoneNumber;
    @Bind(R.id.Spin_city)
    Spinner SpinCity;
    @Bind(R.id.ET_Address)
    EditText ETAddress;
    @Bind(R.id.tv_sumPrizes)
    TextView tvSumPrizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        initView();
        initDara();
    }

    private void initDara() {
        String cartIds = getIntent().getStringExtra(I.Cart.ID);
    }

    private void initView() {
        checkOrder();

    }

    private void checkOrder() {
        String ETname = EtUserName.getText().toString();
        if (TextUtils.isEmpty(ETname)) {
            EtUserName.setError("收货人姓名不能为空");
            EtUserName.requestFocus();
        }
        String ETphone = ETPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(ETphone)) {
            ETPhoneNumber.setError("手机号码不能为空");
            ETPhoneNumber.requestFocus();
        }
        if (!ETphone.matches("[\\d]{11}")) {
            ETPhoneNumber.setError("手机号码格式错误");
            ETPhoneNumber.requestFocus();
        }
        String area = SpinCity.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(BuyActivity.this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = ETAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            ETAddress.setError("街道地址不能为空");
            ETAddress.requestFocus();
            return;
        }

    }

    @OnClick(R.id.btn_pay)
    public void onClick() {
    }
}
