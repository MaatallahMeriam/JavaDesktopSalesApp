import java.sql.*;

public class OffreClient {
    public OffreClient(){}
    //----------------------------------------------------------
    public void ajout_offreCl(String client, String chargement,String	livraison,String type_chargement ,String incoterm,String colisage, Float tarif, String validite	, String observation) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO offreclient (client,chargement,livraison,type_chargement,incoterm,colisage,tarif,validite,observation) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, client);
            statement.setString(2, chargement);
            statement.setString(3, livraison);
            statement.setString(4, type_chargement);
            statement.setString(5, incoterm);
            statement.setString(6, colisage);
            statement.setFloat(7, tarif);
            statement.setString(8, validite);

            statement.setString(9, observation);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("offre inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_offre(String colisage) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM offreclient WHERE colisage = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, colisage);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("offre supprimé avec succès !");
            } else {
                System.out.println("Aucun offre trouvé avec le caracteristique spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_offre(String anciencol, String nouveaucol) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE offreclient SET colisage = ? WHERE colisage = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveaucol);
            statement.setString(2, anciencol);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("offre modifié avec succès !");
            } else {
                System.out.println("Aucun offre trouvé spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_offreClients() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offreclient";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom = resultSet.getString("colisage");
                System.out.println("offre : " + nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_offreCl_nom(String colisage) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offreclient WHERE colisage = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, colisage);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String offre_cl = resultSet.getString("colisage");
                System.out.println("offre : " + offre_cl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
