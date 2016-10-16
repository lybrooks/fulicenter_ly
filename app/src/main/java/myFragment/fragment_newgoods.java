package myFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_newgoods extends Fragment {

    final static int ACTION_DOWNLOAD = 0;
    final static int ACTION_PULL_DOWN = 1;
    public final static int ACTION_PULL_UP = 2;
    public GridLayoutManager layoutManger;
    ArrayList<NewGoodsBean> NewGoodsBeanlist;
    public myDdapter mAdapter;
    public RecyclerView mrv;

    int mNewState;
    int PageId = 1;

    public fragment_newgoods() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newgoods, container, false);
        mrv = (RecyclerView) view.findViewById(R.id.fag_rlv_newgoods);
        NewGoodsBeanlist = new ArrayList<>();
        mAdapter = new myDdapter(getContext(), NewGoodsBeanlist);
        layoutManger = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        layoutManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        downloadContactList(ACTION_DOWNLOAD, PageId);
        mrv.setLayoutManager(layoutManger);
        mrv.setAdapter(mAdapter);
        setListener();
        return view;
    }

    private void setListener() {
        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int Lastposition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mNewState = newState;
                Lastposition = layoutManger.findLastVisibleItemPosition();
                if (Lastposition >= mAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE &&
                        mAdapter.isMore) {
                    PageId++;
                    downloadContactList(fragment_newgoods.ACTION_PULL_UP, PageId);
                }
            }

        });
    }

    public void downloadContactList(final int action, int pageId) {
        final OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>();
        utils.url(I.SERVER_ROOT + I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.GoodsDetails.KEY_CAT_ID, 0 + "")
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, 10 + "")
                .targetClass(NewGoodsBean[].class)
                .execute(new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        ArrayList<NewGoodsBean> goodsBeen = utils.array2List(result);
                        mAdapter.setMore(goodsBeen != null && goodsBeen.size() != 0);
                        if (!mAdapter.isMore) {
                            if (action == ACTION_PULL_UP) {
                                mAdapter.setFooter("没有更多数据了");
                            }
                            return;
                        }
                        mAdapter.setFooter("加载更多数据");
                        switch (action) {
                            case ACTION_DOWNLOAD:
                                mAdapter.setFooter("加载更多数据");

                                mAdapter.inintContact(goodsBeen);
                                break;
                            case ACTION_PULL_DOWN:
                                mAdapter.inintContact(goodsBeen);
                                break;
                            case ACTION_PULL_UP:
                                mAdapter.AddContactList(goodsBeen);
                                break;
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView Googs_img;
        TextView Goods_name, Goods_prize;

        public ContactViewHolder(View itemView) {
            super(itemView);
            Googs_img = (ImageView) itemView.findViewById(R.id.iv_goods_img);
            Goods_name = (TextView) itemView.findViewById(R.id.tv_good_name);
            Goods_prize = (TextView) itemView.findViewById(R.id.tv_good_prize);

        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView mtvFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mtvFooter = (TextView) itemView.findViewById(R.id.tv_item_footer);
        }
    }

    class myDdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final static int TYPE_ITEM = 0;
        final static int TYPE_FOOTER = 1;

        public myDdapter(Context context, ArrayList<NewGoodsBean> contactList) {
            this.context = context;
            this.contactList = contactList;
        }

        Context context;
        ArrayList<NewGoodsBean> contactList;

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

        public boolean isMore;


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
            L.i("aaaaa");
            this.parent = parent;
            RecyclerView.ViewHolder holder = null;
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = null;
            switch (viewType) {
                case TYPE_FOOTER:
                    layout = inflater.inflate(R.layout.item_footer, parent, false);
                    holder = new FooterViewHolder(layout);
                    break;
                case TYPE_ITEM:
                    layout = inflater.inflate(R.layout.item_contact, parent, false);
                    holder = new ContactViewHolder(layout);
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
            ImageLoader.build(I.SERVER_ROOT + I.REQUEST_DOWNLOAD_IMAGE)
                    .addParam(I.IMAGE_URL, goodsBean.getGoodsThumb())
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
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;

        }
    }


}
