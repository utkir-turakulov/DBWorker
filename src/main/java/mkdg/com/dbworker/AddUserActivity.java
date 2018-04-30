package mkdg.com.dbworker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddUserActivity extends Activity implements View.OnClickListener {

    private Button save_button;
    private User user;
    private DatabaseHandler handler;
    private EditText fio;
    private EditText birthDate;
    private EditText birthPlace;
    private EditText sex;
    private Intent intent;
    private String selectedDate;
    /*Edit data */
    private User oldUser;
    int year;
    int month;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        save_button = findViewById(R.id.save_user_button);
        fio = findViewById(R.id.FIO);
        birthDate = findViewById(R.id.date_of_birth_editor);
        birthPlace = findViewById(R.id.birth_place_editor);
        sex = findViewById(R.id.sex_field);

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);


        intent = getIntent();
        handler = new DatabaseHandler(getApplicationContext());
        oldUser = new User();
        if (intent != null && intent.getExtras() != null) {
            oldUser.setFIO(intent.getStringExtra("fio"));

            oldUser.setDateOfBirth(intent.getStringExtra("birthDate"));
            oldUser.setBirthPlace(intent.getStringExtra("birthPlace"));
            oldUser.setSex(intent.getStringExtra("sex"));
                    /*Заполняем форму*/
            fio.setText(intent.getStringExtra("fio"));
            birthDate.setText(intent.getStringExtra("birthDate"));
            birthPlace.setText(intent.getStringExtra("birthPlace"));
            sex.setText(intent.getStringExtra("sex"));
        }
        save_button.setOnClickListener(this);
        birthDate.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        handler = new DatabaseHandler(getApplicationContext());
        user = new User();
        switch (view.getId()) {
            case R.id.save_user_button: {
                if (intent != null && intent.getExtras() != null) {
                    if (intent.getStringExtra("task").equals("edit")) {
                        editUserData();
                    }
                } else {
                    addUser();
                }
                break;
            }
            case R.id.date_of_birth_editor: {
                showDatePicker();
                break;
            }
        }
    }


    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, onDataSet(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener onDataSet() {
        return new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = calendar.getTime();
                selectedDate = format.format(date);
                birthDate.setText(selectedDate);
            }
        };
    }

    public void editUserData() {
        handler = new DatabaseHandler(getApplicationContext());
        User newUser = new User();
        newUser.setFIO(fio.getText().toString());
        newUser.setSex(sex.getText().toString());
        newUser.setBirthPlace(birthPlace.getText().toString());
        newUser.setDateOfBirth(selectedDate);
        if (handler.editUser(newUser, oldUser)) {
            Toast.makeText(getApplicationContext(), "Пользователь изменен!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Пользователь не изменен!", Toast.LENGTH_SHORT).show();
        }
        fio.setText("");
        birthDate.setText("");
        birthPlace.setText("");
        sex.setText("");
    }

    public void addUser() {
        handler = new DatabaseHandler(getApplicationContext());
        user = new User();
        user.setFIO(fio.getText().toString());
        user.setDateOfBirth(selectedDate);
        user.setBirthPlace(birthPlace.getText().toString());
        user.setSex(sex.getText().toString());

        if (handler.insertUser(user)) {
            Toast.makeText(getApplicationContext(), "Пользователь сохранен!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Пользователь не сохранен!", Toast.LENGTH_SHORT).show();
        }

        fio.setText("");
        birthDate.setText("");
        birthPlace.setText("");
        sex.setText("");
    }
}
