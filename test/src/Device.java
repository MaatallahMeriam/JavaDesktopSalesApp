import java.sql.*;

public class Device {
    public Device(){}

    public void ajout_device(String monnaie,Float cout_v ,Float cout_a) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "INSERT INTO device (monnaie,cout_achat, cout_vente) VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, monnaie);
            statement.setFloat(2, cout_a);
            statement.setFloat(3, cout_v);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("device inséré avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------------------------

    public void supprime_device(String monnaie) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM device WHERE monnaie = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, monnaie);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("monnaie supprimé avec succès !");
            } else {
                System.out.println("Aucun monnaie trouvé avec le caracteristique spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_device(String monnaieAnc, String monnaieNv) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE device SET monnaieNv = ? WHERE monnaieAnc = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, monnaieNv);
            statement.setString(2, monnaieAnc);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("device modifié avec succès !");
            } else {
                System.out.println("Aucun device trouvé avec le caractere spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_devices() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM device";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String monnaie = resultSet.getString("monnaie");
                System.out.println("device : " + monnaie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_monnaie_nom(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM device WHERE monnaie = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String device = resultSet.getString("monnaie");
                System.out.println("monnaie : " + device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
