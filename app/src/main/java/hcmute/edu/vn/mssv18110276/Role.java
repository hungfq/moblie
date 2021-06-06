package hcmute.edu.vn.mssv18110276;

public class Role {
    private int iID;
    private String sName;

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

    public Role(String Name){
        this.sName = Name;
    }

    public Role(){}

    public void insertRole(DatabaseHandler db){
        db.insertRole(new Role("Admin"));
        db.insertRole(new Role("User"));
    }
}
