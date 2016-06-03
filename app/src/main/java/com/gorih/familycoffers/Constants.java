package com.gorih.familycoffers;

public class Constants {
    public static final int TAB_EXPANSES_LIST = 0;
    public static final int TAB_STATISTICS = 1;
    public static final int TAB_HISTORY = 2;

    public static final int DEFAULT_CATEGORY_ICON = R.mipmap.ic_plus_box;

    public static final String MEMBER_ID = "member_id";
    public static final String LAUNCHED_FIRST_TIME = "launched_at_first";

    public static final int OFFLINE_MODE = 0;
    public static final int ONLINE_MODE = 1;
    public static final int STATISTICS_FR_ID = 1;
    public static final int HISTORY_FR_ID = 0;
    public static final int ACTION_ERASE_DB = 1;
    public static final int ACTION_CLEAR_CATEGORIES = 0;

    public static class URL {
        public static final String HOST = "http://192.168.0.20:8080/";

        public static final String GET_EXPANSE_ITEM = HOST + "coffers";

        public static final String GET_FAMILY_ID = HOST + "new_family";

        public static final String POST_EXPANSE_ITEM = HOST + "coffers";
    }
}
