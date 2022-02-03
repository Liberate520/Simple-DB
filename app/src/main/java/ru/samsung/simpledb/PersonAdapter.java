package ru.samsung.simpledb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {

    ArrayList<Person> list;

    public PersonAdapter(@NonNull Context context, @NonNull List<Person> objects) {
        super(context, R.layout.list_item, objects);
        list = (ArrayList) objects;
    }

    @Nullable
    @Override
    public Person getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }
        Person person = list.get(position);

        TextView name = convertView.findViewById(R.id.name);
        name.setText(person.name);

        TextView points = convertView.findViewById(R.id.points);
        points.setText(person.points);

        return convertView;
    }
}
