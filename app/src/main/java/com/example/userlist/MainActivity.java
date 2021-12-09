package com.example.userlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    Button buttonGetData;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing variables
        buttonGetData=findViewById(R.id.getDataBtn);
        resultTextView=findViewById(R.id.ResultText);

        //Creating instance for request queue
        queue= Volley.newRequestQueue(this);

        buttonGetData.setOnClickListener(view -> {
            getDataFromJSON();
        });

    }

    private void getDataFromJSON() {
        resultTextView.setText("");
        String url="https://reqres.in/api/users?page=2";
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray=response.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject data=jsonArray.getJSONObject(i);
                    String firstName=data.getString("first_name");
                    String lastName=data.getString("last_name");
                    String email=data.getString("email");
                    resultTextView.append("Name: "+firstName+" "+lastName+"\n"+"Email: "+email+"\n\n");

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(this, "Try Again Later", Toast.LENGTH_SHORT).show();
        });
        queue.add(request);
    }
}