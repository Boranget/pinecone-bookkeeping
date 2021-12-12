package com.blackorangejuice.songguojizhang.bean;

public class Tag {
    private Integer tid;
    // 标签名
    private String tagName;
    // 标签对应的图片名
    private String tagImgName;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagImgName() {
        return tagImgName;
    }

    public void setTagImgName(String tagImgName) {
        this.tagImgName = tagImgName;
    }
}
