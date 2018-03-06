package mkdg.com.dbworker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class User_list extends Activity implements View.OnClickListener {

    private ListView userList;
    private SimpleAdapter adapter;
    private DatabaseHandler handler;
    private String LOG_TAG = "myLogs";
    private TextView fio;
    private TextView dateOfBirth;
    private TextView birthPlace;
    private TextView sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        fillList(handler.getUser());

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
        fillList(handler.getUser());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler = new DatabaseHandler(getApplicationContext());
        userList = findViewById(R.id.list_item);
        fillList(handler.getUser());
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
