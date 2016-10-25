package day.myfulishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.utils.SharedPerfenceUtils;
import day.myfulishe.R;

public class Login extends AppCompatActivity {


    @Bind(R.id.Et_UserName)
    EditText EtUserName;
    @Bind(R.id.ET_Password)
    EditText ETPassword;

    Login mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EtUserName.setText(getIntent().getStringExtra("username"));
        mContext = this;
    }


    @OnClick({R.id.iv_back, R.id.btn_Login, R.id.btn_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish(this);
                break;
            case R.id.btn_Login:
                final ProgressDialog pb = new ProgressDialog(mContext);
                pb.show();
                final String user = EtUserName.getText().toString().trim();
                String password = ETPassword.getText().toString().trim();
                NetDao.Login(this, user, password, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        if (result !=null&&result.getRetCode()==0) {
                            pb.dismiss();
                            L.e(result.toString());
                            String s1 = result.getRetData().toString();
                            UserBean user = new Gson().fromJson(s1, UserBean.class);
                            UserDao dao = new UserDao(mContext);
                            boolean isSuccess = dao.savaUser(user);
                            if (isSuccess) {
                                SharedPerfenceUtils.getInstance(mContext).saveuser(user.getMuserName());
                                L.e("Login:"+user.getMuserName());
                                FuLiCenterApplication.getInstance().setUserBean(user);
                                L.e("Login:FuLiCenterApplication"+user.toString());
                                CommonUtils.showShortToast("登录成功");
                            } else {
                                CommonUtils.showLongToast("user_database_error");
                            }
                            MFGT.finish(Login.this);

                        } else {
                            CommonUtils.showShortToast("登录失败");
                            pb.dismiss();
                            return;
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e("Login:"+error);
                    }
                });
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(this, Regist.class);
                startActivity(intent);
                MFGT.finish(mContext);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MFGT.finish(mContext);
    }
    /*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==1&&resultCode==RESULT_OK){
             EditText.
         }
    }*/
}
