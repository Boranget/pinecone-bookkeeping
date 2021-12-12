package com.blackorangejuice.songguojizhang.bean;

public class AccountBook {
    private Integer bid;
    // 该账本对应的用户的id
    private Integer uid;
    // 账本名
    private String accountBookName;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAccountBookName() {
        return accountBookName;
    }

    public void setAccountBookName(String accountBookName) {
        this.accountBookName = accountBookName;
    }
}
