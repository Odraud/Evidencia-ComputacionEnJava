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
        return "Id: " + recordId + " - Nombre: " + name + " - Contraseña: " + password;
    }
}