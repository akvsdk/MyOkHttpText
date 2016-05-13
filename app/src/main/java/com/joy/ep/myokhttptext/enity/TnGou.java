package com.joy.ep.myokhttptext.enity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author   Joy
 * Date:  2016/5/13.
 * version:  V1.0
 * Description:
 */
public class TnGou implements Parcelable {

    private int count;
    private int fcount;
    private int galleryclass;
    private int id;
    private String img;
    private int rcount;
    private int size;
    private long time;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeInt(this.fcount);
        dest.writeInt(this.galleryclass);
        dest.writeInt(this.id);
        dest.writeString(this.img);
        dest.writeInt(this.rcount);
        dest.writeInt(this.size);
        dest.writeLong(this.time);
        dest.writeString(this.title);
    }

    public TnGou() {
    }

    protected TnGou(Parcel in) {
        this.count = in.readInt();
        this.fcount = in.readInt();
        this.galleryclass = in.readInt();
        this.id = in.readInt();
        this.img = in.readString();
        this.rcount = in.readInt();
        this.size = in.readInt();
        this.time = in.readLong();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<TnGou> CREATOR = new Parcelable.Creator<TnGou>() {
        @Override
        public TnGou createFromParcel(Parcel source) {
            return new TnGou(source);
        }

        @Override
        public TnGou[] newArray(int size) {
            return new TnGou[size];
        }
    };
}

