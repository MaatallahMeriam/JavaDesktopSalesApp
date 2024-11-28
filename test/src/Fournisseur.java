import java.sql.*;

public class Fournisseur {

    public Fournisseur (){}

    //----------------------------------------------------------
    public void ajout_Frn(String nom_fr , String adresse_fr , String tel_fr, String mail_fr , String contact_fr) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO fournisseur (nom_fr,adresse_fr,tel_fr,mail_fr,contact_fr) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom_fr);
            statement.setString(2, adresse_fr);
            statement.setString(3, tel_fr);
            statement.setString(4, mail_fr);
            statement.setString(5, contact_fr);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Fournisseur inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_client(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM fournisseur WHERE nom_fr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Fournisseur supprimé avec succès !");
            } else {
                System.out.println("Aucun fournisseur trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_nom(String ancienNom, String nouveauNom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE fournisseur SET nom_fr = ? WHERE nom_fr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauNom);
            statement.setString(2, ancienNom);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("fournisseur modifié avec succès !");
            } else {
                System.out.println("fournisseur client trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_fournisseurs() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM fournisseur";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom = resultSet.getString("nom_fr");
                System.out.println("fournisseur : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_four_nom(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM fournisseur WHERE nom_fr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom_cl = resultSet.getString("nom_fr");
                System.out.println("fournisseur : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
