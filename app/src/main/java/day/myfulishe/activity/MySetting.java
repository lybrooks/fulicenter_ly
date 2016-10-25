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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
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
        initView();
    }

    @OnClick({R.id.RL_UserAvatar, R.id.LL_UpdateUsername, R.id.LL_UpdateNick, R.id.bt_Quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RL_UserAvatar:
                break;
            case R.id.LL_UpdateUsername:
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
