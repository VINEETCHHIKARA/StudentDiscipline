package com.example.jaatji.studentdecipline;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Detailes extends AppCompatActivity {

    RequestQueue requestQueue;
    Bundle b;
    TextView id,tname,tcourse,tbranch,thod,error,s_id,s_name,s_course,s_branch,s_hod;
    EditText message;
    Button btn;
    String stu_id,name,hod,course,branch,url="http://192.168.0.110/detailes.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        b=getIntent().getExtras();

        s_id=findViewById(R.id.s_id);
        s_name=findViewById(R.id.s_name);
        s_course=findViewById(R.id.s_coures);
        s_branch=findViewById(R.id.s_branch);
        s_hod=findViewById(R.id.s_hod);
        id=findViewById(R.id.id);
        tname=findViewById(R.id.name);
        tcourse=findViewById(R.id.course);
        tbranch=findViewById(R.id.branch);
        thod=findViewById(R.id.hod);
        error=findViewById(R.id.err);
        message=findViewById(R.id.et);
        btn=findViewById(R.id.btn);
        message.setText(null);
        requestQueue= Volley.newRequestQueue(this);
        sendJsonRequest();
    }

    public void sendJsonRequest(){
        stu_id=b.getString("id");
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        id.setText(stu_id);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getString("error").toString()!=null){
                                    s_branch.setText("");
                                    s_course.setText("");
                                    s_hod.setText("");
                                    s_name.setText("");
                                    btn.setEnabled(false);
                                    error.setText(jsonObject.getString("error").toString());
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            name = jsonObject.getString("name").toString();
                            tname.setText(name);
                            course = jsonObject.getString("course").toString();
                            tcourse.setText(course);
                            branch = jsonObject.getString("branch").toString();
                            tbranch.setText(branch);
                            hod = jsonObject.getString("hod_id").toString();
                            thod.setText(hod);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("tag", stu_id);
                return params;
            }
        };
        requestQueue.add(strRequest);
    }

    public void mail(View view) {
        if(TextUtils.isEmpty(message.getText())){
            message.setError("Write some message...");
        }
        else{
            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Decipline");
            intent.putExtra(Intent.EXTRA_TEXT, "Student ID : "+stu_id+"\nStudent Name : "+name+"\nCourse : "+course+"\nBranch : "+branch+"\nComplent : "+message.getText().toString());
            intent.setData(Uri.parse("mailto:"+hod)); // or just "mailto:" for blank
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);
        }
    }
}
