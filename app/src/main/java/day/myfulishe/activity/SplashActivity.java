package day.myfulishe.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.bean.UserBean;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.SharedPerfenceUtils;
import day.myfulishe.R;

public class SplashActivity extends AppCompatActivity {
    final long splashtime = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onStart() {

        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final long start = System.currentTimeMillis();
                long costtime = System.currentTimeMillis() - start;
                if (splashtime - costtime > 0) {
                    try {
                        Thread.sleep(splashtime - costtime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    UserBean user = FuLiCenterApplication.getUserBean();
                    L.e("fulicentener,user=" + user);
                    String username = SharedPerfenceUtils.getInstance(mContext).getUser();
                    L.e("fulicentener,username=" + username);
                    if (user == null && username != null) {
                        UserDao dao = new UserDao(mContext);
                        user = dao.getUser(username);
                        L.e("database,user=" + user);
                        if (user != null) {
                            FuLiCenterApplication.getInstance().setUserBean(user);
                        }
                    }
                    MFGT.gotoMainActivity(SplashActivity.this);
                    finish();
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MFGT.finish(SplashActivity.this);
    }
}
