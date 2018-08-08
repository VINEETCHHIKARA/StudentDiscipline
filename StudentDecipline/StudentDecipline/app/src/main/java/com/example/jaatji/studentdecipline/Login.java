package com.example.jaatji.studentdecipline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    int temp=0;
    EditText id,pass;
    Button btn;
    String u_id,u_pass;
    RequestQueue requestQueue;

    String url="http://192.168.0.110/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id=findViewById(R.id.input_email);
        pass=findViewById(R.id.input_password);
        btn=findViewById(R.id.btn_login);

        requestQueue= Volley.newRequestQueue(this);
    }

    public void login(View view) {
        if(validate()){
            StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getString("error")!=null){
                                    Toast.makeText(Login.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                temp=1;
                            }
                            if(temp==1){
                                temp=0;
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("id", u_id);

                                    editor.putString("name", jsonObject.getString("name"));
                                    editor.putString("email", jsonObject.getString("email"));
                                    editor.putString("userType", jsonObject.getString("userType"));
                                    editor.commit();
                                    Toast.makeText(Login.this, response+""+sp.getString("id",null), Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),Option.class);
                                    startActivity(i);
                                    finish();
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", u_id);
                    params.put("pass", u_pass);
                    return params;
                }
            };
            requestQueue.add(strRequest);
        }
    }

    private boolean validate() {
        u_id=id.getText().toString();
        u_pass=pass.getText().toString();
        if(TextUtils.isEmpty(id.getText())){
            id.setError("Enter Library ID...");
            id.requestFocus();
            return false;
        }else if(!u_id.matches("[a-zA-Z.0-9.? ]*")){
            id.setError("Enter valid Library ID...");
            id.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(pass.getText())){
            pass.setError("Enter Password...");
            pass.requestFocus();
            return false;
        }
        return true;
    }
}
