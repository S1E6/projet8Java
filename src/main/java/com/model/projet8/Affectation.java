package com.model.projet8;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Affectation {
    //declaration
    private String numAffect;
    private String numEmp;
    private String ancienLieu;
    private String nouveauLieu;
    private LocalDate dateAffect;
    private LocalDate priseService;
    private String nom;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String mail;
    private String prenoms;
    //*****Getters
    public String getPrenoms() {return prenoms;}
    public String getNom() {return nom;}
    public String getNumEmp(){return this.numEmp;}
    public String getNumAffect() {return numAffect;}
    public String getAncienLieu() {return ancienLieu;}
    public String getNouveauLieu() {return nouveauLieu;}
    public LocalDate getDateAffect() {return dateAffect;}
    public LocalDate getPriseService() {return priseService;}
    //****Setters
    public void setNumEmp(String numEmp){this.numEmp = numEmp;}
    public void setNumAffect(String numAffect) {this.numAffect = numAffect;}
    public void setAncienLieu(String ancienLieu) {this.ancienLieu = ancienLieu;}
    public void setNouveauLieu(String nouveauLieu) {this.nouveauLieu = nouveauLieu;}
    public void setDateAffect(LocalDate dateAffect) {this.dateAffect = dateAffect;}
    public void setPriseService(LocalDate priseService) {this.priseService = priseService;}
    public void setNom(String nom) {this.nom = nom;}
    public void setPrenoms(String prenoms) {this.prenoms = prenoms;}

    //add
    public  void add(){
        try{
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("INSERT INTO AFFECTATION (numaffect,numemp,ancienlieu, nouveaulieu, dateaffec, datepriseservice)VALUES (?,?,?,?,?,?) ");
            prpStmt.setString(1,this.numAffect);
            prpStmt.setString(2,this.numEmp);
            prpStmt.setString(3,this.ancienLieu);
            prpStmt.setString(4,this.nouveauLieu);
            prpStmt.setDate(5, java.sql.Date.valueOf(this.dateAffect));
            prpStmt.setDate(6,java.sql.Date.valueOf(this.priseService));
            prpStmt.executeUpdate();
            System.out.println(numAffect + " added with success");
            prpStmt.close();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    //Edit
    public void edit(String id){
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("UPDATE AFFECTATION SET nouveaulieu=?, dateaffec=?, datepriseservice=? WHERE numaffect=? ");
            prpStmt.setString(1,this.nouveauLieu);
            prpStmt.setDate(2, java.sql.Date.valueOf(this.dateAffect));
            prpStmt.setDate(3, Date.valueOf(this.priseService));
            prpStmt.setString(4,id);
            prpStmt.executeUpdate();
            System.out.println(id  + " modified with success");
            prpStmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //Delete
    public void delete(String id){
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("DELETE FROM AFFECTATION WHERE numaffect=?");
            prpStmt.setString(1,id);
            prpStmt.executeUpdate();
            System.out.println(id  + " deleted with success");
            prpStmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Affectation> getAllAffectation() {
        List<Affectation> affectations = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM AFFECTATION, EMPLOYE, LIEU  WHERE AFFECTATION.numemp=EMPLOYE.numemp AND LIEU.idlieu = AFFECTATION.nouveaulieu");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Affectation affectation = new Affectation();
                affectation.setNumAffect(resultSet.getString("numaffect"));
                affectation.setNumEmp(resultSet.getString("numemp"));
                affectation.setNom(resultSet.getString("nom"));
                affectation.setPrenoms(resultSet.getString("prenoms"));
                affectation.setAncienLieu(resultSet.getString("ancienlieu"));
                affectation.setNouveauLieu(resultSet.getString("design"));
                affectation.setDateAffect(resultSet.getDate("dateaffec").toLocalDate());
                affectation.setPriseService(resultSet.getDate("datepriseservice").toLocalDate());
                affectations.add(affectation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectations;
    }
    public List<Affectation> getOneAffectation(String rch) {
        List<Affectation> affectations = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM AFFECTATION, EMPLOYE, LIEU  WHERE AFFECTATION.numemp=EMPLOYE.numemp AND LIEU.idlieu = AFFECTATION.nouveaulieu AND(UPPER(nom) LIKE ? OR UPPER(prenoms) LIKE ? OR LOWER(nom) LIKE ? OR LOWER(prenoms) LIKE ? OR prenoms LIKE ? OR AFFECTATION.numemp LIKE ?)");
            prpstmt.setString(1,"%"+rch+"%");
            prpstmt.setString(2,"%"+rch+"%");
            prpstmt.setString(3,"%"+rch+"%");
            prpstmt.setString(4,"%"+rch+"%");
            prpstmt.setString(5,"%"+rch+"%");
            prpstmt.setString(6,"%"+rch+"%");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Affectation affectation = new Affectation();
                affectation.setNumAffect(resultSet.getString("numaffect"));
                affectation.setNumEmp(resultSet.getString("numemp"));
                affectation.setNom(resultSet.getString("nom"));
                affectation.setPrenoms(resultSet.getString("prenoms"));
                affectation.setAncienLieu(resultSet.getString("ancienlieu"));
                affectation.setNouveauLieu(resultSet.getString("design"));
                affectation.setDateAffect(resultSet.getDate("dateaffec").toLocalDate());
                affectation.setPriseService(resultSet.getDate("datepriseservice").toLocalDate());
                affectations.add(affectation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectations;
    }
    public List<Affectation> getOneAffectationTwoDate(LocalDate debut,LocalDate fin){
        List<Affectation> affectations = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM EMPLOYE , AFFECTATION, LIEU WHERE AFFECTATION.numemp=EMPLOYE.numemp AND (dateaffec BETWEEN ? AND ?) AND LIEU.idlieu = AFFECTATION.nouveaulieu");
            prpstmt.setDate(1, Date.valueOf(debut));
            prpstmt.setDate(2, Date.valueOf(fin));
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Affectation affectation = new Affectation();
                affectation.setNumAffect(resultSet.getString("numaffect"));
                affectation.setNumEmp(resultSet.getString("numemp"));
                affectation.setNom(resultSet.getString("nom"));
                affectation.setPrenoms(resultSet.getString("prenoms"));
                affectation.setAncienLieu(resultSet.getString("ancienlieu"));
                affectation.setNouveauLieu(resultSet.getString("design"));
                affectation.setDateAffect(resultSet.getDate("dateaffec").toLocalDate());
                affectation.setPriseService(resultSet.getDate("datepriseservice").toLocalDate());
                affectations.add(affectation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectations;
    }

    public String lastRecord(){
        String lastRecord = null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT numaffect FROM AFFECTATION ORDER BY numaffect DESC LIMIT 1 ");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                lastRecord = resultSet.getString("numaffect");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return lastRecord;
    }
}
