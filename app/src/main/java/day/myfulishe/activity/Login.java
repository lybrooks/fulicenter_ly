package day.myfulishe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;

public class Login extends AppCompatActivity {


    @Bind(R.id.Et_UserName)
    EditText EtUserName;
    @Bind(R.id.ET_Password)
    EditText ETPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EtUserName.setText(getIntent().getStringExtra("username"));
    }


    @OnClick({R.id.iv_back, R.id.btn_Login, R.id.btn_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish(this);
                break;
            case R.id.btn_Login:
                String user = EtUserName.getText().toString().trim();
                String password = ETPassword.getText().toString().trim();
                NetDao.Login(this, user, password, new OkHttpUtils.OnCompleteListener<UserAvatar>() {
                    @Override
                    public void onSuccess(UserAvatar result) {
                        if (result == null) {
                            CommonUtils.showShortToast("登录失败");
                        } else {
                            CommonUtils.showShortToast("登录成功");
                            MainActivity.isLogin = true;
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("index", 4);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(this, Regist.class);
                startActivity(intent);
                break;
        }
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==1&&resultCode==RESULT_OK){
             EditText.
         }
    }*/
}
