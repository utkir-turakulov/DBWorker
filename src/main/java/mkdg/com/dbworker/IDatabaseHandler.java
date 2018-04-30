package mkdg.com.dbworker;

import android.database.Cursor;

import java.text.ParseException;
import java.util.List;

public interface IDatabaseHandler {

    boolean insertUser(User user);

    List<User> getUsers() throws ParseException;

    boolean editUser(User new_user, User old_user);

    List<User> sortBy(String filterType) throws ParseException;

    void yearFilter();

    void clearUserList();

    void deleteUser(User user);

    void sexFilter();
}
