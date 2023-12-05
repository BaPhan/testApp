package com.baphan.testcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvDeleteALl;
    EditText edtId;
    EditText edtName;
    Button btnSave;

    RecyclerView rcvUser;

    List<User> users;

    UserAdapter userAdapter;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDeleteALl = findViewById(R.id.tv_delete_all);
        edtId =findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);

        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(name);

                    boolean result = dbHelper.insertData(user);
                    if (result) {
                        Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                        edtName.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        rcvUser = findViewById(R.id.rcv_item);
        userAdapter = new UserAdapter();
        users = new ArrayList<>();
//        users.add(new User(1,"BaPhan"));
//        users.add(new User(2,"BaPhan 1"));
//        users.add(new User(3,"BaPhan 2"));
//        users.add(new User(4,"BaPhan 3"));

        userAdapter.setData(users);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);
        //loadData();


    }

    private void loadData(){
        Cursor cursor = dbHelper.getAllData();
        users = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()){
            do {
                Integer userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String userName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
                User user = new User(userId,userName);

                users.add(user);

            }while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}