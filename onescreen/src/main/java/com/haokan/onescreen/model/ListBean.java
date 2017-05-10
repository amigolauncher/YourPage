package com.haokan.onescreen.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.haokan.onescreen.model.SmallsBean;

public class ListBean implements Parcelable {

	 /**
     * imageId : 245719
     * imgClickUrl : http://levect.com/zh/detail/tag/21244/img/245719/?eid=102002&appid=10047
     * imageUrl : http://res.levect.com/hkimages/25/26/148150919804897082625.jpg@!540x960?k=10047&pid=0&eid=102002
     * imageTpye : 2
     * imgClickTitle :
     * title : 微博：台灣音樂風雲榜
     * content : 田馥甄今晚在小巨蛋的第3场「如果PLUS」演唱会，依然全场爆满，她常自嘲演唱会是法会，没想到今晚真的在台上敲起木鱼，帮歌迷「收惊」，逗笑全场上万歌迷，她还说：「我找到事业的第二春！」演唱会上田馥甄说：「如果你们是来听田馥甄演唱会，很抱歉，要告诉你们，这是田馥甄法会。」她说，很多歌迷看（由好看MAGI自动编辑发布）
     * tags : [{"name":"田馥甄","url":"http://levect.com/zh/detail/tag/21244/img/245719/?eid=102002&appid=10047"},{"name":"+","url":"http://levect.com/zh/detail/tag/30154/img/245719/?eid=102002&appid=10047"}]
     * smalls : [{"imageId":243403,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243403/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/48/59/148140867916090095948.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243404,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243404/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/99/59/148140870365895745999.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243405,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243405/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/32/56/148140874290794795632.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243406,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243406/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/30/12/148140875858896181230.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243407,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243407/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/08/57/148140877421392735708.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243408,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243408/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/38/14/148140878984099371438.jpg@!480x854?k=10047&pid=0&eid=102002"},{"imageId":243409,"imgClickUrl":"http://levect.com/zh/detail/tag/26529/img/243402/simg/243409/?eid=102002&appid=10047","imageUrl":"http://res.levect.com/hkimages/18/20/148140881640894392018.jpg@!480x854?k=10047&pid=0&eid=102002"}]
     */

    private int imageId;
    private String imgClickUrl;
    private String imageUrl;
    private String imageTpye;
    private String imgClickTitle;
    private String title;
    private String content;
    private String editorRe;
    private String nativeUrl;
    private List<TagsBean> tags;
    private List<SmallsBean> smalls;
    

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImgClickUrl() {
        return imgClickUrl;
    }

    public void setImgClickUrl(String imgClickUrl) {
        this.imgClickUrl = imgClickUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTpye() {
        return imageTpye;
    }

    public void setImageTpye(String imageTpye) {
        this.imageTpye = imageTpye;
    }

    public String getImgClickTitle() {
        return imgClickTitle;
    }

    public void setImgClickTitle(String imgClickTitle) {
        this.imgClickTitle = imgClickTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditorRe() {
        return editorRe;
    }

    public void setEditorRe(String editorRe) {
        this.editorRe = editorRe;
    }
    
    
    public String getNativeUrl() {
        return nativeUrl;
    }

    public void setNativeUrl(String nativeUrl) {
        this.nativeUrl = nativeUrl;
    }
    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<SmallsBean> getSmalls() {
        return smalls;
    }

    public void setSmalls(List<SmallsBean> smalls) {
        this.smalls = smalls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageId);
        dest.writeString(this.imgClickUrl);
        dest.writeString(this.imageUrl);
        dest.writeString(this.imageTpye);
        dest.writeString(this.imgClickTitle);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.editorRe);
        dest.writeString(this.nativeUrl);
        dest.writeList(this.tags);
        dest.writeList(this.smalls);
    }

    public ListBean() {
    }

    protected ListBean(Parcel in) {
        this.imageId = in.readInt();
        this.imgClickUrl = in.readString();
        this.imageUrl = in.readString();
        this.imageTpye = in.readString();
        this.imgClickTitle = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.editorRe = in.readString();
        this.nativeUrl = in.readString();
        this.tags = new ArrayList<TagsBean>();
        in.readList(this.tags, List.class.getClassLoader());
        this.smalls = new ArrayList<SmallsBean>();
        in.readList(this.smalls, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
        public ListBean createFromParcel(Parcel source) {
            return new ListBean(source);
        }

        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };
}
