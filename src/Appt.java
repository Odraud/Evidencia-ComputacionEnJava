import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Appt extends Record{
    public Date dateTime;
    public String doctor;
    public String patient;

    public Date getDateTime() {
        return dateTime;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(dateTime);

        return "Id: " + recordId + " - Nombre: " + name + " - Fecha y hora: " + strDate + " - Id Doctor: " + doctor + " - Id Paciente: " + patient;
    }
}
