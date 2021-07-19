package com.example.myproject;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myproject.Adapters.CartAdapter;
import com.example.myproject.Models.CartModel;
import com.example.myproject.databinding.ActivityMain3Binding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.myproject.Adapters.MainAdapter.orderid;
import static com.example.myproject.MainActivity2.EMAIL;
import com.example.myproject.finalDBhelper;
import com.google.android.material.navigation.NavigationView;

public class cartJava extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    orderDBhelper OrderDB;

    ActivityMain3Binding binding;
    String emailId;
    Integer orderno;
    TextView t;
    finalDBhelper db;
    Button orderbutton;
    String currentDate,currentTime,cname,cprice,cquantity,email;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emailId=EMAIL;
        orderno=orderid;
        binding= ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<CartModel> list=new ArrayList<>();
        OrderDB=new orderDBhelper(this);
        orderbutton=findViewById(R.id.orderButton);

        t=findViewById(R.id.tatalBill);
        drawerLayout=findViewById(R.id.drawableLayout);
        navigationView=findViewById(R.id.nav_views);
        toolbar=findViewById(R.id.tools);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(cartJava.this);
        String ans=OrderDB.calculateTotal(EMAIL);
        t.setText(ans);
        Cursor result = OrderDB.getCartInfo(orderno,EMAIL);
        if (result.getCount() == 0) {
            Toast.makeText(cartJava.this, "No Item in the cart", Toast.LENGTH_LONG).show();
        } else {
            while (result.moveToNext()) {
                String cname = result.getString(2);
                String cprice = result.getString(3);
                String cquantity = result.getString(4);

                list.add(new CartModel(cname, cprice, cquantity));
            }


        }
        CartAdapter adapter = new CartAdapter(list,this);
        binding.cartRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.cartRecycler.setLayoutManager(layoutManager);
        db=new finalDBhelper(getApplicationContext().getApplicationContext());
        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=OrderDB.getCartInfo(orderno,EMAIL);
                while(cursor.moveToNext()) {
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        String email = EMAIL;
                        String cname = cursor.getString(2);
                        String cprice = cursor.getString(3);
                        String cquantity = cursor.getString(4);
                        Boolean insert = db.insertData(orderno, currentTime, currentDate, email, cname, cprice, cquantity);
                        if (insert == true) {
                            Boolean Res = OrderDB.deleteOrder(orderid, email, cname, cquantity);
                            for (int i = list.size()-1 ; i >= 0 ; i--){
                                adapter.removeItem(i);
                            }
                            Toast.makeText(cartJava.this, "Order placed successfully", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(cartJava.this, "Oops! couldn't place order", Toast.LENGTH_LONG).show();
                        }

                    }
            }
        });
    }




   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.id1:
                Intent i1=new Intent(cartJava.this,accountJava.class);
                startActivity(i1);
                return true;
            case R.id.id2:
                Intent i2=new Intent(cartJava.this,cartJava.class);
                startActivity(i2);
                return true;
            case R.id.id3:
                Intent i3=new Intent(cartJava.this,MainActivity.class);
                startActivity(i3);
                return true;
            case R.id.id4:
                Intent i4=new Intent(cartJava.this,MainActivity2.class);
                startActivity(i4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
   public void onBackPressed(){
       DrawerLayout drawer=findViewById(R.id.drawableLayout);
       if(drawer.isDrawerOpen(GravityCompat.START))
       {
           drawer.closeDrawer(GravityCompat.START);
       }
       else
       {
           super.onBackPressed();
       }
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent4=new Intent(cartJava.this,MainActivity2.class);
                startActivity(intent4);
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_profile:
                Intent intent2=new Intent(cartJava.this,accountJava.class);

                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent3=new Intent(cartJava.this,MainActivity.class);
                startActivity(intent3);

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


}
