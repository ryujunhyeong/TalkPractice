package com.example.talkpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button btnFinish = (Button)findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // btnFinish.setOnClickListener((v)->{finish();});  // 위와 같은 구문

        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true); // 높이 가변적이지 않도록
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset={"test1","test2","test3","test4"};
        mAdapter=new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
}