package com.example.angelostockapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<String> cFavorites;
    static FirebaseUser mUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        cFavorites = new ArrayList<String>();

    }

    public static FirebaseUser getUser(){
        return mUser;
    }

    public static List<String> getFavs(){
        return cFavorites;
    }

    public static void setFavs(List<String> s){
        cFavorites = s;
    }

    public static void addFav(String s){
        cFavorites.add(s);
    }

    public static boolean isFav(String s){
        for(String k : cFavorites){
            if(k.equals(s)){
                return true;
            }

        }
        return false;
    }

    public static void remFav(String s){
        if (isFav(s)){
            cFavorites.remove(s);
        }
        return;
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}