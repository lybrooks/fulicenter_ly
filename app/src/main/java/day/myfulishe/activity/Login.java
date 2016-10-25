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
import cn.ucai.fulicenter.I;
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
                NetDao.Login(this, user, password, new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Result result = ResultUtils.getResultFromJson(s, UserBean.class);
                        if (result == null) {
                            CommonUtils.showLongToast("登录失败");
                        } else {
                            if (result.isRetMsg()) {
                                UserBean user = (UserBean) result.getRetData();
                                UserDao dao = new UserDao(mContext);
                                dao.savaUser(user);
                                boolean isSuccess =dao.savaUser(user);
                                if (isSuccess){
                                    SharedPerfenceUtils.getInstance(mContext).saveuser(user.getMuserName());
                                    FuLiCenterApplication.getInstance().setUserBean(user);
                                    MFGT.finish(mContext);
                                }else {
                                        CommonUtils.showLongToast("user_database_error");
                                }

                                L.e("user" + user);
                                MFGT.finish(mContext);
                            } else {
                                if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                    CommonUtils.showLongToast("login_fail_unknown_user");
                                } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                    CommonUtils.showLongToast("login_fail_error_password");
                                } else {
                                    CommonUtils.showLongToast("login_fail");
                                }
                            }
                        }
                        pb.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        L.e("Login:" + error);
                        pb.dismiss();
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
}
