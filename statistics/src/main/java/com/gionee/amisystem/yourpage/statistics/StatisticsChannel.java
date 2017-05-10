package com.gionee.amisystem.yourpage.statistics;

public class StatisticsChannel {

    private static boolean sSupportStatistic = true;

    private static final String CHANNEL_SOURCE = "I01000";

    public String getStatisticsChannel() {
        return CHANNEL_SOURCE;
    }

    public static void setSupportStatisticState(boolean isSupport) {
        sSupportStatistic = isSupport;
    }

    public static boolean isSupportStatistic() {
        return sSupportStatistic;
    }
}
