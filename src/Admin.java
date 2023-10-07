public class Admin extends Record{
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return recordId + ", " + name + ", " + password + "\n";
    }
}
