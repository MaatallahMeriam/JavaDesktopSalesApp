import java.sql.*;

public class Correspondant {

    public Correspondant (){}
    public void ajout_corr(String nom_corr,String adresse , String cntct, String tel , String mail ) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO correspondant (nom_corr,adresse_corr,contact_corr ,num_corr,mail_corr) VALUES (?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom_corr);
            statement.setString(2, adresse);
            statement.setString(3,cntct);
            statement.setString(4, tel);
            statement.setString(5, mail);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Correspondant inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_correspondant(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM correspondant WHERE nom_corr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("correspondant supprimé avec succès !");
            } else {
                System.out.println("Aucun correspondant trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_nom(String ancienNom, String nouveauNom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE correspondant SET nom_corr = ? WHERE nom_corr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauNom);
            statement.setString(2, ancienNom);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("correspondant modifié avec succès !");
            } else {
                System.out.println("correspondant client trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_correspondants() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM correspondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom = resultSet.getString("nom_corr");
                System.out.println("correspondant : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_corr_nom(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM correspondant WHERE nom_corr = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom_cor = resultSet.getString("nom_corr");
                System.out.println("correspondant : " + nom_cor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
