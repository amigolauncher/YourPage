// IYourpageCallback.aidl
package com.gionee.amisystem.depencylib;

// Declare any non-default types here with import statements

interface IYourpageCallback {

    void startBinding();

    void bindCardViews(in String cardInfoList);

    void bindAddCardView(in String cardInfo);

    void bindRemoveCardView(in String removeCard);

    void bindMoveCardView(in String allCards);

    void finishBindCards();

    void setLoadedOnResume();

    void bindUpdatePromotionCard(in String allCards);
}
