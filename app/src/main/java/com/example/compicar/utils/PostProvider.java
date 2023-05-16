package com.example.compicar.utils;

import com.example.compicar.models.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;



public class PostProvider {
    CollectionReference mCollection;
    public PostProvider(){
    mCollection= FirebaseFirestore.getInstance().collection("Posts");
    }
    public Task<Void> save(Post post){
        return mCollection.document().set(post);
    }
}
