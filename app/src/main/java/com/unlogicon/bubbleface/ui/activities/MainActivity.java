package com.unlogicon.bubbleface.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.aminography.choosephotohelper.ChoosePhotoHelper;
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unlogicon.bubbleface.R;
import com.unlogicon.bubbleface.interfaces.views.MainView;
import com.unlogicon.bubbleface.presenters.MainPresenter;

import java.io.File;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    private AppCompatButton add, share;

    private ChoosePhotoHelper choosePhotoHelper;

    private AppCompatImageView photo;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        add.setOnClickListener(presenter::onClick);

        photo = findViewById(R.id.photo);
        photo.setOnClickListener(presenter::onClick);

        share = findViewById(R.id.share);
        share.setOnClickListener(presenter::onClick);

        progressBar = findViewById(R.id.progressBar);

        choosePhotoHelper = ChoosePhotoHelper.with(this)
                .asFilePath()
                .build(new ChoosePhotoCallback<String>() {
                    @Override
                    public void onChoose(String photo) {
                        presenter.onChoose(photo);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void openSelect() {
        choosePhotoHelper.showChooser();
    }

    @Override
    public void setImage(Bitmap bmp) {
        photo.setImageBitmap(bmp);
    }

    @Override
    public void clearPhoto() {
        photo.setImageResource(0);
    }

    @Override
    public void setAddButtonVisibility(int visibility) {
        add.setVisibility(visibility);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void shareImage(File file) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
        shareIntent.setType("image/jpeg");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share)));
    }

    @Override
    public void setShareVisibility(int visibility) {
        share.setVisibility(visibility);
    }

}
