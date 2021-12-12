package com.blackorangejuice.songguojizhang.bean;

import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

public class SettingInfo {
    private Integer sid;
    // 对应的用户id
    private Integer uid;
    // 是否启用登陆密码检查,存true或false
    private String ifEnablePasswordCheck;
    // 默认启动页面
    private String defultLaunchPage;
    // 默认新增页面
    private String defultAddPage;
    // 默认当前使用账本bid
    private Integer currentAccountBookBid;

    public static SettingInfo getDefultSettingInfo(Integer uid) {
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setUid(uid);
        settingInfo.setIfEnablePasswordCheck("true");
        settingInfo.setDefultLaunchPage(GlobalConstant.LAUNCH_PAGE_OVERVIEW_PAGE);
        settingInfo.setDefultAddPage(GlobalConstant.ADD_PAGE_ACCOUNT);
        settingInfo.setCurrentAccountBookBid(0);
        return settingInfo;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getIfEnablePasswordCheck() {
        return ifEnablePasswordCheck;
    }

    public void setIfEnablePasswordCheck(String ifEnablePasswordCheck) {
        this.ifEnablePasswordCheck = ifEnablePasswordCheck;
    }

    public String getDefultLaunchPage() {
        return defultLaunchPage;
    }

    public void setDefultLaunchPage(String defultLaunchPage) {
        this.defultLaunchPage = defultLaunchPage;
    }

    public String getDefultAddPage() {
        return defultAddPage;
    }

    public void setDefultAddPage(String defultAddPage) {
        this.defultAddPage = defultAddPage;
    }

    public Integer getCurrentAccountBookBid() {
        return currentAccountBookBid;
    }

    public void setCurrentAccountBookBid(Integer currentAccountBookBid) {
        this.currentAccountBookBid = currentAccountBookBid;
    }
}
