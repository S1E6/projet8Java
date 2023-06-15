package com.model.projet8;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class Lieu {
    private String idlieu;
    private String design;
    private String province;

    // getters
    public String getIdlieu() {
        return this.idlieu;
    }

    public String getDesign() {
        return this.design;
    }

    public String getProvince() {
        return this.province;
    }

    //setters
    public void setIdlieu(String idlieu) {
        this.idlieu = idlieu;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    //Verification doubloons
    public boolean verify(String id) {
        Boolean response = true;
        try {

            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("SELECT lieu FROM EMPLOYE WHERE lieu=?");
            prpStmt.setString(1, id);
            ResultSet rs = prpStmt.executeQuery();
            if (rs.next()) {
                response = true;
                System.out.println("In Base");
            } else {
                response = false;
                System.out.println("Not In");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    //Add
    public void add() {
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("INSERT INTO LIEU (idlieu,design,province)VALUES (?,?,?) ");
            prpStmt.setString(1, this.idlieu);
            prpStmt.setString(2, this.design);
            prpStmt.setString(3, this.province);
            prpStmt.executeUpdate();
            System.out.println(design + " " + province + " added with success");
            prpStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Edit
    public void edit(String id) {
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("UPDATE LIEU SET  idlieu=?, design=?, province=? WHERE idlieu=? ");
            prpStmt.setString(1, this.idlieu);
            prpStmt.setString(2, this.design);
            prpStmt.setString(3, this.province);
            prpStmt.setString(4, id);
            prpStmt.executeUpdate();
            System.out.println(id + " modified with success");
            prpStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete
    public void delete(String id) {
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpStmt = conn.prepareStatement("DELETE FROM LIEU WHERE idlieu=?");
            prpStmt.setString(1, id);
            prpStmt.executeUpdate();
            System.out.println(id + " deleted with success");
            prpStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String showIdLieu(String design) {
        String lieu= null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT idlieu FROM LIEU WHERE design = ?");
            prpstmt.setString(1,design);
            ResultSet res = prpstmt.executeQuery();
            while (res.next()) {
                lieu = res.getString("idlieu");

            }
            prpstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieu;
    }
    public List<String> showAllDesignLieu() {
        List<String> lieux = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT design FROM LIEU");
            ResultSet res = prpstmt.executeQuery();
            while (res.next()) {
                String lieu1 = res.getString("design");
                lieux.add(lieu1);
            }
            prpstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieux;
    }
    public String showDesignLieu(String id) {
        String lieu = null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT design FROM LIEU WHERE idlieu=?");
            prpstmt.setString(1, id);
            ResultSet res = prpstmt.executeQuery();
            while (res.next()) {
                lieu = res.getString("design");
            }
            prpstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieu;
    }
    public List<Lieu> getAllLieu() {
        List<Lieu>  lieux = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM LIEU");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Lieu lieu = new Lieu();
                lieu.setIdlieu(resultSet.getString("idlieu"));
                lieu.setDesign(resultSet.getString("design"));
                lieu.setProvince(resultSet.getString("province"));
                lieux.add(lieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lieux;
    }
    public List<Lieu> getOneLieu(String rch){
        List<Lieu> lieux = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM LIEU WHERE UPPER(design) LIKE ?  OR LOWER(design) LIKE ? ");
            prpstmt.setString(1,"%"+rch+"%");
            prpstmt.setString(2,"%"+rch+"%");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Lieu lieu = new Lieu();
                lieu.setIdlieu(resultSet.getString("idlieu"));
                lieu.setDesign(resultSet.getString("design"));
                lieu.setProvince(resultSet.getString("province"));
                lieux.add(lieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lieux;
    }
    public String lastRecord() {
        String lastRecord = null;
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT idlieu FROM LIEU ORDER BY idlieu DESC LIMIT 1 ");
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                lastRecord = resultSet.getString("idlieu");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return lastRecord;
    }
    public List<Lieu> getOneLieuInfo(String id){
        List<Lieu> lieux = new ArrayList<>();
        try {
            ConnectDB con = new ConnectDB();
            Connection conn = con.connect();
            PreparedStatement prpstmt = conn.prepareStatement("SELECT * FROM LIEU WHERE idlieu = ? ");
            prpstmt.setString(1,id);
            ResultSet resultSet = prpstmt.executeQuery();
            while (resultSet.next()) {
                Lieu lieu = new Lieu();
                lieu.setIdlieu(resultSet.getString("idlieu"));
                lieu.setDesign(resultSet.getString("design"));
                lieu.setProvince(resultSet.getString("province"));
                lieux.add(lieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lieux;
    }

}
