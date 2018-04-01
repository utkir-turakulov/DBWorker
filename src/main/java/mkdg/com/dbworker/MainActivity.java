package mkdg.com.dbworker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    Button show_user_list;
    Button edit_user;
    Button clear_table;
    DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Открыта главная форма  ",
                Toast.LENGTH_SHORT).show();

        show_user_list = findViewById(R.id.show_user_list);
        edit_user = findViewById(R.id.edit_user);
        clear_table = findViewById(R.id.drop_user_table);

        show_user_list.setOnClickListener(this);
        edit_user.setOnClickListener(this);
        clear_table.setOnClickListener(this);

        handler = new DatabaseHandler(getApplicationContext());
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        handler = new DatabaseHandler(getApplicationContext());
        switch (view.getId()) {
            case R.id.show_user_list: {
                intent = new Intent(this, UserListActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.edit_user: {
                intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.drop_user_table: {
                handler.clearUserList();
                break;
            }

        }

    }
}
