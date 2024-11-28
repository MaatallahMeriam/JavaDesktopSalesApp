import java.sql.*;

public class DemandeCorrespondant {
    public DemandeCorrespondant(){}


    //----------------------------------------------------------
    public void ajout_dmd_corr(String correspondant ,String nature_transport,String ville_chargement, String ville_livraison,String incoterm,String colisage) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO demandecorrespondant (correspondant,nature_transport,ville_chargement,ville_livraison,incoterm,colisage) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, correspondant);

            statement.setString(2, nature_transport);
            statement.setString(3, ville_chargement);
            statement.setString(4, ville_livraison);
            statement.setString(5, incoterm);
            statement.setString(6, colisage);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("demande inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_dmd_corr(String transporteur) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM demandecorrespondant WHERE correspondant = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, transporteur);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("demande supprimé avec succès !");
            } else {
                System.out.println("Aucun demande trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_dmd_corr(String ancienTrns, String nouveauTrns) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE demandecorrespondant SET correspondant = ? WHERE correspondant = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauTrns);
            statement.setString(2, ancienTrns);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("demande modifié avec succès !");
            } else {
                System.out.println("Aucun demande trouvé spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_demandes_corr() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String transporteur = resultSet.getString("correspondant");
                System.out.println("demande ayant le transporteur : " + transporteur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_dmd_trns(String trns) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecorrespondant WHERE correspondant = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, trns);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String trnss = resultSet.getString("transporteur");
                System.out.println("demande corespondant ayant le transporteur : " + trnss);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
