package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import day.myfulishe.activity.Boutique;
import day.myfulishe.activity.CategoryDetails;
import day.myfulishe.activity.Collections;
import day.myfulishe.activity.Deails;
import day.myfulishe.activity.Login;
import day.myfulishe.activity.MainActivity;
import day.myfulishe.R;
import day.myfulishe.activity.MySetting;
import day.myfulishe.activity.Regist;
import day.myfulishe.activity.UpdateNick;


public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoGoodsDtails(Activity context, int Goodsid) {
        Intent intent = new Intent(context, Deails.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, Goodsid);
        startActivity(context, intent);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoCategoryDtails(Activity context, int id, ArrayList<CategoryChildBean> list, String name) {
        Intent intent = new Intent(context, CategoryDetails.class);
        intent.putExtra(I.CategoryChild.CAT_ID, id);
        intent.putExtra("childList", list);
        intent.putExtra(I.CategoryGroup.NAME, name);
        startActivity(context, intent);
    }

    public static void gotoBoutique(Activity context, String Title, int cariId) {
        Intent intent = new Intent(context, Boutique.class);
        intent.putExtra("title", Title);
        intent.putExtra("cartId", cariId);
        startActivity(context, intent);

    }

    public static void gotoRegister(Activity context) {
        // Intent intent = new Intent(context, Regist.class);
        // startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
    }


    public static void gotoLogin(Activity mContext) {
        Intent intent = new Intent();
        intent.setClass(mContext, Login.class);
        startActivityForResult(mContext, intent, I.REQUEST_CODE_LOGIN);
    }

    private static void startActivityForResult(Activity mContext, Intent intent, int requestCode) {
        mContext.startActivityForResult(intent, requestCode);
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void goSettingActivity(Activity mContext) {
        Intent intent = new Intent();
        intent.setClass(mContext, MySetting.class);
        startActivity(mContext, intent);

    }

    public static void goUpdateNick(Activity mContext) {

        startActivityForResult(mContext, new Intent(mContext, UpdateNick.class), I.REQUEST_CODE_NICK);
    }

    public static void gotoCollection(Activity comtext) {
        startActivity(comtext, Collections.class);
    }
}
