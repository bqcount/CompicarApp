package com.example.compicar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText etname;
    EditText etapellidos;
    EditText etemail;
    EditText etpassword;
    EditText etconfirmpass;
    Button btcrearcuenta;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    // DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//validando todos los elementos
        etname = findViewById(R.id.crearnombre);
        etapellidos = findViewById(R.id.crearapellidos);
        etemail = findViewById(R.id.crearemail);
        etpassword = findViewById(R.id.crearpassword);
        etconfirmpass = findViewById(R.id.confirmarpassword);
        btcrearcuenta = findViewById(R.id.btregistrar);
        mAuth = FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        //databaseReference= FirebaseDatabase.getInstance().getReference();


        btcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        }
    private void register() {
        String username = etname.getText().toString();
        String apellidos = etapellidos.getText().toString();
        String email = etemail.getText().toString();
        String cpassword = etpassword.getText().toString();
        String confirmarp = etconfirmpass.getText().toString();

        if (!username.isEmpty() && !apellidos.isEmpty() && !email.isEmpty() && !cpassword.isEmpty() && !confirmarp.isEmpty()){
           if(isEmailValid(email)){
            //if(!cpassword.equals(confirmarp)){
                //Toast.makeText(Login.this,"Las contrase;as son correctas",Toast.LENGTH_SHORT).show();
               if(cpassword.length()>=6&& cpassword.equals(confirmarp)){
                   createUser(username,apellidos,email,cpassword);
               }else{
                   Toast.makeText(Login.this,"La contrasena debe tener mas de 6 caracteres",Toast.LENGTH_SHORT).show();
                   Toast.makeText(Login.this,"Las contrasenas no coinciden",Toast.LENGTH_SHORT).show();
               }
            // }else{
                // Toast.makeText(Login.this,"Las contrasenas no coinciden",Toast.LENGTH_SHORT).show();
            //}
           }else{
               Toast.makeText(Login.this,"El formato del email no es correcto",Toast.LENGTH_SHORT).show();
           }

        }else{
            Toast.makeText(Login.this,"Todos los campos deben estar completados",Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(final String username,final String apellidos,final String email,final String cpassword){

        mAuth.createUserWithEmailAndPassword(email,cpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 String id =mAuth.getCurrentUser().getUid();
                 Map<String,Object>map=new HashMap<>();
                 map.put("Nombre",username);
                 map.put("Apellidos",apellidos);
                 map.put("Email",email);

                 mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(Login.this,"Usuario creado y almacenado correctamente",Toast.LENGTH_SHORT).show();
                      }else{
                          Toast.makeText(Login.this,"Usuario no se  creo correctamente",Toast.LENGTH_SHORT).show();
                      }
                     }
                 });


             }
            }
        });
    }
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    }





