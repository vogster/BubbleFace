package com.unlogicon.bubbleface.interfaces.views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

import java.io.File;

public interface MainView extends MvpView {
    void openSelect();

    void setImage(Bitmap bmp);

    void clearPhoto();

    void setAddButtonVisibility(int visibility);

    void setProgressBarVisibility(int visibility);

    void shareImage(File file);

    void setShareVisibility(int visibility);
}
