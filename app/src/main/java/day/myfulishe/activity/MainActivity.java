package day.myfulishe.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import day.myfulishe.R;
import myFragment.Fragment_Login;
import myFragment.Fragment_boutique;
import myFragment.Fragment_cart;
import myFragment.Fragment_category;
import myFragment.Fragment_newgoods;
import myFragment.Fragment_personal;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    @Bind(R.id.main_fragment)
    ViewPager mVP;
    @Bind(R.id.rb_item_newgood)
    RadioButton mrb_item_newgood;
    @Bind(R.id.rb_item_boutique)
    RadioButton mrb_item_boutique;
    @Bind(R.id.rb_item_category)
    RadioButton mrb_item_category;
    @Bind(R.id.rb_item_cart)
    RadioButton mrb_item_cart;
    @Bind(R.id.rb_item_personal)
    RadioButton mrb_item_personal;

    RadioButton[] mrb;
    int index;
    public static boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();

        int intExtra = getIntent().getIntExtra("index", 0);
        L.i("index:"+intExtra+"");
        if (intExtra != 0) {
            index = intExtra;
            mVP.setCurrentItem(index);
        }

    }

    private void initFragment() {


    }


    private void initView() {
        mrb = new RadioButton[5];
        mrb[0] = mrb_item_newgood;
        mrb[1] = mrb_item_boutique;
        mrb[2] = mrb_item_category;
        mrb[3] = mrb_item_cart;
        mrb[4] = mrb_item_personal;


        Fragment_newgoods newgoods = new Fragment_newgoods();
        Fragment_boutique boutique = new Fragment_boutique();
        Fragment_category category = new Fragment_category();
        Fragment_cart cart = new Fragment_cart();
        Fragment_personal personal = new Fragment_personal();
        //Fragment_Login login = new Fragment_Login();


        fragmentArrayList.add(newgoods);
        fragmentArrayList.add(boutique);
        fragmentArrayList.add(category);
        fragmentArrayList.add(cart);
//        if (isLogin) {
        fragmentArrayList.add(personal);
//        } else {
        //        fragmentArrayList.add(login);
//      }
        fragmentManager = getSupportFragmentManager();

        MyViewPage VP_Adapter = new MyViewPage(fragmentManager, fragmentArrayList);
/*        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        mVP.setAdapter(VP_Adapter);


    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rb_item_newgood:
                index = 0;
                break;
            case R.id.rb_item_boutique:
                index = 1;
                break;
            case R.id.rb_item_category:
                index = 2;
                break;
            case R.id.rb_item_cart:
                index = 3;
                break;
            case R.id.rb_item_personal:
                index = 4;
                break;
        }
        setViewPageAndRadioButtonStatus();
        setViewPage();
    }

    private void setViewPage() {
        for (int i = 0; i < mrb.length; i++) {
            if (i == index) {
                if (i == mrb.length - 1 && !isLogin) {
                    MFGT.startActivity(this, Login.class);
                    return;
                }
                mVP.setCurrentItem(i);
            }

        }
    }


    private void setViewPageAndRadioButtonStatus() {
        for (int i = 0; i < mrb.length; i++) {
            if (i == index) {
                //  mVP.setCurrentItem(i);
                mrb[i].setChecked(true);
            } else {
                mrb[i].setChecked(false);
            }
        }

    }


    class MyViewPage extends FragmentPagerAdapter {
        ArrayList<Fragment> arrayList;

        public MyViewPage(FragmentManager fm, ArrayList<Fragment> arrayList) {
            super(fm);
            this.arrayList = arrayList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            getSupportFragmentManager().beginTransaction().hide(fragmentArrayList.get(position));
        }
    }

}
