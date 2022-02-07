package ru.samsung.simpledb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        registerForContextMenu(listView);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Person person = (Person) listView.getAdapter().getItem(info.position);
        switch (item.getItemId()){
            case R.id.delete:
                long count = database.delete(person.id);
                Toast.makeText(this, "Было удалено "+ count + " записей", Toast.LENGTH_SHORT).show();
                if (count != 0){
                    showAll();
                }
                break;
            case R.id.edit:
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.edit_dialog);

                EditText edName = dialog.findViewById(R.id.ed_name);
                EditText edPoints = dialog.findViewById(R.id.ed_points);
                edName.setText(person.name);
                edPoints.setText(String.valueOf(person.points));
                Button btEdit = dialog.findViewById(R.id.btEdit);
                btEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!edName.getText().equals("") && !edPoints.getText().equals("")){
                            try {
                                Person curPerson = new Person();
                                curPerson.id = person.id;
                                curPerson.name = String.valueOf(edName.getText());
                                curPerson.points = Integer.parseInt(edPoints.getText().toString());
                                long count = database.update(curPerson);
                                Toast.makeText(getApplicationContext(), "Было изменено "+ count + " записей", Toast.LENGTH_SHORT).show();
                                if (count != 0){
                                    showAll();
                                }
                                dialog.cancel();
                            } catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Неверный формат", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                Button btCancel = dialog.findViewById(R.id.btCancel);
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}