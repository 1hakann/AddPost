package com.badlogic.androidgames.addpost;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sdssd extends AppCompatActivity {

    //Bu kısımda bileşenlerimizi tanımlıyoruz.
    TextView pass, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Bu metod uygulama açıldığında çalıştırılan metod.
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //Bu kısımda yukarıda tanımladığımız bileşenlerle xml olarak hazırladığımız bileşenleri birbirlerine bağlıyoruz.
        user = (TextView) findViewById(R.id.tvMail);
        pass = (TextView) findViewById(R.id.tvPass);

    }

    public void btnLogin(View view) {
        String kullanici = user.getText().toString();
        String parola = pass.getText().toString();

       // DBCon db = new DBCon();
       // db.execute();
    }
}