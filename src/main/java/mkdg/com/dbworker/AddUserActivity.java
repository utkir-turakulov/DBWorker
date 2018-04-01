package mkdg.com.dbworker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

public class AddUserActivity extends Activity implements View.OnClickListener {

    Button save_button;
    User user;
    DatabaseHandler handler;
    EditText fio;
    EditText birthDate;
    EditText birthPlace;
    EditText sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        save_button = findViewById(R.id.save_user_button);
        fio = findViewById(R.id.FIO);
        birthDate = findViewById(R.id.date_of_birth_editor);
        birthPlace = findViewById(R.id.birth_place_editor);
        sex = findViewById(R.id.sex_field);
        save_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        handler = new DatabaseHandler(getApplicationContext());
        user = new User();
        switch (view.getId()) {
            case R.id.save_user_button: {
                user.setFIO(fio.getText().toString());
                try {
                    user.setDateOfBirth(birthDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                user.setBirthPlace(birthPlace.getText().toString());
                user.setSex(sex.getText().toString());
                handler.insertUser(user);
                fio.setText("");
                birthDate.setText("");
                birthPlace.setText("");
                sex.setText("");
                Toast.makeText(getApplicationContext(), "User saved", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.test: {
                break;
            }

        }
    }
}
