package myFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;


public class Fragment_boutique extends Fragment {


    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.fag_rlv_newgoods)
    RecyclerView fagRlvNewgoods;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    ArrayList<BoutiqueBean> boutiquelist;
    public myDdapter mAdapter;
    public LinearLayoutManager layoutManger;

    public Fragment_boutique() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        boutiquelist = new ArrayList<>();
        mAdapter = new myDdapter(getContext(), boutiquelist);
        layoutManger = new LinearLayoutManager(getContext());
        fagRlvNewgoods.setLayoutManager(layoutManger);
        fagRlvNewgoods.setAdapter(mAdapter);


        initData();
        return view;
    }

    private void initData() {
        NetDao.downloadBoutique(getContext(), new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
                    @Override
                    public void onSuccess(BoutiqueBean[] result) {
                        if (result != null && result.length > 0 && mAdapter.isMore) {
                            ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                            mAdapter.inintContact(list);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                }
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_goods_titile)
        TextView tvGoodsTitile;
        @Bind(R.id.tv_goods_dsc)
        TextView tvGoodsDsc;
        @Bind(R.id.tv_goods_name)
        TextView tvGoodsName;
        @Bind(R.id.iv_bouti)
        ImageView ivBouti;

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

    class myDdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        public myDdapter(Context context, ArrayList<BoutiqueBean> contactList) {
            this.context = context;
            this.contactList = contactList;
        }

        Context context;
        ArrayList<BoutiqueBean> contactList;

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

        public void inintContact(ArrayList<BoutiqueBean> list) {
            this.contactList.clear();
            this.contactList.addAll(list);
            srl.setRefreshing(false);
            srl.setEnabled(false);
            notifyDataSetChanged();
        }

        public void AddContactList(ArrayList<BoutiqueBean> list) {
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
                    layout = inflater.inflate(R.layout.boutique, parent, false);
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
            BoutiqueBean goodsBean = contactList.get(position);
            contactViewHolder.tvGoodsName.setText(goodsBean.getName());
            contactViewHolder.tvGoodsTitile.setText(goodsBean.getTitle());
            contactViewHolder.tvGoodsDsc.setText(goodsBean.getDescription());
            ImageLoader.build(I.DOWNLOAD_IMG_URL + goodsBean.getImageurl())
                    .height(500)
                    .width(400)
                    .imageView(contactViewHolder.ivBouti)
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
    }

    public interface MyOnClickListener {
        void OnClick(View view, int position);
    }
}
