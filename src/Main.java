import java.io.*;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    final static String FILEPATHADMIN = Paths.get("Admin_AL03056091.txt").toAbsolutePath().toString();
    final static String FILEPATHDR = Paths.get("Doctor_AL03056091.txt").toAbsolutePath().toString();
    final static String FILEPATHPATIENT = Paths.get("Patient_AL03056091.txt").toAbsolutePath().toString();
    final static String FILEPATHAPPT = Paths.get("Appt_AL03056091.txt").toAbsolutePath().toString();
    static HashMap<String,Admin> adminMap = new HashMap<String,Admin>();
    static HashMap<String,Doctor> doctorMap = new HashMap<String,Doctor>();
    static HashMap<String,Patient> patientMap = new HashMap<String,Patient>();
    static HashMap<String,Appt> apptMap = new HashMap<String,Appt>();
    static final long ONE_MINUTE_IN_MILLIS = 60000;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        HashMap<String,String> filePathMap = new HashMap<String,String>();
        filePathMap.put(FILEPATHADMIN, "Admin");
        filePathMap.put(FILEPATHDR, "Doctor");
        filePathMap.put(FILEPATHPATIENT, "Patient");
        filePathMap.put(FILEPATHAPPT, "Appt");

        loadInfo(filePathMap);
        System.out.println("ADMINS");
        for(String id : adminMap.keySet()){
            System.out.print(adminMap.get(id));
        }
        System.out.println("DOCTORS");
        for(String id : doctorMap.keySet()){
            System.out.print(doctorMap.get(id));
        }
        System.out.println("PATIENTS");
        for(String id : patientMap.keySet()){
            System.out.print(patientMap.get(id));
        }
        System.out.println("APPTS");
        for(String id : apptMap.keySet()){
            System.out.print(apptMap.get(id));
        }

        boolean verified = printLogin();

        if(verified == true){
            printMenu(filePathMap);
        } else {
            System.out.println("Saliendo.");
        }

    }
    public static void loadInfo(HashMap<String,String> filePathMap){
        BufferedReader bufferedReader = null;
        for(String filePath : filePathMap.keySet()) {
            String info[];
            try {
                bufferedReader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    info = line.split(", ");
                    if(filePathMap.get(filePath).equals("Admin")){
                        Admin admin = new Admin();
                        admin.recordId = info[0];
                        admin.name = info[1];
                        admin.password = info[2];
                        adminMap.put(info[0], admin);
                    } else if(filePathMap.get(filePath).equals("Doctor")){
                        Doctor doctor = new Doctor();
                        doctor.recordId = info[0];
                        doctor.name = info[1];
                        doctor.specialty = info[2];
                        doctorMap.put(info[0],doctor);
                    } else if(filePathMap.get(filePath).equals("Patient")){
                        Patient patient = new Patient();
                        patient.recordId = info[0];
                        patient.name = info[1];
                        patient.phone = info[2];
                        patientMap.put(info[0],patient);
                    } else if(filePathMap.get(filePath).equals("Appt")){
                        Appt appt = new Appt();
                        appt.recordId = info[0];
                        appt.name = info[1];
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date date = df.parse(info[2]);
                        appt.dateTime = date;
                        appt.doctor = info[3];
                        appt.patient = info[4];
                        apptMap.put(info[0],appt);
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException catched while reading: " + e.getMessage());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    System.out.println("IOException catched while closing: " + e.getMessage());
                }
            }
        }

    }
    public static boolean printLogin(){
        boolean verified = false, validValue = true;
        int option = 0, option2 = 0;
        String user, passwordIn;
        do{
            System.out.println("Bienvenido/a: ");
            do{
                System.out.println("Ingrese su Id:");
                user = scan.next();
                if (adminMap.containsKey(user)) {
                    Admin admin = adminMap.get(user);
                    do {
                        System.out.println("Ingrese su contraseña:");
                        passwordIn = scan.next();
                        if (admin.password.equals(passwordIn)) {
                            verified = true;
                            option = 2;
                            option2 = 3;
                            System.out.println("Accediendo. Espere un momento.");
                        } else {
                            do{
                                System.out.println("Su contraseña es incorrecta.");
                                System.out.println("Ingrese la opción que desee.");
                                System.out.println("1. Intentar de nuevo.");
                                System.out.println("2. Cambiar de usuario.");
                                System.out.println("3. Salir.");
                                try{
                                    validValue = true;
                                    option2 = Integer.parseInt(scan.next());
                                    option = 2;
                                } catch (NumberFormatException ex) {
                                    validValue = false;
                                    System.out.println("Por favor, ingresa un valor válido.");
                                }
                            } while (validValue != true);
                        }
                    }while (option2 == 1) ;
                } else {
                    do {
                        System.out.println("Su usuario no cuenta con permisos de administrador.");
                        System.out.println("¿Quiere intentar de nuevo?");
                        System.out.println("1. Sí");
                        System.out.println("2. No");
                        try {
                            option = Integer.parseInt(scan.next());
                        } catch (NumberFormatException ex) {
                            validValue = false;
                            System.out.println("Por favor, ingresa un valor válido.");
                        }
                    } while (validValue != true);
                }
            } while (option != 2 || option2 == 2);
        } while(verified != true && option != 2);
        return verified;
    }
    public static void printMenu(HashMap<String,String> filePathMap){
        int option = 0;
        boolean validValue = true;
        do {
            System.out.println("Bienvenido/a: ");
            System.out.println("Por favor, ingrese a la función deseada");
            System.out.println("1. Crear registro para Doctor");
            System.out.println("2. Crear registro para Paciente");
            System.out.println("3. Crear registo para Cita");
            System.out.println("4. Salir");
            option = Integer.parseInt(scan.next());
            scan.nextLine();
            try {
                validValue = true;
                switch (option) {
                    case 1:
                        insertDoctor();
                        break;
                    case 2:
                        insertPatient();
                        break;
                    case 3:
                        insertAppt();
                        break;
                    case 4:
                        System.out.println("La base de datos se ha guardado exitosamente.");
                        filePathMap.remove(FILEPATHADMIN);
                        saveFiles(filePathMap);
                        break;
                    default:
                        System.out.println("Por favor, ingrese una opción válida.");
                        break;
                }
            } catch (NumberFormatException ex) {
                validValue = false;
                System.out.println("Por favor, ingrese una opción válida.");
            }
        } while (option != 4 && validValue != false);
    }
    public static void insertDoctor(){
        String drId, name, specialty;
        boolean recordCreated = true;
        do {
            System.out.println("Ingrese el id del Doctor:");
            drId = scan.nextLine();
            if (!doctorMap.containsKey(drId)) {
                System.out.println("Ingrese el nombre del Doctor:");
                name = scan.nextLine();
                System.out.println("Ingrese la especialidad del Doctor:");
                specialty = scan.nextLine();
                Doctor doctor = new Doctor();
                doctor.recordId = drId;
                doctor.name = name;
                doctor.specialty = specialty;
                doctorMap.put(drId, doctor);
                recordCreated = true;
                System.out.println("Se creó el registro exitosamente.");
            } else {
                recordCreated = false;
                System.out.println("Ese registro ya existe. Verifique los datos.");
            }
        } while(recordCreated != true);
    }
    public static void insertPatient(){
        String patientId, name, phone;
        boolean recordCreated = true;
        do {
            System.out.println("Ingrese el id del Paciente:");
            patientId = scan.nextLine();
            if (!patientMap.containsKey(patientId)) {
                System.out.println("Ingrese el nombre del Paciente:");
                name = scan.nextLine();
                System.out.println("Ingrese el teléfono del Paciente:");
                phone = scan.nextLine();
                Patient patient = new Patient();
                patient.recordId = patientId;
                patient.name = name;
                patient.phone = phone;
                patientMap.put(patientId, patient);
                System.out.println("Se creó el registro exitosamente.");
                recordCreated = true;
            } else {
                recordCreated = false;
                System.out.println("Ese registro ya existe. Verifique los datos.");
            }
        } while(recordCreated != true);
    }
    public static void insertAppt(){
        String apptId, reason, dateTime, drId, patientId;
        boolean recordCreated = true, validDr = true, validPatient = true;
        System.out.println("Ingrese el id de la Cita:");
        apptId = scan.nextLine();
        do {
            try {
                if (!apptMap.containsKey(apptId)) {
                    System.out.println("Ingrese motivo de la Cita:");
                    reason = scan.nextLine();
                    System.out.println("Ingrese fecha y hora de la Cita en formato dd/MM/yyyy HH:mm:");
                    dateTime = scan.nextLine();
                    Appt appt = new Appt();
                    appt.recordId = apptId;
                    appt.name = reason;
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = df.parse(dateTime);
                    appt.dateTime = date;
                    do {
                        System.out.println("Ingrese el Id del Doctor asignado:");
                        drId = scan.nextLine();
                        if (doctorMap.containsKey(drId)) {
                            validDr = checkAvailability(drId, date, "Doctor");
                        } else {
                            System.out.println("Ese Id no se encontró en la base de datos. Se le solicitará la creación de un registro nuevo.");
                            insertDoctor();
                            validDr = true;
                        }
                    } while (validDr != true);
                    do {
                        System.out.println("Ingrese el Id del Paciente asignado:");
                        patientId = scan.nextLine();
                        if (patientMap.containsKey(patientId)) {
                            validPatient = checkAvailability(patientId, date, "Patient");
                        } else {
                            System.out.println("Ese Id no se encontró en la base de datos. Se le solicitará la creación de un registro nuevo.");
                            insertPatient();
                            validPatient = true;
                        }
                    } while (validPatient != true);
                    appt.doctor = drId;
                    appt.patient = patientId;

                    apptMap.put(apptId, appt);
                    System.out.println("Se creó el registro exitosamente.");
                    recordCreated=true;
                } else {
                    recordCreated=false;
                    System.out.println("Ese registro ya existe. Verifique los datos.");
                }
            } catch (ParseException e) {
                System.out.println("La fecha y hora tienen un formato inválido.");
                recordCreated=false;
            }
        } while(recordCreated!=true);
    }
    public static void saveFiles(HashMap<String,String> filePathMap){
        BufferedWriter bufferedWriter = null;
        for(String filePath : filePathMap.keySet()){
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(filePath));
                if(filePathMap.get(filePath).equals("Doctor")){
                    for(String record : doctorMap.keySet()){
                        bufferedWriter.write(doctorMap.get(record).toString());
                    }
                } else if(filePathMap.get(filePath).equals("Patient")){
                    for(String record : patientMap.keySet()){
                        bufferedWriter.write(patientMap.get(record).toString());
                    }
                } else if(filePathMap.get(filePath).equals("Appt")){
                    for(String record : apptMap.keySet()){
                        bufferedWriter.write(apptMap.get(record).toString());
                    }
                }

            } catch(IOException e) {
                System.out.println("IOException catched while reading: " + e.getMessage());
            } finally {
                try {
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    System.out.println("IOException catched while closing: " + e.getMessage());
                }
            }
        }
    }
    public static boolean checkAvailability(String recordId, Date date, String object){
        boolean available = true;
        for(String record : apptMap.keySet()){
            if(apptMap.get(record).doctor.equals(recordId) || apptMap.get(record).patient.equals(recordId)){
                Date apptDateTime = apptMap.get(record).dateTime;
                Date apptDateTime30 = addMinutesToDate(30, apptDateTime);
                Date date30 = addMinutesToDate(30, date);
                if(date.equals(apptDateTime) || (date.after(apptDateTime) && date.before(apptDateTime30)) || (date30.after(apptDateTime) && date30.before(apptDateTime30))){
                    if(object.equals("Doctor")){
                        System.out.println("Ese Doctor ya tiene una cita programada para ese momento.");
                        available = false;
                    } else if (object.equals("Patient")){
                        System.out.println("Ese Paciente ya tiene una cita programada para ese momento.");
                        available = false;
                    }
                }
            }
        }
        return available;
    }

    public static Date addMinutesToDate(int minutes, Date beforeTime) {

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs
                + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
}