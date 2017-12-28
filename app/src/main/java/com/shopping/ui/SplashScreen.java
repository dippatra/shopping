package com.shopping.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shopping.R;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG="SplashScreen";
    private CircularProgressBar progressBar;
    private TextView waitingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        try{
            initializeActivityControl();

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void initializeActivityControl(){
        try{
            progressBar=(CircularProgressBar)findViewById(R.id.progressbar);
            waitingText=(TextView)findViewById(R.id.waiting_string);
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void fetchData(){
        try{
            showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="https://stark-spire-93433.herokuapp/json";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            hideProgressBar();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {


                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }

                }
            });
            queue.add(stringRequest);


        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void hideProgressBar(){
        try {
            if(progressBar!=null&&progressBar.getVisibility()==View.VISIBLE){
                progressBar.setVisibility(View.GONE);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void showProgressBar(){
        try {
            if(progressBar!=null&&progressBar.getVisibility()==View.INVISIBLE){
                progressBar.setVisibility(View.INVISIBLE);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }


}
