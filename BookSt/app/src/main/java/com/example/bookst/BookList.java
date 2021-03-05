package com.example.bookst;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookList  extends ArrayAdapter<BookData> {
    private Activity context;
    private List<BookData> UserDataList;
    public BookList(Activity context, List<BookData> UserDataList){
        super(context, R.layout.list_layout, UserDataList);
        this.context = context;
        this.UserDataList = UserDataList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View lisViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textName = (TextView) lisViewItem.findViewById(R.id.txName);
        TextView textLocation = (TextView) lisViewItem.findViewById(R.id.txLoc);
        BookData user = UserDataList.get(position);
        textName.setText(user.getBookName());
        textLocation.setText(user.getLocation());
        return lisViewItem;
    }
}
