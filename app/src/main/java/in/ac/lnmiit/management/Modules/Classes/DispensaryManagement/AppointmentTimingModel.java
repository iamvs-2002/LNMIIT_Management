package in.ac.lnmiit.management.Modules.Classes.DispensaryManagement;

public class AppointmentTimingModel {
    String doctorName;
    String doctorTiming;

    public AppointmentTimingModel() {
    }

    public AppointmentTimingModel(String doctorName, String doctorTiming) {
        this.doctorName = doctorName;
        this.doctorTiming = doctorTiming;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTiming() {
        return doctorTiming;
    }

    public void setDoctorTiming(String doctorTiming) {
        this.doctorTiming = doctorTiming;
    }
}
