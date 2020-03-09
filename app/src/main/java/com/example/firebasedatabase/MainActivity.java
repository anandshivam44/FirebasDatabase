package com.example.firebasedatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        this.mRef.addValueEventListener(this.mListner);
        this.Execute_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String obj = MainActivity.this.mName.getText().toString();
                Log.d(MainActivity.TAG, " Update ");
                MainActivity.this.mRef.child("user 1").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        Log.d(MainActivity.TAG, "onSuccess: TASK SUCCESSFUL");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        Log.d(MainActivity.TAG, "onFailure: DATA removed");
                    }
                });
            }
        });
    }

    private void initObjects() {
        this.mListner = new ValueEventListener() {
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
    }

    private void init() {
        this.Execute_Button = (Button) findViewById(R.id.execute_button);
        this.readData = (Button) findViewById(R.id.read_button);
        this.mName = (EditText) findViewById(R.id.name);
        this.mAge = (EditText) findViewById(R.id.age);
        this.mOutputText = (TextView) findViewById(R.id.text_view);
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        this.mDatabase = instance;
        this.mRef = instance.getReference("users");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mRef.child("user 1").removeEventListener(this.mListner);
    }
}