package cn.ucai.fulicenter.myAdapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import day.myfulishe.R;
import day.myfulishe.activity.CategoryDetails;
import myFragment.Fragment_category;


/**
 * Created by Administrator on 2016/10/19.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<CategoryGroupBean> getGrouplist() {
        return Grouplist;
    }

    public void setGrouplist(ArrayList<CategoryGroupBean> grouplist) {
        Grouplist = grouplist;
    }

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> grouplist, ArrayList<ArrayList<CategoryChildBean>> childs, ExpandableListView ELV) {

        this.context = context;
        Grouplist = grouplist;
        this.childs = childs;
        this.ELV = ELV;
        initChilds();
        L.i("CategoryAdapter" + childs.size() + "");
    }

    private void initChilds() {
        for (int i = 0; i < this.Grouplist.size(); i++) {
            childs.add(new ArrayList<CategoryChildBean>());
        }
    }

    ExpandableListView ELV;

    public ExpandableListView getELV() {
        return ELV;
    }

    public void setELV(ExpandableListView ELV) {
        this.ELV = ELV;
    }

    Context context;
    ArrayList<CategoryGroupBean> Grouplist;
    ArrayList<ArrayList<CategoryChildBean>> childs;
    boolean isInitChild = false;
    boolean isdownload = false;

    public ArrayList<ArrayList<CategoryChildBean>> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ArrayList<CategoryChildBean>> childs) {
        this.childs = childs;
    }

    @Override
    public int getGroupCount() {
        return Grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        L.i(childs.size() + "");
        return (childs.get(groupPosition).size() == 0) ?
                0 : childs.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Grouplist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, null);
        } else {
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_category_name);
        ImageView ivcategory = (ImageView) convertView.findViewById(R.id.iv_category);
        tvName.setText(Grouplist.get(groupPosition).getName());
        final ImageView img  = (ImageView) convertView.findViewById(R.id.iv_categoty_btn);
        ImageLoader.downloadImg(getContext(),ivcategory,Grouplist.get(groupPosition).getImageUrl());
        img.setImageResource(isExpanded?R.mipmap.expand_off:R.mipmap.expand_on);
   /*     ImageLoader.build(I.DOWNLOAD_IMG_URL + Grouplist.get(groupPosition).getImageUrl())
                .height(200)
                .width(200)
                .imageView(ivcategory)
                .listener(parent)
                .showImage(context);*/
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ELV.isGroupExpanded(groupPosition)) {
                    ELV.expandGroup(groupPosition);
                    NetDao.downloadCategoryChild(getContext(), Grouplist.get(groupPosition).getId(), new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                        @Override
                        public void onSuccess(CategoryChildBean[] result) {
                            ArrayList<CategoryChildBean> categorylist = ConvertUtils.array2List(result);
                            addchildlist(categorylist, groupPosition);
                        }
                        @Override
                        public void onError(String error) {

                        }
                    });
                } else {
                    ELV.collapseGroup(groupPosition);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_child, null);
        }
        ImageView ivchild = (ImageView) convertView.findViewById(R.id.iv_category_child_img);
        TextView chilname = (TextView) convertView.findViewById(R.id.tv_catagory_child_name);
        chilname.setText(childs.get(groupPosition).get(childPosition).getName());
        ImageLoader.downloadImg(getContext(),ivchild,childs.get(groupPosition).get(childPosition).getImageUrl());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CategoryDetails.class);
                intent.putExtra("cart_id",childs.get(groupPosition).get(childPosition).getId());
                intent.putExtra("title",childs.get(groupPosition).get(childPosition).getName());
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addlist(ArrayList<CategoryGroupBean> categorylist) {
        this.Grouplist.addAll(categorylist);
        if (!isInitChild) {
            initChilds();
        }
        notifyDataSetChanged();

    }


    public void addchildlist(ArrayList<CategoryChildBean> categorylist, int grouposition) {
        this.childs.get(grouposition).clear();
        this.childs.get(grouposition).addAll(categorylist);
        notifyDataSetChanged();
    }
}
