package myFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;
import day.myfulishe.activity.MainActivity;
import day.myfulishe.activity.Regist;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Login extends Fragment {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.Et_UserName)
    EditText EtUserName;
    @Bind(R.id.ET_Password)
    EditText ETPassword;
    @Bind(R.id.btn_Login)
    Button btnLogin;
    @Bind(R.id.btn_regist)
    Button btnRegist;

    public Fragment_Login() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment__login, container, false);
        ButterKnife.bind(this, view);
        initData();
        //  String username = getActivity().getIntent().getStringExtra("Username");
        //L.e(username);
        return view;
    }

    private void initData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_Login, R.id.btn_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MFGT.finish((Activity) getContext());
                break;
            case R.id.btn_Login:
                String user = EtUserName.getText().toString().trim();
                String password = ETPassword.getText().toString().trim();
                NetDao.Login(getContext(), user, password, new OkHttpUtils.OnCompleteListener<UserAvatar>() {
                    @Override
                    public void onSuccess(UserAvatar result) {
                        if (result == null) {
                            CommonUtils.showShortToast("登录失败");
                        } else {
                            CommonUtils.showShortToast("登录成功");
                            MainActivity.isLogin = true;
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(getContext(), Regist.class);
                startActivity(intent);
                break;
        }
    }


}
