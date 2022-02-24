package in.ac.lnmiit.management.Modules.Classes.DispensaryManagement;

public class AppointmentTimingModel {
    String doctorType;
    String doctorTiming;

    public AppointmentTimingModel() {
    }

    public AppointmentTimingModel(String doctorName, String doctorTiming) {
        this.doctorType = doctorName;
        this.doctorTiming = doctorTiming;
    }

    public String getDoctorName() {
        return doctorType;
    }

    public void setDoctorName(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorTiming() {
        return doctorTiming;
    }

    public void setDoctorTiming(String doctorTiming) {
        this.doctorTiming = doctorTiming;
    }
}
