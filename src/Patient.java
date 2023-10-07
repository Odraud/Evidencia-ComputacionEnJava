public class Patient extends Record{
    public String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "Id: " + recordId + " - Nombre: " + name + " - Teléfono: " + phone;
    }
}
