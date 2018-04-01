package mkdg.com.dbworker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

public class EditUserActivity extends Activity implements View.OnClickListener {

    private DatabaseHandler handler;
    EditText fio;
    EditText birthDate;
    EditText birthPlace;
    EditText sex;
    User oldUser;
    Button editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        fio = findViewById(R.id.edit_user_fio);
        birthPlace = findViewById(R.id.edit_user_birthPlace);
        sex = findViewById(R.id.edit_user_sex);
        birthDate = findViewById(R.id.edit_user_birthDate);
        editUser = findViewById(R.id.edit_user_edit);

        Intent intent = getIntent();
        handler = new DatabaseHandler(getApplicationContext());
        oldUser = new User();

        if (intent != null) {
            oldUser.setFIO(intent.getStringExtra("fio"));
            try {
                oldUser.setDateOfBirth(intent.getStringExtra("birthDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            oldUser.setBirthPlace(intent.getStringExtra("birthPlace"));
            oldUser.setSex(intent.getStringExtra("sex"));
                    /*Заполняем форму*/
            fio.setText(intent.getStringExtra("fio"));
            birthDate.setText(intent.getStringExtra("birthDate"));
            birthPlace.setText(intent.getStringExtra("birthPlace"));
            sex.setText(intent.getStringExtra("sex"));
        }

        editUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        handler = new DatabaseHandler(getApplicationContext());
        User newUser = new User();

        switch (v.getId()){
            case R.id.edit_user_edit:
                newUser.setFIO(fio.getText().toString());
                newUser.setSex(sex.getText().toString());
                newUser.setBirthPlace(birthPlace.getText().toString());
                try {
                    newUser.setDateOfBirth(birthDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                handler.editUser(newUser,oldUser);
                break;
        }

    }
}
