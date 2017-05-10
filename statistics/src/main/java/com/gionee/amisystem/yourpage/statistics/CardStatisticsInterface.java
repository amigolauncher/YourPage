package com.gionee.amisystem.yourpage.statistics;

import com.gionee.amisystem.depencylib.IYourpageService;

/**
 * Created by ke on 16-9-7.
 */
public interface CardStatisticsInterface {

    /**
     * set current card type, the type will provider by Yourpage
     * @param cardType
     */
    public void setCardType(int cardType);


    /**
     * @param service is yourpage service, use for statistics
     */
    public void setYourpageServie(IYourpageService service);

}
