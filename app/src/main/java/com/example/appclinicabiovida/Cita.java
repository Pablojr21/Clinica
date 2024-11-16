package com.example.appclinicabiovida;

public class Cita
{
    String codigoCita;
    String dniPaciente;
    String sintomasPaciente;
    String estadoCita;
    String motivocancelacionCita;
    int idHorario;


    public Cita() {
    }

    public Cita(String codigoCita, String dniPaciente, String sintomasPaciente, String estadoCita, String motivocancelacionCita, int idHorario) {
        this.codigoCita = codigoCita;
        this.dniPaciente = dniPaciente;
        this.sintomasPaciente = sintomasPaciente;
        this.estadoCita = estadoCita;
        this.motivocancelacionCita = motivocancelacionCita;
        this.idHorario = idHorario;
    }




    public String getCodigoCita() {
        return codigoCita;
    }

    public void setCodigoCita(String codigoCita) {
        this.codigoCita = codigoCita;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getSintomasPaciente() {
        return sintomasPaciente;
    }

    public void setSintomasPaciente(String sintomasPaciente) {
        this.sintomasPaciente = sintomasPaciente;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getMotivocancelacionCita() {
        return motivocancelacionCita;
    }

    public void setMotivocancelacionCita(String motivocancelacionCita) {
        this.motivocancelacionCita = motivocancelacionCita;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
}
