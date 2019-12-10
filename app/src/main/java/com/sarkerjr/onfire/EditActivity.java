package com.sarkerjr.onfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    Button backButton;
    Button deleteButton;
    Button updateButton;

    EditText titleEdit;
    EditText textEdit;

    Intent intent;
    String keyUID;
    String title;
    String text;
    String key;

    FirebaseDatabase database;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        //Get the associated login user info
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Write a message to the database
        database = FirebaseDatabase.getInstance();

        //Retrieving the url of website send by the previous activity
        intent = getIntent();
        title = intent.getStringExtra("Title");
        text = intent.getStringExtra("Text");
        keyUID = intent.getStringExtra("KeyUID");

        titleEdit = findViewById(R.id.titleEdit);
        titleEdit.setText(title);

        textEdit = findViewById(R.id.textEdit);
        textEdit.setText(text);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference1 = database.getReference().child("notes").child(user.getUid()).child(keyUID).child("title");
                databaseReference1.setValue(titleEdit.getText().toString());

                databaseReference2 = database.getReference().child("notes").child(user.getUid()).child(keyUID).child("text");
                databaseReference2.setValue(textEdit.getText().toString());

                Toast.makeText(EditActivity.this, "Update Value", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference3 = database.getReference().child("notes").child(user.getUid()).child(keyUID);
                databaseReference3.removeValue();
                Toast.makeText(EditActivity.this, "Data Removed", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
