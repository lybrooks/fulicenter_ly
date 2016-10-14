package day.myfulishe.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mrb_item_newgood = (RadioButton) findViewById(R.id.rb_item_newgood);
        mrb_item_boutique = (RadioButton) findViewById(R.id.rb_item_boutique);
        mrb_item_category = (RadioButton) findViewById(R.id.rb_item_category);
        mrb_item_cart = (RadioButton) findViewById(R.id.rb_item_cart);
        mrb_item_personal = (RadioButton) findViewById(R.id.rb_item_personal);
        fragmentManager = getSupportFragmentManager();
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rb_item_newgood:
                Change((RadioButton) v);
                fragment_newgoods newgoods = new fragment_newgoods();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.Linear, newgoods);
                transaction.commit();
                break;
            case R.id.rb_item_boutique:
                Change((RadioButton) v);
                fragment_boutique boutique = new fragment_boutique();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.Linear, boutique);
                transaction.commit();
                break;
            case R.id.rb_item_category:
                Change((RadioButton) v);
                fragment_category category = new fragment_category();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.Linear, category);
                transaction.commit();
                break;
            case R.id.rb_item_cart:
                Change((RadioButton) v);
                fragment_cart cart = new fragment_cart();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.Linear, cart);
                transaction.commit();
                break;
            case R.id.rb_item_personal:
                Change((RadioButton) v);
                fragment_personal personal = new fragment_personal();
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.Linear, personal);
                transaction.commit();
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
}
