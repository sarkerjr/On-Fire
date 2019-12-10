package com.sarkerjr.onfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    private static final int DEFAULT_MSG_LENGTH_LIMIT = 100;
    public static final int RC_SIGN_IN = 100;
    Button saveButton;
    Button cancelButton;
    EditText mtitle;
    EditText mtext;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Get the associated login user info
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Write a message to the database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("notes").child(user.getUid());

        saveButton = findViewById(R.id.save);
        cancelButton = findViewById(R.id.cancel);
        mtitle = findViewById(R.id.saveTitle);
        mtext = findViewById(R.id.saveText);

        // Enable Send button when there's text to send
        mtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mtitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        //Save a note to the database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new Note(mtitle.getText().toString(), mtext.getText().toString()));
                Toast.makeText(AddActivity.this, "Note Saved", Toast.LENGTH_LONG).show();
            }
        });

        //Cancel the SAVE activity and returns to main
        cancelButton.setOnClickListener(                                                                                                                                                                              new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
