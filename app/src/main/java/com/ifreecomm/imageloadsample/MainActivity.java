package com.ifreecomm.imageloadsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ifreecomm.imageload.imageload.ImageLoader;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    String url1 = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void down(View view) {
        ImageLoader.getInstance().displayImage(url1,imageView);
    }

    public void listLoad(View view) {
        startActivity(new Intent(this,ListActivity.class));
    }
}
