package com.shopping.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.shopping.R;
import com.shopping.commons.CommonMethods;

import org.json.JSONObject;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG="SplashScreen";
    private CircularProgressBar progressBar;
    private TextView waitingText;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        try{
            initializeActivityControl();
            if(CommonMethods.isOnline(getBaseContext())){
                if(CommonMethods.getShoppingData(getBaseContext())==null){
                    fetchData();
                }else {
                    showProgressBar();

                }
            }else {
                Toast.makeText(this, getString(R.string.no_internet_window_message), Toast.LENGTH_SHORT).show();
            }

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
            new AsyncTask<Void, JSONObject, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showProgressBar();
                }

                @Override
                protected JSONObject doInBackground(Void... params) {
                    String requestUrl="https://stark-spire-93433.herokuapp.com/json";
                    String response;
                    try{
                        response=CommonMethods.performGet(requestUrl);
                        if(!response.isEmpty()){
                            CommonMethods.saveShoppingData(getBaseContext(),response);
                            return new JSONObject(response);
                        }


                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(JSONObject Result) {
                    super.onPostExecute(Result);
                    try{
                        hideProgressBar();
                        if(Result==null){
                            Toast.makeText(SplashScreen.this, getString(R.string.something_went_wrong_message), Toast.LENGTH_SHORT).show();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finishAffinity();
                                }
                            },100);
                        }else {
                            openShoppingListActivity();
                        }

                    }catch (Exception ex){
                        Log.e(TAG,ex.toString());
                    }
                }
            }.execute();



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
                progressBar.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }
    private void openShoppingListActivity(){
        Intent intent;
        try{
            intent = new Intent(getApplicationContext(),HomeScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.open_next, R.anim.close_main);
            finish();

        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
    }


}
