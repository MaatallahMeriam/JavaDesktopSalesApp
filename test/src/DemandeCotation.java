import java.sql.*;

public class DemandeCotation {
    public DemandeCotation(){}
    //----------------------------------------------------------
    public void ajout_dmd_cot(String client ,String nature_transport,String incoterm,String ville_chargement,
                              String ville_livraison,String Colisage) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO demandecotation (client ,nature_transport,incoterm,ville_chargement,ville_livraison,Colisage) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setString(1, client);
            statement.setString(2, nature_transport);
            statement.setString(3, incoterm);
            statement.setString(4, ville_chargement);
            statement.setString(5, ville_livraison);
            statement.setString(6, Colisage);



            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("demande inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_dmd_cot(String nature_transport) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM demandecotation WHERE nature_transport = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nature_transport);
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
    public void modifi_dmd_cot(String ancientrns, String nouveautrns) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE demandecotation SET nature_transport = ? WHERE nature_transport = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveautrns);
            statement.setString(2, ancientrns);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("demande modifié avec succès !");
            } else {
                System.out.println("Aucun demande trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_demandes_cot() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecotation";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String trns = resultSet.getString("nature_transport");
                System.out.println("demande cotation de nature_transport : " + trns);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_dmd_cot(String trns) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecotation WHERE nature_transport = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, trns);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String transporteur = resultSet.getString("nature_transport");
                System.out.println("deamande de nature_transport : " + transporteur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
