package cn.ucai.fulicenter.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public CartAdapter(Context context, ArrayList<CartBean> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;
    ArrayList<CartBean> contactList;

    public ArrayList<CartBean> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<CartBean> contactList) {
        this.contactList = contactList;
    }

    ViewGroup parent;

    public String getFootertext() {
        return footertext;
    }

    public void setFootertext(String footertext) {
        this.footertext = footertext;
    }

    String footertext;


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public boolean isMore = true;


    public void inintContact(ArrayList<CartBean> list) {

        this.contactList = list;
        notifyDataSetChanged();
    }

    public void setFooter(String footertext) {
        this.footertext = footertext;
        notifyDataSetChanged();
    }

    int count;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHolder(layout);
                break;
            case I.TYPE_ITEM:
                layout = inflater.inflate(R.layout.item_cart, parent, false);
                holder = new ContactViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvItemFooter.setText(footertext);
            return;
        }
        final CartBean goodsBean = contactList.get(position);
        if (goodsBean == null) {
            return;
        } else {
            final ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
            GoodsDetailsBean goods = goodsBean.getGoods();
            if (goods == null) {
                return;
            } else {
                count = goodsBean.getCount();
                contactViewHolder.tvGoodCount.setText("(" + count + ")");
                String s = goods.getCurrencyPrice().substring(goods.getCurrencyPrice().indexOf("￥") + 1);
                final int Money = Integer.parseInt(s);
                contactViewHolder.tvGoodPrize.setText("￥" + Money * count);
                contactViewHolder.tvGoodName.setText(goods.getGoodsName());
                ImageLoader.downloadImg(context, contactViewHolder.ivGoodsImg, goods.getGoodsThumb());
                addCarts(contactViewHolder, Money);
                deteleCarts(goodsBean, contactViewHolder, Money);
            }
        }
    }

    private void deteleCarts(final CartBean goodsBean, final ContactViewHolder contactViewHolder, final int money) {
        contactViewHolder.ivDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                contactViewHolder.tvGoodPrize.setText("￥" + count * money);
                contactViewHolder.tvGoodCount.setText("(" + count + ")");
                if (count == 0) {
                    removethis(goodsBean);
                    deteleCar(goodsBean);
                    return;
                }
            }
        });
    }
    private void addCarts(final ContactViewHolder contactViewHolder, final int money) {
        contactViewHolder.ivAddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                contactViewHolder.tvGoodPrize.setText("￥" + count * money);
                contactViewHolder.tvGoodCount.setText("(" + count + ")");
            }
        });
    }
    private void deteleCar(CartBean goodsBean) {
        NetDao.deleteCart(getContext(), goodsBean.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result.isSuccess()) {
                    CommonUtils.showShortToast("删除成功");
                }
            }
            @Override
            public void onError(String error) {
                CommonUtils.showShortToast("删除失败");
            }
        });
    }

    private void removethis(CartBean goodsBean) {
        this.contactList.remove(goodsBean);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }


    public interface MyOnClickListener {
        void OnClick(View view, int position);
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_footer)
        TextView tvItemFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_goods_img)
        ImageView ivGoodsImg;
        @Bind(R.id.tv_good_name)
        TextView tvGoodName;
        @Bind(R.id.iv_addcart)
        ImageView ivAddcart;
        @Bind(R.id.tv_good_count)
        TextView tvGoodCount;
        @Bind(R.id.iv_deleteCart)
        ImageView ivDeleteCart;
        @Bind(R.id.tv_good_prize)
        TextView tvGoodPrize;
        @Bind(R.id.RL_Catr)
        RelativeLayout RLCatr;

        ContactViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
