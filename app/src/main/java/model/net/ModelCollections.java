package model.net;

import android.content.Context;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ModelCollections implements IModelCollections {
    @Override
    public void downloadCollections(Context context, String username, int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, username)
                .addParam(I.PAGE_ID, pageId + "")
                .addParam(I.PAGE_SIZE, I.PAGE_SIZE_DEFAULT + "")
                .targetClass(CollectBean[].class)
                .execute(listener);

    }

    @Override
    public void collectGoods(Context context, int goodId, String muserName, OkHttpUtils.OnCompleteListener<MessageBean> listener) {

        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.GOODS_ID, goodId + "")
                .addParam(I.Collect.USER_NAME, muserName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void deleteCollect(Context context, int goodId, String name, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodId))
                .addParam(I.Collect.USER_NAME, name)
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    @Override
    public void isCollected(Context context, int goodId, String name, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodId))
                .addParam(I.Collect.USER_NAME, name)
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    @Override
    public void realse() {
        OkHttpUtils.release();
    }
}
