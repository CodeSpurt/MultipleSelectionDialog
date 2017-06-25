package com.codespurt.multipleselectiondialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View openDialog;
    private StringBuilder selectedItems;
    private List<Integer> selectedItemIndexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDialog = (View) findViewById(R.id.openDialog);

        selectedItems = new StringBuilder();
        selectedItemIndexes = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openDialog.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        openDialog.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openDialog:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        // set dummy list
        List<CharSequence> list = new ArrayList<>();
        for (int countr = 0; countr < 20; countr++) {
            list.add(String.valueOf(countr + 1));
        }

        final CharSequence[] dialogList = list.toArray(new CharSequence[list.size()]);
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count];

        // add pre-selected items
        for (int index : selectedItemIndexes) {
            is_checked[index] = true;
        }

        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity.this);
        builderDialog.setTitle("Select Item");
        builderDialog.setMultiChoiceItems(dialogList, is_checked, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int whichButton, boolean isChecked) {

            }
        });
        builderDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListView list = ((AlertDialog) dialog).getListView();
                StringBuilder stringBuilder = new StringBuilder();
                for (int countr = 0; countr < list.getCount(); countr++) {
                    boolean checked = list.isItemChecked(countr);
                    if (checked) {
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(list.getItemAtPosition(countr));
                        selectedItemIndexes.add(countr);
                    }
                }
                if (stringBuilder.toString().trim().equals("")) {
                    ((TextView) findViewById(R.id.text)).setText("Open Dialog");
                    stringBuilder.setLength(0);
                } else {
                    ((TextView) findViewById(R.id.text)).setText(stringBuilder);
                    selectedItems = stringBuilder;
                }
            }
        });
        builderDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((TextView) findViewById(R.id.text)).setText("Open Dialog");
            }
        });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }
}