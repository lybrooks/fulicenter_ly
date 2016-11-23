package controller.activity;

import android.app.Application;

import cn.ucai.fulicenter.bean.UserBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;
    public static FuLiCenterApplication application;

    public String getUsernane() {
        return Usernane;
    }

    public void setUsernane(String usernane) {
        Usernane = usernane;
    }

    String Usernane;

    public static UserBean getUserBean() {
        return userBean;
    }

    public  void setUserBean(UserBean userBean) {
     this.userBean=userBean;
    }

    public static UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
}
