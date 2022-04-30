package in.ac.lnmiit.management.Modules.Classes.DispensaryManagement;

public class AppointmentStatusModel {
    String name, email, issue, date, time;
    boolean approved;

    public AppointmentStatusModel() {
    }

    public AppointmentStatusModel(String name, String email, String issue, String date, String time, boolean approved) {
        this.name = name;
        this.email = email;
        this.issue = issue;
        this.date = date;
        this.time = time;
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
