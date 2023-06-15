package com.model.projet8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SIEG
 */
public class Employe {
    private String numEmp;
    private String civilite;
    private String nom;
    private String prenoms;
    private String mail;
    private String poste;
    private String lieu;
    // getters
    public String getNumEmp(){
        return this.numEmp;
    }
    public String getCivilite(){
        return this.civilite;
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenoms(){
        return this.prenoms;
    }
    public String getMail(){
        return this.mail;
    }
    public String getPoste(){
        return this.poste;
    }
    public String getLieu(){
        return this.lieu;
    }
    // setters
    public void setNumEmp(String numEmp){
        this.numEmp= numEmp;
    }
    public void setCivilite(String civilite){
        this.civilite = civilite;
    }
    public void setNom(String nom){
        this.nom= nom;
    }
    public void setPrenoms(String prenoms){
        this.prenoms= prenoms;
    }
    public void setMail(String mail){
        this.mail= mail;
    }
    public void setPoste(String poste){
        this.poste= poste;
    }
    public void setLieu(String lieu){
        this.lieu= lieu;
    }
    //Verification doubloons

    //add
    public  void add(){
        try{
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("INSERT INTO EMPLOYE (numemp, civilite, nom, prenoms, mail, poste, lieu)VALUES (?,?,?,?,?,?,?) ");
            prpStmt.setString(1,this.numEmp);
            prpStmt.setString(2,this.civilite);
            prpStmt.setString(3,this.nom);
            prpStmt.setString(4,this.prenoms);
            prpStmt.setString(5,this.mail);
            prpStmt.setString(6,this.poste);
            prpStmt.setString(7,this.lieu);
            prpStmt.executeUpdate();
            System.out.println(nom + prenoms + " added with success");
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
            PreparedStatement prpStmt = conn.prepareStatement("UPDATE EMPLOYE SET  civilite=?, nom=?, prenoms=?, mail=?, poste=?, lieu=? WHERE numemp=? ");
            prpStmt.setString(1,this.civilite);
            prpStmt.setString(2,this.nom);
            prpStmt.setString(3,this.prenoms);
            prpStmt.setString(4,this.mail);
            prpStmt.setString(5,this.poste);
            prpStmt.setString(6,this.lieu);
            prpStmt.setString(7,id);
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
            PreparedStatement prpStmt = conn.prepareStatement("DELETE FROM EMPLOYE WHERE numemp=?");
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
    public List<String> showNumEmploye() {
        List<String> employes = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT numemp FROM EMPLOYE");
            ResultSet res = prpstmt.executeQuery();
            while (res.next()) {
                String employe = res.getString("numemp");
                employes.add(employe);
            }
            prpstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;

    }
    public List<Employe> getAllEmployes() {
        List<Employe> employes = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM EMPLOYE,LIEU WHERE EMPLOYE.lieu = LIEU.idlieu");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setNumEmp(resultSet.getString("numemp"));
                employe.setCivilite(resultSet.getString("civilite"));
                employe.setNom(resultSet.getString("nom"));
                employe.setPrenoms(resultSet.getString("prenoms"));
                employe.setMail(resultSet.getString("mail"));
                employe.setPoste(resultSet.getString("poste"));
                employe.setLieu(resultSet.getString("Design"));
                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employes;
    }
    public List<Employe> getAllAffectationNoAffected() {
        List<Employe> employes = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM  EMPLOYE LEFT OUTER JOIN AFFECTATION ON affectation.numemp = employe.numemp JOIN lieu ON lieu.idlieu = employe.lieu WHERE AFFECTATION.numaffect IS NULL ");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setNumEmp(resultSet.getString("numemp"));
                employe.setCivilite(resultSet.getString("civilite"));
                employe.setNom(resultSet.getString("nom"));
                employe.setPrenoms(resultSet.getString("prenoms"));
                employe.setMail(resultSet.getString("mail"));
                employe.setPoste(resultSet.getString("poste"));
                employe.setLieu(resultSet.getString("Design"));
                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employes;
    }
    public String getEmployeMail(String id){
        String mail=null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT mail FROM EMPLOYE WHERE numemp = ?");
            prpstmt.setString(1,id);
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
               mail = resultSet.getString("mail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mail;
    }
    public List<Employe> getOneEmploye(String rch){
        List<Employe> employes = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM EMPLOYE,LIEU WHERE EMPLOYE.lieu=idlieu AND(UPPER(nom) LIKE ? OR UPPER(prenoms) LIKE ? OR LOWER(nom) LIKE ? OR LOWER(prenoms) LIKE ?) ");
            prpstmt.setString(1,"%"+rch+"%");
            prpstmt.setString(2,"%"+rch+"%");
            prpstmt.setString(3,"%"+rch+"%");
            prpstmt.setString(4,"%"+rch+"%");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Employe employe = new Employe();
                employe.setNumEmp(resultSet.getString("numemp"));
                employe.setCivilite(resultSet.getString("civilite"));
                employe.setNom(resultSet.getString("nom"));
                employe.setPrenoms(resultSet.getString("prenoms"));
                employe.setMail(resultSet.getString("mail"));
                employe.setPoste(resultSet.getString("poste"));
                employe.setLieu(resultSet.getString("lieu"));

                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employes;
    }
    public void UpdateLieuOneElmpoye(String id){
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("UPDATE EMPLOYE SET lieu=? WHERE numemp=? ");
            prpStmt.setString(1,this.lieu);
            prpStmt.setString(2,id);
            prpStmt.executeUpdate();
            System.out.println(id  + " modified with success");
            prpStmt.close();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String lastRecord() {
        String lastRecord = null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT numemp FROM EMPLOYE ORDER BY numemp DESC LIMIT 1 ");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                lastRecord = resultSet.getString("numemp");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return lastRecord;
    }
}