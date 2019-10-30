package com.unlogicon.bubbleface.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.unlogicon.bubbleface.BubbleFaceApp;
import com.unlogicon.bubbleface.R;
import com.unlogicon.bubbleface.interfaces.api.RestApi;
import com.unlogicon.bubbleface.interfaces.views.MainView;
import com.unlogicon.bubbleface.utils.FileUtils;

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
                getViewState().openSelect();
                break;
        }
    }

    public void onChoose(String photo) {

        File file = new File(photo);

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

    }

    private void onSuccess(ResponseBody responseBody) {
        Log.d("", "");
            if (responseBody != null) {
                // display the image data in a ImageView or save it
                Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                getViewState().setIamge(bmp);
            }
    }

    private void onError(Throwable throwable) {

    }
}
