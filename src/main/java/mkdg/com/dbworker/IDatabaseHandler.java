package mkdg.com.dbworker;

import android.database.Cursor;

import java.util.List;

public interface IDatabaseHandler {

    public void insertUser(User user);
    public List<User> getUser();
    public void editUser(User user);
    public List<User> sortBy(String filterType);
    public void yearFilter();
    public void clearUserList();
    public void sexFilter();
}
