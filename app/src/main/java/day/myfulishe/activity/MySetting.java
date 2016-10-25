package day.myfulishe.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.utils.SharedPerfenceUtils;
import day.myfulishe.R;

public class MySetting extends AppCompatActivity {

    @Bind(R.id.iv_User_avatar)
    ImageView ivUserAvatar;
    @Bind(R.id.tv_Username)
    TextView tvUsername;
    @Bind(R.id.iv_update_name)
    ImageView ivUpdateName;
    @Bind(R.id.tv_UserNick)
    TextView tvUserNick;
    @Bind(R.id.iv_update_nick)
    ImageView ivUpdateNick;
    @Bind(R.id.bt_Quit)
    Button btQuit;

    MySetting mContext;
    UserBean userBean;

    OnSetAvatarListener setAvatarListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        userBean = FuLiCenterApplication.getUserBean();
        if (userBean != null) {
            tvUsername.setText(userBean.getMuserName());
            tvUserNick.setText(userBean.getMuserNick());
            ImageLoader.setAcatar(ImageLoader.getAcatarUrl(userBean), mContext, ivUserAvatar);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    private void showInfo() {
        userBean = FuLiCenterApplication.getUserBean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RESULT_OK) {
            return;
        }
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK) {
            CommonUtils.showLongToast("update_user_nick_success");
        }
        setAvatarListener.setAvatar(requestCode, data, ivUserAvatar);
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAavatar();
        }
    }

    private void updateAavatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
                OnSetAvatarListener.getAvatarPath(mContext, userBean.getMavatarPath() + "/" + userBean.getMuserName()
                        + I.AVATAR_SUFFIX_JPG)
        ));
        L.e("file" + file.exists());
        L.e("file" + file.getAbsolutePath());
        NetDao.updateAvatar(mContext, userBean.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserBean.class);
                UserBean userBean = (UserBean) result.getRetData();

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick({R.id.RL_UserAvatar, R.id.LL_UpdateUsername, R.id.LL_UpdateNick, R.id.bt_Quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RL_UserAvatar:
                setAvatarListener = new OnSetAvatarListener(mContext, R.id.Setting, userBean.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.LL_UpdateUsername:
                CommonUtils.showLongToast("用户名不能被更改");
                break;
            case R.id.LL_UpdateNick:
                MFGT.goUpdateNick(mContext);
                break;
            case R.id.bt_Quit:
                if (userBean != null) {
                    SharedPerfenceUtils.getInstance(mContext).removeUser();
                    FuLiCenterApplication.getInstance().setUsernane(null);
                    MFGT.gotoLogin(mContext);
                }
                finish();
                break;
        }
    }
}
