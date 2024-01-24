package org.chreso.edots;

public class PatientDispensationStatusEvent {
    private String patient_dispensation_status_uuid;
    private String patient_uuid;
    private String patient_dispensation_status;

    public PatientDispensationStatusEvent(String patient_dispensation_status_uuid, String patient_uuid, String patient_dispensation_status) {
        this.patient_dispensation_status_uuid = patient_dispensation_status_uuid;
        this.patient_uuid = patient_uuid;
        this.patient_dispensation_status = patient_dispensation_status;
    }

    public String getPatient_dispensation_status_uuid() {
        return patient_dispensation_status_uuid;
    }

    public void setPatient_dispensation_status_uuid(String patient_dispensation_status_uuid) {
        this.patient_dispensation_status_uuid = patient_dispensation_status_uuid;
    }

    public String getPatient_uuid() {
        return patient_uuid;
    }

    public void setPatient_uuid(String patient_uuid) {
        this.patient_uuid = patient_uuid;
    }

    public String getPatient_dispensation_status() {
        return patient_dispensation_status;
    }

    public void setPatient_dispensation_status(String patient_dispensation_status) {
        this.patient_dispensation_status = patient_dispensation_status;
    }
}
