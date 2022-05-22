package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

public class UserController {
    private static UserController instance;

    private UserController(){
        UserController.loadInfo();
    }
    public static UserController getInstance(){
        if(instance == null)instance = new UserController();
        return instance;
    }

    public String login(String username, String password){
        for(User user : User.getUsers()){
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    User.setCurrentUser(user);
                    return "login successful";
                }else {
                    return "incorrect username or password";
                }
            }
        }
        return "incorrect username or password";
    }

    public void loginGuest(){
        User.setCurrentUser(new User("Guest", "", true));
    }
    public String register(String username, String password){
        if(username.matches("\\s*"))return "please enter username";
        if(password.matches("\\s*"))return "please enter password";
        String message;
        if(!((message = checkPasswordStrength(password)).equals("ok")))return message;
        for(User user : User.getUsers()){
            if(user.getUsername().equals(username)){
                return "a user with this username already exists";
            }
        }
        User.setCurrentUser(new User(username, password, false));
        UserController.saveInfo();
        return "register successful";
    }

    public String changePassword(String currentPassword, String newPassword){
        if(User.getCurrentUser().isGuest())return "you are in guest mode";
        if(currentPassword.matches("\\s*"))return "please enter current password";
        if(newPassword.matches("\\s*"))return "please enter new password";
        String message;
        if(!((message = checkPasswordStrength(newPassword)).equals("ok")))return message;
        if(!(User.getCurrentUser().getPassword().equals(currentPassword)))
            return "incorrect password";
        User.getCurrentUser().setPassword(newPassword);
        UserController.saveInfo();
        return "password change successful";
    }

    public String changeUsername(String newUsername, String password){
        if(User.getCurrentUser().isGuest())return "you are in guest mode";
        if(newUsername.matches("\\s*"))return "please enter new username";
        if(password.matches("\\s*"))return "please enter password";
        if(!(User.getCurrentUser().getPassword().equals(password))) return "incorrect password";
        for(User user : User.getUsers()){
            if(user.getUsername().equals(newUsername)){
                return "a user with this username already exists";
            }
        }
        User.getCurrentUser().setUsername(newUsername);
        UserController.saveInfo();
        return "username change successful";
    }

    public void logout(){
        User.setCurrentUser(null);
    }
    public String removeUser(String password){
        if(User.getCurrentUser().isGuest()) return "user remove successful";
        if(password.matches("\\s*"))return "please enter current password";
        if(!(User.getCurrentUser().getPassword().equals(password))) return "incorrect password";
        User.getUsers().remove(User.getCurrentUser());
        logout();
        UserController.saveInfo();
        return "user remove successful";
    }
    public String checkPasswordStrength(String password){
        if(password.length() < 4)return "weak password";
        return "ok";
    }

    public static void saveInfo() {
        try {
            FileWriter fileWriter = new FileWriter(new File("src\\main\\resources\\UserInfo.json"));
            Gson gson = new GsonBuilder().create();
            fileWriter.write(gson.toJson(User.getUsers()));
            fileWriter.close();
        }catch (Exception exception){
            System.out.println("ERROR writing UserInfo.json");
        }
    }
    public static void loadInfo(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\UserInfo.json"));
            String json = new String(fileInputStream.readAllBytes());
            Gson gson = new GsonBuilder().create();
            User.setUsers(gson.fromJson(json, new TypeToken<List<User>>(){}.getType()));
            fileInputStream.close();
        }catch (Exception exception){
            System.out.println("ERROR reading UserInfo.json");
        }
    }



}
