package cn.ucai.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PropertiesBean {
    private int id;
    private AlbumsBean albums;
    private boolean promote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AlbumsBean getAlbums() {
        return albums;
    }

    public void setAlbums(AlbumsBean albums) {
        this.albums = albums;
    }

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public PropertiesBean() {
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "id=" + id +
                ", albums=" + albums +
                ", promote=" + promote +
                '}';
    }
}
