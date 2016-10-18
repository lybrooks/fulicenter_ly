package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.I;
import day.myfulishe.activity.Boutique;
import day.myfulishe.activity.Deails;
import day.myfulishe.activity.MainActivity;
import day.myfulishe.R;


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
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,Goodsid);
        startActivity(context, intent);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }



}
