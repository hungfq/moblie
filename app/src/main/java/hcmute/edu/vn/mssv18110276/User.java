package hcmute.edu.vn.mssv18110276;

public class User {
    private int iID;
    private String sName;
    private String sEmail;
    private String sPhone;
    private String sPassword;
    private Boolean bVerifyEmail;
    private Boolean bState;
    private int iRole;

    public int getiID() {
        return iID;
    }
    public void setiID(int id) {
        this.iID = id;
    }

    public String getsName() {
        return sName;
    }
    public void setsName(String name) {
        this.sName = name;
    }

    public String getsEmail() {
        return sEmail;
    }
    public void setsEmail(String email) {
        this.sEmail = email;
    }

    public String getsPhone() {
        return sPhone;
    }
    public void setsPhone(String phone) {
        this.sPhone = phone;
    }

    public String getsPassword() {
        return sPassword;
    }
    public void setsPassword(String password) {
        this.sPassword = password;
    }

    public Boolean getbVerifyEmail(){
        return bVerifyEmail;
    };
    public void setbVerifyEmail(Boolean verifyemail){
        this.bVerifyEmail = verifyemail;
    };

    public Boolean getbState(){
        return bState;
    };
    public void setbState(Boolean state){
        this.bState = state;
    };

    public int getiRole() {
        return iRole;
    }
    public void setiRole(int role) {
        this.iRole = role;
    }
}
