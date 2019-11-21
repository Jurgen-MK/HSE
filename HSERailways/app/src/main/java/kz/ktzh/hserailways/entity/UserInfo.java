package kz.ktzh.hserailways.entity;

public class UserInfo {

//    private static UserInfo uInstance;
    int id;
    String username;
    String fullname;
    String phone;
    String email;
    String position;

//    protected UserInfo() {
//
//    }

    public UserInfo(String username, String fullname, String phone, String email, String position) {
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.position = position;
    }

//    public static UserInfo getInstance() {
//        if (uInstance == null)
//            uInstance = new UserInfo();
//        return uInstance;
//    }

//    public void setUserInfo(UserInfo ui){
//        uInstance = ui;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



}