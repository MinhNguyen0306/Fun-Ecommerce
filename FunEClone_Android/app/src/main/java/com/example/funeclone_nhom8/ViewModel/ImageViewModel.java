package com.example.funeclone_nhom8.ViewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funeclone_nhom8.R;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {
    private MutableLiveData<List<Bitmap>> imageLiveDataList;
    private List<Bitmap> imageList;

    public ImageViewModel() {
        imageLiveDataList = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        imageList = new ArrayList<>();
//        imageList.add(R.drawable.plus);
//        imageLiveDataList.setValue(imageList);
    }

    public MutableLiveData<List<Bitmap>> getImageLiveDataList() {
        return imageLiveDataList;
    }

    public void addImage(Bitmap image) {
        imageList.add(image);
        imageLiveDataList.setValue(imageList);
    }
}
