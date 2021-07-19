package com.example.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginJava  extends  AppCompatActivity{
    EditText e1,e2;
    Button b1;
    DBhelper DB;
    public static String LOGINEMAIL="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        e1=findViewById(R.id.loginEmail);
        e2=findViewById(R.id.loginPwd);
        b1=findViewById(R.id.loginBtn);
        DB=new DBhelper(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=e1.getText().toString();
                LOGINEMAIL=email;
                String pwd=e2.getText().toString();
                if(email.equals("") || pwd.equals(""))
                {
                    Toast.makeText(loginJava.this,"Please enter all the fields",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Boolean checkemailpassword=DB.checkEmailPassword(email,pwd);
                    if(checkemailpassword==true)
                    {
                        Toast.makeText(loginJava.this,"Logged in successfully",Toast.LENGTH_LONG).show();
                        Intent i3=new Intent(loginJava.this,MainActivity2.class);
                        i3.putExtra("EMAIL",email);
                        startActivity(i3);

                    }
                    else
                    {
                        Toast.makeText(loginJava.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
