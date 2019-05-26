package com.example.absensi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login<TAG> extends AppCompatActivity {
    ProgressDialog pDialog;
    Button btn_login;
    EditText txt_username, txt_password;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "login.php";

    private static final String TAG = login.class.getSimpleName();

    private static final String TAG_SUCCESS ="success";
    private static final String TAG_MESSAGE ="message";

    public final static String TAG_USERNAME ="username";
    public final static String TAG_ID ="id";

    String tag_json_obj "json_obj_req";
    SharedPreferences sharedPreferences;
    Boolean session = false;
    String id, username;
    public static final String my_shared_preferences ="my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected voidon Create(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVISE)
        {
            if (conMgr.getActiveNetworkInfo() !=null
                 && conMgr.getActiveNetworkInfo().isAvailable()
                 && conMgr.getActiveNetworkInfo().isConnected()){
            }else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();

            }
        }

        btn_login =(Button) findViewById(R.id.btn_login);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        //Cek session login jika TRUE maka langsung MainActivity
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status,false);
        id = sharedPreferences.getString(TAG_ID,null);
        username = sharedPreferences.getString(TAG_USERNAME,null);

        if (session){
            Intent intent = new Intent(login.this,MainAActivity.class);
            Intent.putExtra(TAG_ID,id);
            Intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (username.trim().length() > 0 && password.trim().length() > 0){
                    if (conMgr.getActiveNetworkInfo() !=null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isAvailable()){
                        checklogin(username, password);
                    }else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                    }else{
                        //prompt user to enter credentials
                        Toast.makeText(getApplicationContext(),"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
