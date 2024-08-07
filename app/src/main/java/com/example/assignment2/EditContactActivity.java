package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

//******************************************//
//      By: Hashmeet Singh Saini            //
//      August 1st 2024                     //
//******************************************//

public class EditContactActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private Button btnSave, btnDelete;
    private Contact contact;
    private TextView tvPageHead;
    private AppDatabase db;
    private Boolean isEdit;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_contact);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-a4").allowMainThreadQueries().build();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        tvPageHead = findViewById(R.id.tvPageHead);
        btnDelete=findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);

        if (isEdit) {
            btnSave.setText("Save");
            tvPageHead.setText("Edit Contact");
            contact = (Contact) intent.getSerializableExtra("contact");
            if (contact != null) {
                etName.setText(contact.getName());
                etEmail.setText(contact.getEmail());
                etPhone.setText(contact.getPhone());
            }
        } else {
            btnSave.setText("Add");
            tvPageHead.setText("Add Contact");
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> {
            if (contact == null) {
                contact = new Contact();
            }
            contact.setName(etName.getText().toString());
            contact.setEmail(etEmail.getText().toString());
            contact.setPhone(etPhone.getText().toString());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("contact", contact);
            resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
            setResult(RESULT_OK, resultIntent);

            if (isEdit) {
                db.ContactDao().update(contact);
            } else {
                db.ContactDao().insert(contact);
                // Query the database to get the inserted contact's ID
                Contact insertedContact = db.ContactDao().getContactByNameEmailPhone(
                        contact.getName(), contact.getEmail(), contact.getPhone());
                if (insertedContact != null) {
                    contact.setUid(insertedContact.getUid());
                }
            }


            finish();
        });

        btnDelete.setOnClickListener(v -> {
            if (isEdit) {
                db.ContactDao().delete(contact);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
                resultIntent.putExtra("isDeleted", true);
                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
