package com.example.compicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compicar.models.Post;
import com.example.compicar.utils.FileUtil;
import com.example.compicar.utils.ImageProvider;
import com.example.compicar.utils.PostProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.security.AuthProvider;
import java.util.Date;

public class Publicaciones extends AppCompatActivity {
    ImageView mivPost;
    FirebaseAuth mFAut;
    File mimageFile;
    Button mbtnPublicar;
    ImageProvider mImageProvider;
    PostProvider mPostProvider;
    TextView mtvfecha;
    TextView mtvdestino;
    TextView mtvdescription;
    private final int Gallery_Request_Code = 1;
    String mFecha="";
    String mDestino="";
    String mDescription="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        mtvfecha=findViewById(R.id.tifecha);
        mtvdestino=findViewById(R.id.tidestino);
        mtvdescription=findViewById(R.id.tidescription);
        mPostProvider=new PostProvider();
        mFAut=FirebaseAuth.getInstance();
       // mAuthProvider=new AuthProvider();

        mbtnPublicar=findViewById(R.id.btpublicar);
        mbtnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             postear();
            }
        });
        mImageProvider=new ImageProvider();
        mivPost=findViewById(R.id.ivPost);
        mivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
    }
    private void postear(){
         mFecha=mtvfecha.getText().toString();
         mDestino= mtvdestino.getText().toString();
         mDescription=mtvdescription.getText().toString();
        if(!mFecha.isEmpty()&&!mDestino.isEmpty() ){
            if(mimageFile!=null){
                saveimagen();
            }else{
                Toast.makeText(Publicaciones.this,"Debe seleccionar una imagen",Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(Publicaciones.this,"Debe indicar los campos fecha y destino ",Toast.LENGTH_SHORT).show();
        }
    }
   private void saveimagen(){
        mImageProvider.save(Publicaciones.this,mimageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
              if (task.isSuccessful()){
                  mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {
                       String url =uri.toString();
                       String id =mFAut.getCurrentUser().getUid();
                       Post post = new Post();
                       post.setImagen(url);
                       post.setFecha(mFecha);
                       post.setDestino(mDestino);
                       post.setDescription(mDescription);

                       post.setIdUser(id);
                       mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> taskSave) {
                            if (taskSave.isSuccessful()){
                                Toast.makeText(Publicaciones.this,"la infromacion de almacen'o correctamente ",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Publicaciones.this,"NO se pudo almacenar la informacion",Toast.LENGTH_SHORT).show();
                            }
                           }
                       });

                      }
                  });
              }else{
                  Toast.makeText(Publicaciones.this,"Ha ocurrido un error al almacenar",Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
    private void opengallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_Request_Code && resultCode==RESULT_OK){
            try {
                mimageFile= FileUtil.from(this,data.getData());
                mivPost.setImageBitmap(BitmapFactory.decodeFile(mimageFile.getAbsolutePath()));

            }catch (Exception e){
                Log.d("MENSAJE","Se produjo un error"+e.getMessage());
                Toast.makeText(this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}