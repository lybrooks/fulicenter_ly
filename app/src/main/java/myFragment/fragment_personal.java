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
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;
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
    @Bind(R.id.tv_personal_countOfCollections)
    TextView tvPersonalCountOfCollections;

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

    public void downCollectCounts() {
        NetDao.downloadCollecCounts(mContext, userBean.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.getMsg() == null && !result.isSuccess()) {
                    tvPersonalCountOfCollections.setText(String.valueOf(0));
                } else {
                    tvPersonalCountOfCollections.setText(String.valueOf(result.getMsg()));
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void updateUserMessages() {
        NetDao.findUserByUserName(mContext, userBean.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserBean.class);
                if(result!=null){
                    UserBean U = (UserBean) result.getRetData();
                    if(userBean.equals(U)){
                        UserDao dao = new UserDao(mContext);
                        boolean b=dao.savaUser(U);
                        if(b){
                            FuLiCenterApplication.getInstance().setUserBean(U);
                            userBean=U;
                            ImageLoader.setAcatar(ImageLoader.getAcatarUrl(userBean),mContext,ivPersonalAvatar);
                            tvPersonalUserName.setText(userBean.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
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

    @OnClick(R.id.RL_CollectCounts)
    public  void goCollection(){
        MFGT.gotoCollection(mContext);
    }

    @Override
    public void onResume() {
        super.onResume();
        userBean = FuLiCenterApplication.getUserBean();
        if (userBean != null) {
            ImageLoader.setAcatar(ImageLoader.getAcatarUrl(userBean), mContext, ivPersonalAvatar);
            tvPersonalUserName.setText(userBean.getMuserNick());
            L.e("personal:"+userBean.getMuserNick());
            downCollectCounts();
            updateUserMessages();
        }
    }

}
