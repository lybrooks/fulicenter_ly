package myFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;
import day.myfulishe.R;
import day.myfulishe.activity.FuLiCenterApplication;
import day.myfulishe.activity.MainActivity;


public class Fragment_personal extends Fragment {

    @Bind(R.id.iv_personal_avatar)
    ImageView ivPersonalAvatar;
    @Bind(R.id.tv_personal_userName)
    TextView tvPersonalUserName;

    MainActivity mContext;
    UserBean userBean;

    public Fragment_personal() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getActivity();
        initData();
        return view;
    }

    private void initData() {
        userBean = FuLiCenterApplication.getUserBean();
        if (userBean == null) {
            MFGT.gotoLogin(mContext);
        } else {
            ImageLoader.setAcatar(ImageLoader.getAcatarUrl(userBean), mContext, ivPersonalAvatar);
            tvPersonalUserName.setText(userBean.getMuserNick());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_personal_settings, R.id.ll_message})
    public void onClick() {
        MFGT.goSettingActivity(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (userBean != null) {
            ImageLoader.setAcatar(ImageLoader.getAcatarUrl(userBean), mContext, ivPersonalAvatar);
            tvPersonalUserName.setText(userBean.getMuserNick());
        }
    }
}
