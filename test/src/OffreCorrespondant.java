import java.sql.*;

public class OffreCorrespondant {
    public OffreCorrespondant(){}

    //----------------------------------------------------------
    public void ajout_offre_corr(String correspondant ,String	nature_transport , String ville_chargement,String incoterm , String ville_livraison,String colisage,Float fret) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO offrecorrespondant (correspondant,nature_transport,ville_chargement,Incoterm,ville_livraison,colisage,fret) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,correspondant);


            statement.setString(2, nature_transport);
            statement.setString(3, ville_chargement);
            statement.setString(4, incoterm);

            statement.setString(5, ville_livraison);
            statement.setString(6, colisage);

            statement.setFloat(7, fret);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("offre inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_off_corr(String transporteur) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM offrecorrespondant WHERE transporteur = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, transporteur);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("offre supprimé avec succès !");
            } else {
                System.out.println("Aucun offre trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_nom(String ancienTrns, String nouveauTrns) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE offrecorrespondant SET transporteur = ? WHERE transporteur = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauTrns);
            statement.setString(2, ancienTrns);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("offre modifié avec succès !");
            } else {
                System.out.println("Aucun offre trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_offre_corr() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offrecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom = resultSet.getString("transporteur");
                System.out.println("offre_corr : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_offre_trns(String transporteur) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offrecorrespondant WHERE transporteur = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, transporteur);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String offre = resultSet.getString("transporteur");
                System.out.println("offre corr : " + offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
