package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.utils.ImageLoader;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        String muserName = FuLiCenterApplication.getUserBean().getMuserName();
        tvUsername.setText(muserName);
        String muserNick = FuLiCenterApplication.getUserBean().getMuserNick();
        tvUserNick.setText(muserNick);
        ImageLoader.setAcatar(ImageLoader.getAcatarUrl(FuLiCenterApplication.getUserBean()), mContext, ivUserAvatar);
    }
}
