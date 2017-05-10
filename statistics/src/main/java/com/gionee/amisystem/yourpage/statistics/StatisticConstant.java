package com.gionee.amisystem.yourpage.statistics;

import android.provider.BaseColumns;

public class StatisticConstant {

    public static final String EVENT_YOURPAGE_CARD_CLICK_TIMES = "Yourspage_Card_Click_Times";
    public static final String EVENT_YOURPAGE_CARD_CLICK_REFRESH_BUTTON_TIMES = "Yourspage_Card_Click_Refresh_Button_Times";
    public static final String EVENT_YOURPAGE_CARD_CLICK_MORE_BUTTON_TIMES = "Yourspage_Card_Click_More_Button_Times";
    public static final String EVENT_YOURPAGE_CARD_CLICK_DOWNLOAD_APK_TIMES = "Yourspage_Card_Click_Download_Apk_Times";


    public static final String EVENT_YOURSPAGE_PULL_DOWN_TIMES = "Yourspage_Pull_Down_Times";
    public static final String EVENT_YOURSPAGE_PULL_UP_TIMES = "Yourspage_Pull_Up_Times";
    public static final String EVENT_YOURSPAGE_ACTIVE_TIMES = "Yourspage_Active_Times";
    public static final String EVENT_YOURSPAGE_ACTIVE_USERS = "Yourspage_Active_Users";
    public static final String EVENT_YOURSPAGE_CLICK_FUNCTION_BUTTON_TIMES = "Yourspage_Click_Function_Button_Times";
    public static final String EVENT_YOURSPAGE_CLICK_EMPTYPAGE_FUNCTION_BUTTON_TIMES = "Yourspage_Click_EmptyPage_Function_Button_Times";//NOSONAR
    public static final String EVENT_YOURSPAGE_CARD_SEARCH_CLICK_TIMES = "Yourspage_Card_Search_Click_Times";//NOSONAR
    public static final String EVENT_YOURSPAGE_CARD_SEARCH_CLICK_SCANNER_TIMES = "Yourspage_Card_Search_Click_Scanner_Times";//NOSONAR
    public static final String EVENT_YOURSPAGE_CARDS_AND_ORDER = "Yourspage_Cards_And_Order";
    public static final String EVENT_YOURSPAGE_CARD_MANAGER_CLICK_DELETE_TIMES = "Yourspage_Cards_Manager_Click_Delete_Times";
    public static final String EVENT_YOURSPAGE_CARD_MANAGER_CLICK_ADD_TIMES = "Yourspage_Cards_Manager_Click_Add_Times";
    public static final String EVENT_YOURSPAGE_CARD_MANAGER_DRAG_AND_DROP_TIMES = "Yourspage_Cards_Manager_Drag_Add_Drop_Times";

    public static final String EVENT_YOURSPAGE_CARDS_SHOW_TIMES = "Yourspage_Cards_Show_Times";
    public static final String EVENT_PULL_TO_REFRESH_TIMES = "Yourspage_Pull_To_Refresh_Times";
    public static final String EVENT_SEARCH_VIEW_CLICK_SEARCH_TIMES = "Yourspage_Search_View_Click_Search_Times";
    public static final String EVENT_SEARCH_VIEW_CLICK_SCANNER_TIMES = "Yourspage_Search_View_Click_Scanner_Times";
    public static final String EVENT_SEARCH_VIEW_CLICK_DELETE_ICON_TIMES = "Yourspage_Search_View_Click_Delete_Icon_Times";

    public static final String EVENT_SEARCH_VIEW_ENTER_CONCTACTS_TIMES = "Yourspage_Search_View_Enter_Contacts_Times";
    public static final String EVENT_SEARCH_VIEW_ENTER_MMS_TIMES = "Yourspage_Search_View_Enter_Mms_Times";
    public static final String EVENT_SEARCH_VIEW_ENTER_LOCAL_APP_TIMES = "Yourspage_Search_View_Enter_Local_App_Times";
    public static final String EVENT_SEARCH_VIEW_ENTER_BROWSER_TIMES = "Yourspage_Search_View_Enter_Browser_Times";
    public static final String EVENT_SEARCH_VIEW_SCANNER_CODE_TYPE = "Yourspage_Search_View_Scanner_Code_Type";

    public static final String TAG = "StatisticConstant";
    public static final String TABLE_NAME = "statistic";

    public static final class Column implements BaseColumns {
        public static final String SOURCE_FROM = "source_from";
        public static final String STATISTIC_INFO = "statistic_info";
        public static final String URL = "url";
    }

    public static class Param {
        public static final String IMEI = "imei";
        public static final String ANDROID_VERSION = "av";
        public static final String ROM_VERSION = "rv";
        public static final String OP_TYPE = "op";
        public static final String NET_TYPE = "nt";
        public static final String MODEL = "m";
        public static final String YOURPAGE_VERSION = "yv";
        public static final String CARD_ID = "cid";
        public static final String CARD_ORDER = "cord";
        public static final String NAME = "n";
    }

    public static class Op {
        public static final int OP_INIT = 10;
        public static final int OP_CLICK_REFRESH_BUTTON = 20;
        public static final int OP_CLICK_CARD_CONTENT = 30;
        public static final int OP_CLICK_MORE_BUTTON = 40;
        public static final int OP_CLICK_DOWNLOAD_BUTTON = 50;
        public static final int OP_CARD_SHOW = 60;
    }

    public static final int SOURCE_FORM_BI = 0;

    public static final String TIME = "TIME";

    public static final String ENCODE = "utf-8";
    public static final String TAG_YU = "&";
    public static final String TAG_EQUALS = "=";
    public static final String TAG_QUESTION = "?";

    public static final String NULL = "null";
    public static final String PROP_MODEL = "ro.product.model";
    public static final String PROP_GNROMVER = "ro.gn.gnromvernumber";
    public static final String PROP_ANDROIDVER = "ro.build.version.release";


}
