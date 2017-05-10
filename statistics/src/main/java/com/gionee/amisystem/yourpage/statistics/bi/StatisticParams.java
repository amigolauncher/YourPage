package com.gionee.amisystem.yourpage.statistics.bi;


/**
 * Created by lee on 11/23/15.
 */
public class StatisticParams {

    public static final String TAG = "StatisticParams";
    public static final int INVALID = -1;

    public int op = -1;

    public String eventName = null;

    public int cardId = -1;

    public int cardClass = -1;

    public String cardName;

    public StatisticParams() {

    }

    public String toString() {
        StringBuilder sb = new StringBuilder(TAG);
        sb.append(": cardId = ").append(cardId)
                .append(", cardClass = ").append(cardClass)
                .append(", cardName = ").append(cardName)
                .append(", eventName = ").append(eventName)
                .append(", op = ").append(op);
        return sb.toString();
    }

}
