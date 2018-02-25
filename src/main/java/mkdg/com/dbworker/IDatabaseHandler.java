package mkdg.com.dbworker;

public interface IDatabaseHandler {

    public void createUser(User user);
    public User getUser();
    public void editUser(User user);
    public void sortBy(String filterType);
    public void yearFilter();
    public void sexFilter();
}
