package cn.ucai.fulicenter.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import day.myfulishe.R;

/**
 * Created by Administrator on 2016/10/18.
 */


  public class newAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public newAdapter(Context context, ArrayList<NewGoodsBean> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    Context context;
    public ArrayList<NewGoodsBean> contactList;

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

    public MyOnClickListener getMyOnClick() {
        return myOnClick;
    }

    public void setMyOnClick(MyOnClickListener myOnClick) {
        this.myOnClick = myOnClick;
    }

    MyOnClickListener myOnClick;

    public void inintContact(ArrayList<NewGoodsBean> list) {
        this.contactList.clear();
        this.contactList.addAll(list);

        notifyDataSetChanged();
    }

    public void AddContactList(ArrayList<NewGoodsBean> list) {
        this.contactList.addAll(list);
        notifyDataSetChanged();
    }

    public void setFooter(String footertext) {
        this.footertext = footertext;
        notifyDataSetChanged();
    }

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
                layout = inflater.inflate(R.layout.item_contact, parent, false);
                holder = new ContactViewHolder(layout, myOnClick);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.mtvFooter.setText(footertext);
            return;
        }
        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        NewGoodsBean goodsBean = contactList.get(position);
        contactViewHolder.Goods_name.setText(goodsBean.getGoodsName());
        contactViewHolder.Goods_prize.setText(goodsBean.getCurrencyPrice());
        ImageLoader.build(I.DOWNLOAD_IMG_URL + goodsBean.getGoodsThumb())
                .height(200)
                .width(200)
                .imageView(contactViewHolder.Googs_img)
                .listener(parent)
                .showImage(context);
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


    class ContactViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_goods_img)
        ImageView Googs_img;
        @Bind(R.id.tv_good_name)
        TextView Goods_name;
        @Bind(R.id.tv_good_prize)
        TextView Goods_prize;
        MyOnClickListener MyOnClick;

        ContactViewHolder(View view, final MyOnClickListener MyonClick) {
            super(view);
            ButterKnife.bind(this, view);
            this.MyOnClick = MyonClick;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyOnClick != null) {
                        MyOnClick.OnClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_footer)
        TextView mtvFooter;
        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface MyOnClickListener {
        void OnClick(View view, int position);
    }
}


