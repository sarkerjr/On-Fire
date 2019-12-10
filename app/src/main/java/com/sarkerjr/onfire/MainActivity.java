package com.sarkerjr.onfire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 100;
    ListView listView;
    ArrayList<NotenNodes> titlesArray;
    NoteAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        titlesArray = new ArrayList();

        //Get the associated login user info
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Write a message to the database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("notes").child(user.getUid());

        //Adding adapter to the list of titles
        titlesArray = new ArrayList<NotenNodes>();
        adapter = new NoteAdapter(this, titlesArray);
        listView.setAdapter(adapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note notes = dataSnapshot.getValue(Note.class);
                NotenNodes notenNodes = new NotenNodes(notes.getTitle(), notes.getText(),dataSnapshot.getKey());
                titlesArray.add(notenNodes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Loading failed!", Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference.addChildEventListener(childEventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NotenNodes notenNodes = titlesArray.get(i);

                String title = notenNodes.getTitle();
                String text = notenNodes.getText();
                String key = notenNodes.getKey();

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Text", text);
                intent.putExtra("KeyUID", key);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance()
                        .signOut(this);
                return true;
            case R.id.add_new:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
