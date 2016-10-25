package day.myfulishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.SharedPerfenceUtils;
import day.myfulishe.R;

public class UpdateNick extends AppCompatActivity {

    @Bind(R.id.et_nick)
    EditText etNick;

    String muserName;
    String usernick;

    UpdateNick mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        muserName = FuLiCenterApplication.getUserBean().getMuserName();
        usernick = FuLiCenterApplication.getUserBean().getMuserNick();
    }

    private void initData() {
        etNick.setText(usernick);
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        usernick =etNick.getText().toString();
        final ProgressDialog pd = new ProgressDialog(mContext);
        NetDao.updateNick(mContext, muserName, usernick, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        if (result != null && result.getRetCode() == 0) {
                            pd.dismiss();
                            L.e(result.toString());
                            String s1 = result.getRetData().toString();
                            UserBean user = new Gson().fromJson(s1, UserBean.class);
                            UserDao dao = new UserDao(mContext);
                            boolean isSuccess = dao.update(user);
                            if (isSuccess) {
                                SharedPerfenceUtils.getInstance(mContext).saveuser(user.getMuserName());
                                L.e("Update:" + user.getMuserName());
                                FuLiCenterApplication.getInstance().setUserBean(user);
                                L.e("Update:FuLiCenterApplication" + user.toString());
                                CommonUtils.showShortToast("修改昵称成功");
                            } else {
                                CommonUtils.showLongToast("user_database_error");
                            }
                        } else {
                            CommonUtils.showShortToast("修改昵称失败");
                            pd.dismiss();
                        }
                        MFGT.finish(mContext);
                    }

                    @Override
                    public void onError(String error) {

                    }
                }

        );

    }
}
