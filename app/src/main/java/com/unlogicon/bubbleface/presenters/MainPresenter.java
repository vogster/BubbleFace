package com.unlogicon.bubbleface.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.unlogicon.bubbleface.BubbleFaceApp;
import com.unlogicon.bubbleface.R;
import com.unlogicon.bubbleface.interfaces.api.RestApi;
import com.unlogicon.bubbleface.interfaces.views.MainView;
import com.unlogicon.bubbleface.utils.SaveToExternalStorage;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    Context context;

    @Inject
    RestApi restApi;

    private Bitmap bmp;

    public MainPresenter() {
        BubbleFaceApp.getInstance().getComponents().getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
            case R.id.photo:
                getViewState().openSelect();
                break;
            case R.id.share:
                File file = SaveToExternalStorage.saveTempBitmap(bmp);
                getViewState().shareImage(file);
                break;
        }
    }

    public void onChoose(String photo) {

        if (photo != null) {

            File file = new File(photo);

            getViewState().setAddButtonVisibility(View.GONE);
            getViewState().setProgressBarVisibility(View.VISIBLE);
            getViewState().setShareVisibility(View.GONE);

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            String descriptionString = "magic";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);

            restApi.magic(description, body)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onSuccess, this::onError);
        } else {
            getViewState().clearPhoto();
            getViewState().setAddButtonVisibility(View.VISIBLE);
            getViewState().setProgressBarVisibility(View.GONE);
            getViewState().setShareVisibility(View.GONE);
        }

    }

    private void onSuccess(ResponseBody responseBody) {
        Log.d("", "");
            if (responseBody != null) {
                // display the image data in a ImageView or save it
                bmp =  BitmapFactory.decodeStream(responseBody.byteStream());
                getViewState().setAddButtonVisibility(View.GONE);
                getViewState().setProgressBarVisibility(View.GONE);
                getViewState().setShareVisibility(View.VISIBLE);
                getViewState().setImage(bmp);
            }
    }

    private void onError(Throwable throwable) {
        getViewState().setAddButtonVisibility(View.VISIBLE);
        getViewState().setProgressBarVisibility(View.GONE);
    }
}
