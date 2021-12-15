package com.blackorangejuice.songguojizhang.utils.globle;

import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.GuideInfo;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;

import java.util.List;

/**
 * globle infos
 */
public class GlobalInfo {
    // 用户上一次选择的添加页面
    public static String currentAddPage = GlobalConstant.ADD_PAGE_ACCOUNT;

    public static GuideInfo guideInfo = null;
    // 设置信息
    public static SettingInfo settingInfo = null;
    // 当前账本
    public static AccountBook currentAccountBook = null;

    // 当前用户所拥有的账本
    public static List<AccountBook> accountBooks = null;
    //lastAddAccount		上一次添加的记账信息
    public static AccountItem lastAddAccount = null;
    //lastAddEvent		上一次添加的事件
    public static EventItem lastAddEvent = null;

}
