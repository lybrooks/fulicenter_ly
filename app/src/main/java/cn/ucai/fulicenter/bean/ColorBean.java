package cn.ucai.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ColorBean {


    /**
     * colorId : 7
     * colorName : 白色
     * colorCode : #ffffff
     * colorImg : d
     * colorUrl : https://detail.tmall.com/item.htmspm=a1z10.5b.w40113609973698.66.6
     */

    private int colorId;
    private String colorName;
    private String colorCode;
    private String colorImg;
    private String colorUrl;

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getColorUrl() {
        return colorUrl;
    }

    public void setColorUrl(String colorUrl) {
        this.colorUrl = colorUrl;
    }

    public ColorBean() {
    }

    @Override
    public String toString() {
        return "ColorBean{" +
                "colorId=" + colorId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", colorImg='" + colorImg + '\'' +
                ", colorUrl='" + colorUrl + '\'' +
                '}';
    }
}
