package com.example.compicar.utils;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class ImageProvider {
    StorageReference mstorage;
    public ImageProvider(){
        mstorage= FirebaseStorage.getInstance().getReference();

    }
    public UploadTask save(Context context, File file){
        byte[] imageByte=CompressorBitmapImage.getImage(context, file.getPath(), 500,500);
        StorageReference storage=mstorage.child(new Date()+ ".jpg");
        mstorage=storage;
        UploadTask task = storage.putBytes(imageByte);
        return task;

    }
    public StorageReference getStorage(){
     return mstorage;
    }
}
