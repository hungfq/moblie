package hcmute.edu.vn.mssv18110276;

public class User {
    private int iID;
    private String sName;
    private String sEmail;
    private String sPhone;
    private String sPassword;
    private int iVerifyEmail;
    private int iState;
    private int iRole;
    private byte[] sSource;

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

    public int getiVerifyEmail(){
        return iVerifyEmail;
    };
    public void setbVerifyEmail(int verifyemail){
        this.iVerifyEmail = verifyemail;
    };

    public int getiState(){
        return iState;
    };
    public void setbState(int state){
        this.iState = state;
    };

    public int getiRole() {
        return iRole;
    }
    public void setiRole(int role) {
        this.iRole = role;
    }

    public byte[] getsSource() {
        return sSource;
    }
    public void setsSource(byte[] Src){this.sSource = Src; }
    public User(){};

    public User(String Name, String Email, String Phone, String Password, int VerifyEmail, int State, int Role, byte[] source){
        this.sName = Name;
        this.sEmail = Email;
        this.sPhone = Phone;
        this.sPassword = Password;
        this.iVerifyEmail = VerifyEmail;
        this.iState = State;
        this.iRole = Role;
        this.sSource = source;
    }

    public User(int ID, String Name, String Email, String Phone, int State, int Role){
        this.iID = ID;
        this.sName = Name;
        this.sEmail = Email;
        this.sPhone = Phone;
        this.iState = State;
        this.iRole = Role;
    }

    public void insertDefaultUser(DatabaseHandler db){
        db.registerUser(new User("Tiên Giang Admin","18110276@student.hcmute.edu.vn","0383509677","matkhau123",1,1,1, null));
        db.registerUser(new User("Tiên Giang User","default@student.hcmute.edu.vn","0383509678","matkhau123",1,1,2, null));
    }
}
