package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Comparable<User>{
    private String username;
    private String password;

    private String avatar;
    private int score;

    private final boolean isGuest;
    private static ArrayList<User> users = new ArrayList<>();

    private static User currentUser;


    public User(String username, String password, boolean isGuest) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.isGuest = isGuest;
        avatar = "blue";
        Random random = new Random();
        if(random.nextInt(2) == 0)avatar = "red";
        if(!isGuest)users.add(this);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }


    @Override
    public int compareTo(User o) {
        return Integer.compare(this.score, o.getScore());
    }
}
