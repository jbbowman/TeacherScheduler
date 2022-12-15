package edu.hamilton.teacherscheduler.model;

public class Teacher {
    private int teacherID;
    private String firstName;
    private String lastName;
    private String phone;
    private int hoursAvailable;

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHoursAvailable() {
        return hoursAvailable;
    }

    public void setHoursAvailable(int hoursAvailable) {
        this.hoursAvailable = hoursAvailable;
    }
}