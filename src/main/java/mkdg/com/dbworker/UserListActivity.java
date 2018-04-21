package mkdg.com.dbworker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class UserListActivity extends Activity implements View.OnClickListener {

    private ListView userList;
    private SimpleAdapter adapter;
    private DatabaseHandler handler;
    private String LOG_TAG = "myLogs";
    private TextView fio;
    private TextView dateOfBirth;
    private TextView birthPlace;
    private TextView sex;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlertDialog.Builder builder;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userList = findViewById(R.id.list_item);
        userList.invalidateViews();
        userList.refreshDrawableState();

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
                TextView fio1 = view.findViewById(R.id.fio);
                Log.d(LOG_TAG, "FIO: " + fio1.getText().toString());
            }

        });
        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                TextView fio1 = arg1.findViewById(R.id.fio);
                TextView sex = arg1.findViewById(R.id.sex);
                TextView birthDate = arg1.findViewById(R.id.birthDate);
                TextView birthPlace = arg1.findViewById(R.id.birthPlace);

                User user = new User();
                user.setFIO(fio1.getText().toString());
                user.setSex(sex.getText().toString());
                user.setBirthPlace(birthPlace.getText().toString());
                user.setId(pos);
                try {
                    user.setDateOfBirth(birthDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                showDialog(user);

                return true;
            }
        });

        fio = findViewById(R.id.fio_title);
        dateOfBirth = findViewById(R.id.birth_date_title);
        birthPlace = findViewById(R.id.birth_place_title);
        sex = findViewById(R.id.sex_title);

        fio.setOnClickListener(this);
        dateOfBirth.setOnClickListener(this);
        birthPlace.setOnClickListener(this);
        sex.setOnClickListener(this);

        handler = new DatabaseHandler(getApplicationContext());
        fillList(handler.getUsers());

    }


    @Override
    public void onClick(View view) {
        handler = new DatabaseHandler(getApplicationContext());

        switch (view.getId()) {
            case R.id.fio_title: {
                Log.d(LOG_TAG, "Sort by FIO");
                fillList(handler.sortBy("FIO"));
                break;
            }
            case R.id.birth_date_title: {
                Log.d(LOG_TAG, "Sort by DATE_OF_BIRTH");
                fillList(handler.sortBy("DATE_OF_BIRTH"));
                break;
            }
            case R.id.birth_place_title: {
                fillList(handler.sortBy("PLACE"));
                break;
            }
            case R.id.sex_title: {
                fillList(handler.sortBy("SEX"));
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new DatabaseHandler(getApplicationContext());
        try {
            fillList(handler.getUsers());
        } catch (SQLException ex) {
            Toast.makeText(getApplicationContext(), "Не удалось получить пользователей!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler = new DatabaseHandler(getApplicationContext());
        userList = findViewById(R.id.list_item);
        fillList(handler.getUsers());
    }

    /**
     * Метод для отображения диалога
     */
    public void showDialog(final User user) {
        int checkedItem = 0;
        final String[] actions = {"Изменить", "Удалить"};
        final boolean[] items = {false, false};
        handler = new DatabaseHandler(getApplicationContext());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Выберите действие").setCancelable(false);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selected = 0;
                for (int i = 0; i < items.length; i++) {
                    if (items[i]) {
                        selected = i;
                    }
                }
                switch (selected) {
                    case 0:
                        intent = new Intent(getApplicationContext(), AddUserActivity.class);
                        intent.putExtra("fio", user.getFIO());
                        intent.putExtra("birthPlace", user.getBirthPlace());
                        intent.putExtra("birthDate", user.getDateOfBirth());
                        intent.putExtra("sex", user.getSex());
                        intent.putExtra("task", "edit");
                        startActivity(intent);
                        return;

                    case 1:
                        try {
                            handler.deleteUser(user);
                            Toast.makeText(getApplicationContext(), "Пользователь удален!", Toast.LENGTH_SHORT).show();
                        } catch (SQLException ex) {
                            Toast.makeText(getApplicationContext(), "Не удалось удалить пользователя!", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            fillList(handler.getUsers());
                        } catch (SQLException ex) {
                            Toast.makeText(getApplicationContext(), "Не удалить получить список пользователей!", Toast.LENGTH_SHORT).show();
                        }
                        return;
                }
            }

        });
        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setSingleChoiceItems(actions, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (boolean i : items) {
                    i = false;
                }
                switch (which) {
                    case 0:
                        items[0] = true;
                        break;
                    case 1:
                        items[1] = true;
                        break;
                }
            }
        });
        dialog.create().show();
    }

    public void clearList() {
        adapter = new SimpleAdapter(this, null, R.layout.activity_list_item, null, null);
        userList.setAdapter(adapter);
    }


    public void fillList(List<User> users) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map;
        String[] from = {"Name", "Date_of_birth", "Birth_place", "Sex"};
        int[] to = {R.id.fio, R.id.birthDate, R.id.birthPlace, R.id.sex};

        ListIterator<User> iterator = users.listIterator();
        if (iterator.hasNext()) {

            while (iterator.hasNext()) {
                map = new HashMap<>();
                User user = iterator.next();
                map.put("Name", user.getFIO());
                map.put("Date_of_birth", user.getDateOfBirth());
                map.put("Birth_place", user.getBirthPlace());
                map.put("Sex", user.getSex());
                Log.d(LOG_TAG, "Name: " + user.getFIO() + " Date_of_birth: " + user.getDateOfBirth() + " Birth_place: " + user.getBirthPlace() + " Sex: " + user.getSex());
                list.add(map);
            }
            adapter = new SimpleAdapter(this, list, R.layout.activity_list_item, from, to);
        } else {
            map = new HashMap<>();
            map.put("item", "Список пуст");
            list.add(map);
            adapter = new SimpleAdapter(this, list, R.layout.activity_empty_list_item, new String[]{"item"}, new int[]{R.id.empty_list_item});
        }

        userList.setAdapter(adapter);
    }


}
