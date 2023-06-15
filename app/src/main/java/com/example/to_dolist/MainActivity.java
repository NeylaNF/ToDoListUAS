package com.example.to_dolist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ToDo> ToDoList = new ArrayList<>();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = findViewById(R.id.rvToDo);
        ArrayList<ToDo> ToDos = new ArrayList<>();
        RecyclerView rvToDo = this.findViewById(R.id.rvToDo);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://mgm.ub.ac.id/todo.php";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject responseObj = response.getJSONObject(i);

                                String id = responseObj.getString("id");
                                String activity = responseObj.getString("what");
                                String time = responseObj.getString("time");
                                ToDos.add(new ToDo(id, activity, time));
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToDoAdapter tAdapter = new ToDoAdapter(MainActivity.this, ToDos);
                                        rvToDo.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        rvToDo.setAdapter(tAdapter);
                                    }
                                });

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Gagal mengambil data. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonArrayRequest);
            }

        });
        t.start();
    }
}