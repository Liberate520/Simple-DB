package ru.samsung.simpledb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editName, editPoints;
    Button save;
    ListView listView;
    MyDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.name);
        editPoints = findViewById(R.id.points);
        save = findViewById(R.id.save);
        listView = findViewById(R.id.listView);
        save.setOnClickListener(this);

        database = new MyDB(this);

        showAll();
    }

    public void showAll(){
        List<Person> list = database.selectAll();
        PersonAdapter adapter = new PersonAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (!editName.getText().equals("") && !editPoints.getText().equals("")){
            try {
                Person person = new Person();
                person.name = String.valueOf(editName.getText());
                person.points = Integer.parseInt(editPoints.getText().toString());
                database.insert(person);
                showAll();
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Неверный формат", Toast.LENGTH_SHORT).show();
            }
        }
    }
}