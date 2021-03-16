package com.example.talkpractice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnSend;
    EditText etText;
    FirebaseDatabase database;
    String stEmail;
    ArrayList<Chat> chatArrayList;
    private static final String TAG = "ChatActivity";
    Button btnWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        chatArrayList=new ArrayList<>();
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        stEmail=getIntent().getStringExtra("email");
        Button btnFinish = (Button)findViewById(R.id.btnFinish);
        btnSend=(Button)findViewById(R.id.btnSend);
        etText=(EditText)findViewById(R.id.etText);
        btnWeb = (Button)findViewById(R.id.btnWeb);
        Button btnplus = findViewById(R.id.btnPlus);
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleBottomSheetDialog bottomSheetDialog=new ExampleBottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(),"exampleBottomSheet");
            }
        });
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

       // String[] myDataset={"test1","test2","test3","test4"};
        mAdapter=new MyAdapter(chatArrayList,stEmail);
        recyclerView.setAdapter(mAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String stEmail = chat.getEmail();
                String stText=chat.getText();
                Log.d(TAG,"stEmail"+stEmail);
                Log.d(TAG,"stText"+stText);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatArrayList.size()-1);
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference ref = database.getReference("message");
        ref.addChildEventListener(childEventListener);

        btnSend.setOnClickListener((v)-> {
            String stText=etText.getText().toString();
            Toast.makeText(ChatActivity.this,"MSG : "+stText,Toast.LENGTH_LONG).show();

            Calendar c= Calendar.getInstance();
            SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateformat.format(c.getTime());
            DatabaseReference myRef = database.getReference("message").child(datetime);
            Hashtable<String,String> numbers
                    =new Hashtable<String,String>();
            numbers.put("email",stEmail);
            numbers.put("text",stText);
            myRef.setValue(numbers);
            etText.setText("");
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SearchName = etText.getText().toString();
                Intent BrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+SearchName));
                startActivity(BrowserIntent);
            }
        });
    }
}