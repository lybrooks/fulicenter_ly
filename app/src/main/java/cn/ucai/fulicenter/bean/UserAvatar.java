package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class UserAvatar implements Serializable {

    /**
     * muserName : a952702
     * muserNick : 彭鹏
     * mavatarId : 74
     * mavatarPath : user_avatar
     * mavatarSuffix : .jpg
     * mavatarType : 0
     * mavatarLastUpdateTime : 1476284146171
     */

    private RetDataBean retData;

    public RetDataBean getRetData() {
        return retData;
    }

    public void setRetData(RetDataBean retData) {
        this.retData = retData;
    }

    public static class RetDataBean {
        private String muserName;
        private String muserNick;
        private int mavatarId;
        private String mavatarPath;
        private String mavatarSuffix;
        private int mavatarType;
        private String mavatarLastUpdateTime;

        public String getMuserName() {
            return muserName;
        }

        public void setMuserName(String muserName) {
            this.muserName = muserName;
        }

        public String getMuserNick() {
            return muserNick;
        }

        public void setMuserNick(String muserNick) {
            this.muserNick = muserNick;
        }

        public int getMavatarId() {
            return mavatarId;
        }

        public void setMavatarId(int mavatarId) {
            this.mavatarId = mavatarId;
        }

        public String getMavatarPath() {
            return mavatarPath;
        }

        public void setMavatarPath(String mavatarPath) {
            this.mavatarPath = mavatarPath;
        }

        public String getMavatarSuffix() {
            return mavatarSuffix;
        }

        public void setMavatarSuffix(String mavatarSuffix) {
            this.mavatarSuffix = mavatarSuffix;
        }

        public int getMavatarType() {
            return mavatarType;
        }

        public void setMavatarType(int mavatarType) {
            this.mavatarType = mavatarType;
        }

        public String getMavatarLastUpdateTime() {
            return mavatarLastUpdateTime;
        }

        public void setMavatarLastUpdateTime(String mavatarLastUpdateTime) {
            this.mavatarLastUpdateTime = mavatarLastUpdateTime;
        }

        public RetDataBean() {
        }

        @Override
        public String toString() {
            return "RetDataBean{" +
                    "muserName='" + muserName + '\'' +
                    ", muserNick='" + muserNick + '\'' +
                    ", mavatarId=" + mavatarId +
                    ", mavatarPath='" + mavatarPath + '\'' +
                    ", mavatarSuffix='" + mavatarSuffix + '\'' +
                    ", mavatarType=" + mavatarType +
                    ", mavatarLastUpdateTime='" + mavatarLastUpdateTime + '\'' +
                    '}';
        }
    }
}
