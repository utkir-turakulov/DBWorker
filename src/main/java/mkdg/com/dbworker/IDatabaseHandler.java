package mkdg.com.dbworker;

import android.database.Cursor;

import java.util.List;

public interface IDatabaseHandler {

    void insertUser(User user);

    List<User> getUsers();

    void editUser(User new_user, User old_user);

    List<User> sortBy(String filterType);

    void yearFilter();

    void clearUserList();

    void deleteUser(User user);

    void sexFilter();
}
