package com.blackorangejuice.songguojizhang.bean;

import java.util.Date;
import java.util.List;

public class EventItem {
    private Integer eid;
    // 事件标题
    private String eventTitle;
    // 事件内容
    private String eventContent;
    // 事件时间,存时间戳
    private Long eventTime;
    // 绑定的记账列表
    private List<AccountItem> accountItemList;
    // 对应账本id
    private Integer bid;
    // 对应账本
    private AccountBook accountBook;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public List<AccountItem> getAccountItemList() {
        return accountItemList;
    }

    public void setAccountItemList(List<AccountItem> accountItemList) {
        this.accountItemList = accountItemList;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }

}
