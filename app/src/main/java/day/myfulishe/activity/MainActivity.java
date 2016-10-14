package day.myfulishe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import day.myfulishe.R;

public class MainActivity extends AppCompatActivity {
    RadioButton mrb_item_newgood, mrb_item_boutique, mrb_item_category, mrb_item_cart, mrb_item_personal;

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
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.rb_item_newgood:
                Change((RadioButton) v);
                break;
            case R.id.rb_item_boutique:
                Change((RadioButton) v);
                break;
            case R.id.rb_item_category:
                Change((RadioButton) v);
                break;
            case R.id.rb_item_cart:
                Change((RadioButton) v);
                break;
            case R.id.rb_item_personal:
                Change((RadioButton) v);
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
