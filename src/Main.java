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
            System.out.println(adminMap.get(id));
        }
        System.out.println("DOCTORS");
        for(String id : doctorMap.keySet()){
            System.out.println(doctorMap.get(id));
        }
        System.out.println("PATIENTS");
        for(String id : patientMap.keySet()){
            System.out.println(patientMap.get(id));
        }
        System.out.println("APPTS");
        for(String id : apptMap.keySet()){
            System.out.println(apptMap.get(id));
        }

        boolean verified = printLogin();

        if(verified == true){
            printMenu();
        } else {
            System.out.println("Saliendo.");
        }

    }
    /*public static HashMap<String,Admin> loadAdmins(){
        HashMap<String,Admin> adminsMap = new HashMap<String,Admin>();
        BufferedReader bufferedReader = null;
        for()
        String info[] = new String[];
        try {
            bufferedReader = new BufferedReader(new FileReader(FILEPATHADMIN));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                info = line.split(", ");
                Admin admin = new Admin();
                admin.recordId = info[0];
                admin.name = info[1];
                admin.password = info[2];

                adminsMap.put(info[0], admin);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
        return adminsMap;
    }*/
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
        boolean verified = false;
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
                            System.out.println("Su contraseña es incorrecta.");
                            System.out.println("Ingrese la opción que desee.");
                            System.out.println("1. Intentar de nuevo.");
                            System.out.println("2. Cambiar de usuario.");
                            System.out.println("3. Salir.");
                            option2 = Integer.parseInt(scan.next());
                            option = 2;
                        }
                    }while (option2 == 1) ;
                } else {
                    System.out.println("Su usuario no cuenta con permisos de administrador.");
                    System.out.println("¿Quiere intentar de nuevo?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    option = Integer.parseInt(scan.next());
                }
            } while (option != 2 || option2 == 2);
        } while(verified != true && option != 2);
        return verified;
    }
    public static void printMenu(){
        int option = 0;
        System.out.println("Bienvenido/a: ");
        System.out.println("Por favor, ingrese a la función deseada");
        System.out.println("1. Crear registro para Doctor");
        System.out.println("2. Crear registro para Paciente");
        System.out.println("3. Crear registo para Cita");
        System.out.println("4. Salir");
        do{
            try {
                switch(option){
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
                        System.out.println("Su agenda se ha guardado exitosamente.");
                        //saveFiles();
                        break;
                    default:
                        System.out.println("Por favor, ingrese una opción válida.");
                        break;
                }
            } catch (NumberFormatException ex){
                System.out.println("Por favor, ingrese una opción válida.");
            }
        } while (option != 4);
    }
    public static void insertDoctor(){
        String drId, name, specialty;
        System.out.println("Ingrese el id del Doctor:");
        drId = scan.next();
        System.out.println("Ingrese el nombre del Doctor:");
        name = scan.next();
        System.out.println("Ingrese la especialidad del Doctor:");
        specialty = scan.next();
        if(!doctorMap.containsKey(drId)){
            Doctor doctor = new Doctor();
            doctor.recordId = drId;
            doctor.name = name;
            doctor.specialty = specialty;
            doctorMap.put(drId,doctor);
            System.out.println("Se creó el registro exitosamente.");
        } else {
            System.out.println("Ese registro ya existe. Verifique los datos.");
        }

    }
    public static void insertPatient(){
        String patientId, name, phone;
        System.out.println("Ingrese el id del Paciente:");
        patientId = scan.next();
        System.out.println("Ingrese el nombre del Paciente:");
        name = scan.next();
        System.out.println("Ingrese el teléfono del Paciente:");
        phone = scan.next();
        if(!patientMap.containsKey(patientId)){
            Patient patient = new Patient();
            patient.recordId = patientId;
            patient.name = name;
            patient.phone = phone;
            patientMap.put(patientId, patient);
        } else {
            System.out.println("Ese registro ya existe. Verifique los datos.");
        }

    }
    public static void insertAppt(){
        System.out.println("Ingrese el id de la Cita:");
        System.out.println("Ingrese fecha y hora de la Cita:");
        System.out.println("Ingrese motivo de la Cita:");
        System.out.println("Ingrese el Id del Doctor asignado:");
        System.out.println("Ingrese el Id del Paciente asignado:");
    }
    /*public static void saveFiles(){
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(FILEPATH));
            for(String phone : addressBook.keySet()){
                bufferedWriter.write(phone + ", " + addressBook.get(phone) + "\n");
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
    }*/
}