package mkdg.com.dbworker;

import java.text.ParseException;

public class User {

    private String FIO;
    private String dateOfBirth;
    private String birthPlace;
    private String sex = "sex";
    private int id;

    public User() {

    }

    public User(String FIO, String dateOfBirth, String birthPlace, String sex) {
        this.FIO = FIO;
        this.dateOfBirth = dateOfBirth;
        this.birthPlace = birthPlace;
        this.sex = sex;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String fio) {
        this.FIO = fio;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
       return  dateOfBirth;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
