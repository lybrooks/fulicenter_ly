package day.myfulishe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
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
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.utils.SharedPerfenceUtils;
import day.myfulishe.R;

public class UpdateNick extends AppCompatActivity {

    @Bind(R.id.et_nick)
    EditText etNick;

    String muserName;
    String usernick;

    UpdateNick mContext;
    UserBean userBean;

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
        userBean = FuLiCenterApplication.getUserBean();
        if (userBean != null) {
            muserName = userBean.getMuserName();
            usernick = userBean.getMuserNick();
        } else {
            finish();
        }
    }

    private void initData() {
        etNick.setText(usernick);
    }

    @OnClick(R.id.btn_save)
    public void onClick() {

        if (userBean != null) {
            usernick = etNick.getText().toString();
            if (usernick == null) {
                CommonUtils.showLongToast("昵称不能为空");
                return;
            } else {
                if (usernick.equals(userBean.getMuserNick())) {
                    CommonUtils.showLongToast("未作出任何修改");
                    return;
                } else {
                    updateNick(usernick);
                    return;
                }
            }
        }

    }

    private void updateNick(String usernick) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.show();
        NetDao.updateNick(mContext, muserName, usernick, new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Result result = ResultUtils.getResultFromJson(s, UserBean.class);
                        L.e("UpdateNick:"+result.toString());
                        if (result == null) {
                            CommonUtils.showLongToast("修改昵称失败");
                        } else {
                            if (result.isRetMsg()) {
                                L.e(result.toString());
                                UserBean U = (UserBean) result.getRetData();
                                UserDao dao = new UserDao(mContext);
                                boolean isSuccess = dao.update(U);
                                if (isSuccess) {
                                    FuLiCenterApplication.getInstance().setUserBean(U);
                                    L.e("Update:FuLiCenterApplication" + U.toString());
                                    CommonUtils.showShortToast("修改昵称成功");
                                    MFGT.finish(mContext);
                                } else {
                                    if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                        CommonUtils.showLongToast("update_fail");
                                    } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                        CommonUtils.showLongToast("update_fail");
                                    } else {
                                        CommonUtils.showLongToast("update_fail");
                                    }
                                }

                            }

                        }
                        pd.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast("Update:" + error);
                    }
                }
        );
    }
}
