public class Doctor extends Record{
    public String specialty;
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    @Override
    public String toString() {
        return "Id: " + recordId + " - Nombre: " + name + " - Especialidad: " + specialty;
    }
}
