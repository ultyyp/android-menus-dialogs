package com.example.menusdialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonDatePicker;
    private Button buttonTimePicker;
    private Button dialogButton;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonDatePicker = findViewById(R.id.buttonDatePicker);
        buttonTimePicker = findViewById(R.id.buttonTimePicker);
        dialogButton =  findViewById(R.id.dialogButton);

        // Initialize the list of items
        items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            items.add("Item " + i);
        }

        // Set up RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SimpleAdapter(items);
        recyclerView.setAdapter(adapter);

        // Set up Date Picker button
        buttonDatePicker.setOnClickListener(v -> showDatePickerDialog());

        // Set up Time Picker button
        buttonTimePicker.setOnClickListener(v -> showTimePickerDialog());

        // Dialog Button
        dialogButton.setOnClickListener(v -> showAlertDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    // Display selected date
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    showAlert("Date Selected", "Selected date is: " + date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    // Display selected time
                    String time = hourOfDay + ":" + String.format("%02d", minute1);
                    showAlert("Time Selected", "Selected time is: " + time);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");
        builder.setMessage("This is an AlertDialog.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

        private final ArrayList<String> localItems;

        public SimpleAdapter(ArrayList<String> items) {
            this.localItems = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String item = localItems.get(position);
            holder.textView.setText(item);
        }

        @Override
        public int getItemCount() {
            return localItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView textView;

            public ViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.itemTextView);
            }
        }
    }
}