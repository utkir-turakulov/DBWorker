package mkdg.com.dbworker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String name;
    private String fatherName;
    private String surname;
    private Date dateOfBirth;
    private String birthPlace;
    private String sex;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFatherName(String fatherName){
        this.fatherName = fatherName;
    }

    public String getFatherName(){
        return fatherName;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getSurname(){
        return surname;
    }

    public void setDateOfBirth(String dateOfBirth) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        this.dateOfBirth = format.parse(dateOfBirth);
    }

    public String getDateOfBirth(){
        return dateOfBirth.toString();
    }

    public void setBirthPlace(String birthPlace){
        this.birthPlace = birthPlace;
    }

    public String getBirthPlace(){
        return birthPlace;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}
