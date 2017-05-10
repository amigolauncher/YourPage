package com.gionee.amisystem.yourpage.statistics.bi;


/**
 * Created by lee on 11/17/15.
 */
public interface StatisticInterface {
    StatisticRunnable createValidStatisticRunnable(StatisticParams params);

    StatisticRunnable createDBStatisticRunnable();

    void destroy();
}
