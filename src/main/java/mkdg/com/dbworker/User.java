package mkdg.com.dbworker;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    /*private String name;
    private String fatherName;
    private String surname;*/
    private String FIO;
    private Date dateOfBirth;
    private String birthPlace;
    private String sex = "sex";

    public User() {

    }

    public User(String FIO, String dateOfBirth, String birthPlace, String sex) throws ParseException {
        this.FIO = FIO;
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        this.dateOfBirth = format.parse(dateOfBirth);
        this.birthPlace = birthPlace;
        this.sex = sex;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String fio) {
        this.FIO = fio;
    }

    public void setDateOfBirth(String dateOfBirth) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        this.dateOfBirth = format.parse(dateOfBirth);
    }

    public String getDateOfBirth() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(dateOfBirth);
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
}
