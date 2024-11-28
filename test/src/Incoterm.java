import java.sql.*;

public class Incoterm {
    private String nom_incoterm;
    public Incoterm() {
        }

    public String getNom_incoterm() {
        return nom_incoterm;
    }

    public void setNom_incoterm(String nom_incoterm) {
        this.nom_incoterm = nom_incoterm;
    }

    //----------------------------------------------------------
        public void ajout_inc(String nom) {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
                String query = "INSERT INTO incoterm (nom_incoterm) VALUES (?)";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, nom);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Incoterm inséré avec succès !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    //----------------------------------------------------------------

    public void supprime_incoterm(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "DELETE FROM incoterm WHERE nom_incoterm = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Incoterm supprimé avec succès !");
            } else {
                System.out.println("Aucun Incoterm trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------
    public void modifi_incoterm(String ancienNom, String nouveauNom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "UPDATE incoterm SET nom_incoterm = ? WHERE nom_incoterm = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nouveauNom);
            statement.setString(2, ancienNom);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Incoterm modifié avec succès !");
            } else {
                System.out.println("Aucun Incoterm trouvé avec le nom spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------

    public void affiche_incoterm() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM incoterm";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String nom_incoterm = resultSet.getString("nom_incoterm");
                System.out.println("Incoterm : " + nom_incoterm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------

    public void affiche_incoterm_nom(String nom) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM incoterm WHERE nom_incoterm = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nom_incoterm = resultSet.getString("nom_incoterm");
                System.out.println("Incoterm : " + nom_incoterm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    }

