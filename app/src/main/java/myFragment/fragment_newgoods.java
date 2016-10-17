package myFragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;
import day.myfulishe.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_newgoods extends Fragment {


    public GridLayoutManager layoutManger;
    ArrayList<NewGoodsBean> NewGoodsBeanlist;
    public myDdapter mAdapter;
    public RecyclerView mrv;

    int mNewState;
    int PageId = 1;


    public Fragment_newgoods() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newgoods, container, false);
        initView(view);
        ButterKnife.bind(this, view);
        initData(I.ACTION_DOWNLOAD, PageId);
        setListener();
        return view;
    }

    private void initView(View view) {
        mrv = (RecyclerView) view.findViewById(R.id.fag_rlv_newgoods);
        NewGoodsBeanlist = new ArrayList<>();
        mAdapter = new myDdapter(getContext(), NewGoodsBeanlist);
        layoutManger = new GridLayoutManager(getContext(), I.COLUM_NUM, GridLayoutManager.VERTICAL, false);
        layoutManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        mrv.setLayoutManager(layoutManger);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
    }

    private void initData(final int action, int PageId) {
        NetDao.downloadNewGoods(getContext(), PageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0 && mAdapter.isMore) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    L.i(list.toString());
                    switch (action) {
                        case I.ACTION_DOWNLOAD:
                            mAdapter.setFooter("加载更多数据");
                            mAdapter.inintContact(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            mAdapter.inintContact(list);
                            break;
                        case I.ACTION_PULL_UP:
                            mAdapter.AddContactList(list);
                            break;
                    }
                }else {
                        mAdapter.setFooter("没有更多数据了");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
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
                    L.i(PageId + "");
                    initData(I.ACTION_PULL_UP, PageId);
                }
            }

        });

        mAdapter.setMyOnClick(new MyOnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("商品信息").create().show();
            }
        });

    }

/*    public void downloadContactList(final int action, int pageId) {
        final OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(getContext());
        utils.url(I.SERVER_ROOT + I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.GoodsDetails.KEY_CAT_ID, String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        ArrayList<NewGoodsBean> goodsBeen = utils.array2List(result);
                        mAdapter.setMore(goodsBeen != null && goodsBeen.size() != 0);
                        if (!mAdapter.isMore) {
                            if (action == I.ACTION_PULL_UP) {
                                mAdapter.setFooter("没有更多数据了");
                            }
                            return;
                        }
                        mAdapter.setFooter("加载更多数据");
                        switch (action) {
                            case I.ACTION_DOWNLOAD:
                                mAdapter.setFooter("加载更多数据");
                                mAdapter.inintContact(goodsBeen);
                                break;
                            case I.ACTION_PULL_DOWN:
                                mAdapter.inintContact(goodsBeen);
                                break;
                            case I.ACTION_PULL_UP:
                                mAdapter.AddContactList(goodsBeen);
                                break;
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

/*    class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView Googs_img;
        TextView Goods_name, Goods_prize;

        public ContactViewHolder(View itemView) {
            super(itemView);
            Googs_img = (ImageView) itemView.findViewById(R.id.iv_goods_img);
            Goods_name = (TextView) itemView.findViewById(R.id.tv_good_name);
            Goods_prize = (TextView) itemView.findViewById(R.id.tv_good_prize);
        }
    }*/

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
                    if (MyOnClick == null) {
                        MyOnClick.OnClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    /*   class FooterViewHolder extends RecyclerView.ViewHolder {
           TextView mtvFooter;

           public FooterViewHolder(View itemView) {
               super(itemView);
               mtvFooter = (TextView) itemView.findViewById(R.id.tv_item_footer);
           }
       }*/
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_footer)
        TextView mtvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class myDdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    }

    public interface MyOnClickListener {
        void OnClick(View view, int position);
    }


}
