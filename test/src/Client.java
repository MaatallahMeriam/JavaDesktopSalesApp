import java.sql.*;


public class Client {
    private String nom_client;
    public Client() {
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    //----------------------------------------------------------
    public void ajout_cl(String nom_cl,String adresse , String tel , String mail , int code , String cntct) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO client (client,adresse,num_tel,adresse_mail,code_postale,contact) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom_cl);
            statement.setString(2, adresse);
            statement.setString(3, tel);
            statement.setString(4, mail);
            statement.setInt(5, code);
            statement.setString(6, cntct);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_client(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM client WHERE nom = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("client supprimé avec succès !");
            } else {
                System.out.println("Aucun client trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_nom(String ancienNom, String nouveauNom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE client SET nom = ? WHERE nom = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauNom);
            statement.setString(2, ancienNom);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("client modifié avec succès !");
            } else {
                System.out.println("Aucun client trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_clients() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM client";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                System.out.println("client : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_client_nom(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM client WHERE nom = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom_cl = resultSet.getString("nom");
                System.out.println("client : " + nom_cl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







}


