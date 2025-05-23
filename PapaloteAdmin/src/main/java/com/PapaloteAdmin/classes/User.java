package com.PapaloteAdmin.classes;

import java.time.LocalDate;

public class  User {

    private static User admin;

    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private int category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "{" +
                " \"name\": \"" + name + '\"' +
                ", \"email\": \"" + email + '\"' +
                ", \"password\": \"" + password + '\"' +
                ", \"birthday\": \"" + birthday +"\""+
                ", \"category\": \"" + category + "\""+
                '}';
    }

    public static User getAdmin(){
        if(admin == null){
            User newUser = new User();
            newUser.setName("admin");
            newUser.setPassword("admin");
            newUser.setEmail("admin@gmail.com");
            newUser.setBirthday(LocalDate.ofYearDay(2004, 300));
            newUser.setCategory(1);
            Request.addUser(newUser);
            admin = newUser;

        }
        return admin;
    }
}
