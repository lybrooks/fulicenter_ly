package cn.ucai.fulicenter.myAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import day.myfulishe.R;


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

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> grouplist, ArrayList<ArrayList<CategoryChildBean>> childs) {

        this.context = context;
        Grouplist = grouplist;
        this.childs = childs;
    }

    Context context;
    ArrayList<CategoryGroupBean> Grouplist;
    ArrayList<ArrayList<CategoryChildBean>> childs;

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
        return childs.get(groupPosition).size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, null);
        } else {
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_category_name);
        ImageView ivcategory = (ImageView) convertView.findViewById(R.id.iv_category);
        tvName.setText(Grouplist.get(groupPosition).getName());
        ImageLoader.build(I.DOWNLOAD_IMG_URL + Grouplist.get(groupPosition).getImageUrl())
                .height(200)
                .width(200)
                .imageView(ivcategory)
                .listener(parent)
                .showImage(context);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_child, null);
        }
        ImageView ivchild = (ImageView) convertView.findViewById(R.id.iv_category_child_img);
        TextView chilname = (TextView) convertView.findViewById(R.id.tv_catagory_child_name);
        chilname.setText(childs.get(groupPosition).get(childPosition).getName());
        ImageLoader.build(I.DOWNLOAD_IMG_URL + childs.get(groupPosition).get(childPosition).getImageUrl())
                .height(200)
                .width(200)
                .imageView(ivchild)
                .listener(parent)
                .showImage(context);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addlist(ArrayList<CategoryGroupBean> categorylist) {
        this.Grouplist.addAll(categorylist);
        notifyDataSetChanged();
    }


}
