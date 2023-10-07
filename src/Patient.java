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
        return recordId + ", " + name + ", " + phone + "\n";
    }
}
