package com.unlogicon.bubbleface.interfaces.views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {
    void openSelect();

    void setIamge(Bitmap bmp);
}
