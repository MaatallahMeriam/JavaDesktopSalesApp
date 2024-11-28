import java.sql.*;

public class RoutingOrder {
    public RoutingOrder(){}


    //----------------------------------------------------------
    public void ajout_routing(String type_r,String client,String	transporteur,String	fournisseur,Float tarif,String delais_offre,String observation,String colisage,
                         String chargement,String livraison,String incoterm) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO routingorder (type_r,client,transporteur,fournisseur,tarif,delais_offre,observation,colisage,chargement,livraison,incoterm) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, type_r);
            statement.setString(2, client);
            statement.setString(3, transporteur);
            statement.setString(4, fournisseur);
            statement.setFloat(5, tarif);
            statement.setString(6, delais_offre);
            statement.setString(7, observation);
            statement.setString(8, colisage);
            statement.setString(9, chargement);
            statement.setString(10, livraison);
            statement.setString(11, incoterm);



            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ROUTING inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------

    public void supprime_routing(String client) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM routingorder WHERE client = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, client);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("routing supprimé avec succès !");
            } else {
                System.out.println("Aucun routing trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_routing(String anciencl, String nouveaucl) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE routingorder SET client = ? WHERE client = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveaucl);
            statement.setString(2, anciencl);
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

    public void affiche_routings() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM routingorder";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String client = resultSet.getString("client");
                System.out.println("routing par le client: " + client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_routing_cl(String client) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM routingorder WHERE client = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, client);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom_cl = resultSet.getString("client");
                System.out.println("routing par le client : " + nom_cl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
