package day.myfulishe.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ucai.fulicenter.utils.L;
import day.myfulishe.R;
import myFragment.fragment_boutique;
import myFragment.fragment_cart;
import myFragment.fragment_category;
import myFragment.fragment_newgoods;
import myFragment.fragment_personal;

public class MainActivity extends AppCompatActivity {
    RadioButton mrb_item_newgood, mrb_item_boutique, mrb_item_category, mrb_item_cart, mrb_item_personal;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    // public static SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    ViewPager mVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        fragment_newgoods newgoods = new fragment_newgoods();
        fragment_boutique boutique = new fragment_boutique();
        fragment_category category = new fragment_category();
        fragment_cart cart = new fragment_cart();
        fragment_personal personal = new fragment_personal();
        fragmentArrayList.add(newgoods);
        fragmentArrayList.add(boutique);
        fragmentArrayList.add(category);
        fragmentArrayList.add(cart);
        fragmentArrayList.add(personal);

        mVP = (ViewPager) findViewById(R.id.main_fragment);
        fragmentManager = getSupportFragmentManager();
        MyViewPage VP_Adapter = new MyViewPage(fragmentManager, fragmentArrayList);
        mVP.setAdapter(VP_Adapter);

        mrb_item_newgood = (RadioButton) findViewById(R.id.rb_item_newgood);
        mrb_item_boutique = (RadioButton) findViewById(R.id.rb_item_boutique);
        mrb_item_category = (RadioButton) findViewById(R.id.rb_item_category);
        mrb_item_cart = (RadioButton) findViewById(R.id.rb_item_cart);
        mrb_item_personal = (RadioButton) findViewById(R.id.rb_item_personal);

        //      mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);


    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rb_item_newgood:
                Change((RadioButton) v);
                mVP.setCurrentItem(0);
                break;
            case R.id.rb_item_boutique:
                Change((RadioButton) v);
                mVP.setCurrentItem(1);
                break;
            case R.id.rb_item_category:
                Change((RadioButton) v);
                mVP.setCurrentItem(2);
                break;
            case R.id.rb_item_cart:
                Change((RadioButton) v);
                mVP.setCurrentItem(3);
                break;
            case R.id.rb_item_personal:
                Change((RadioButton) v);
                mVP.setCurrentItem(4);
                break;


        }


    }

    private void Change(RadioButton rb) {
        if (rb != mrb_item_boutique) {
            mrb_item_boutique.setChecked(false);
        }
        if (rb != mrb_item_category) {
            mrb_item_category.setChecked(false);
        }
        if (rb != mrb_item_newgood) {
            mrb_item_newgood.setChecked(false);
        }
        if (rb != mrb_item_personal) {
            mrb_item_personal.setChecked(false);
        }
        if (rb != mrb_item_cart) {
            mrb_item_cart.setChecked(false);
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
    }
}
