package com.example.firebasedatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasedatabase.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyTag";
    Button Execute_Button;
    EditText mAge;
    FirebaseDatabase mDatabase;
    ValueEventListener mListner;
    ChildEventListener childListner;
    EditText mName;
    TextView mOutputText;
    DatabaseReference mRef;
    Button readData;
    int value = 1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initObjects();
        //this.mRef.addValueEventListener(this.mListner);
        mRef.addChildEventListener(childListner);
        this.Execute_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = mName.getText().toString();
                String age=mAge.getText().toString();
                User user=new User(name,age);
//                Map<String,Object> updateValue=new HashMap<>();
//                updateValue.put("name",name);
//                updateValue.put("age",age);
//
//                Log.d(MainActivity.TAG, " Update ");
                String key=mRef.push().getKey();
                mRef.child(key).setValue(user);
//                mRef.child("user 1").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    public void onSuccess(Void aVoid) {
//                        Log.d(MainActivity.TAG, "onSuccess: TASK SUCCESSFUL");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    public void onFailure(Exception e) {
//                        Log.d(MainActivity.TAG, "onFailure: DATA removed");
//                    }
//                });
            }
        });
    }

    private void initObjects() {
        mListner = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (Object append : dataSnapshot.getChildren()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("--ITERATOR--");
                    sb.append(append);
                    Log.d(MainActivity.TAG, sb.toString());
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                MainActivity mainActivity = MainActivity.this;
                StringBuilder sb = new StringBuilder();
                sb.append("XXX  Failed  XXX\n");
                sb.append(databaseError.getMessage());
                Toast.makeText(mainActivity, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        childListner=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: CALLED");
                User person=dataSnapshot.getValue(User.class);
                Log.d(TAG, "Name = "+person.getName()+" Age = "+person.getAge());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: CALLED");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: CALLED");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved: CALLED");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: CALLED");
            }
        };
    }

    private void init() {
        Execute_Button = (Button) findViewById(R.id.execute_button);
        readData = (Button) findViewById(R.id.read_button);
        mName = (EditText) findViewById(R.id.name);
        mAge = (EditText) findViewById(R.id.age);
        mOutputText = (TextView) findViewById(R.id.text_view);
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        mDatabase = instance;
        mRef = instance.getReference("users");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mRef.child("user 1").removeEventListener(this.mListner);
        this.mRef.child("user 1").removeEventListener(childListner);
    }
}