package com.example.assignment2;

//******************************************//
//      By: Hashmeet Singh Saini            //
//      August 1st 2024                     //
//******************************************//

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private AppDatabase db;
    private ActivityResultLauncher<Intent> addContactLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-a4").allowMainThreadQueries().build();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button addBtn = findViewById(R.id.addBtn);

        contacts = db.ContactDao().getAllContact();

        contactAdapter = new ContactAdapter(this, contacts);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("isEdit", false);
            addContactLauncher.launch(intent);
        });

        addContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            boolean isDeleted = data.getBooleanExtra("isDeleted", false);
                            int position = data.getIntExtra("position", -1);

                            if (isDeleted && position != -1) {
                                contacts.remove(position);
                                contactAdapter.notifyItemRemoved(position);
                            } else {
                                Contact updatedContact = (Contact) data.getSerializableExtra("contact");

                                if (position != -1 && updatedContact != null) {
                                    contacts.set(position, updatedContact);
                                    contactAdapter.notifyItemChanged(position);
                                } else if (updatedContact != null) {
                                    contacts.add(updatedContact);
                                    contactAdapter.notifyItemInserted(contacts.size() - 1);
                                }
                            }
                        }
                    }
                }
        );


    }

    void onContactClick(Contact contact, int position) {
        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("contact", contact);
        intent.putExtra("position", position);
        addContactLauncher.launch(intent);
    }
}
