import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DmdCorrespondant extends JFrame {
    private JTextField trans;
    public JTextField dest;
    public JLabel charge;
    //public JComboBox comboBox1;
    public JLabel nature;

    private JButton enregistrerButton;
    private JButton annulerButton;
    private JPanel dmdCot;
    public JTextArea colisage;
    private JTextField t_corr;
    public JLabel incoterm;
    public JLabel liv;
    public JLabel coll;

    private JButton supprimerDemandeButton;
    private JButton imprimerButton1;
    private JTable table1;
    private JComboBox comboBox1;
    public DmdCorrespondant dm = this ;
    public DemandeCorrespondant DmdCor = new DemandeCorrespondant();
    public DmdClient DmdCL ;


    public DmdCorrespondant(){
       // super(parent);
        setTitle("Demande Cotation Correspondant");
        setContentPane(dmdCot);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        load_demand_corr();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM correspondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                comboBox1.addItem(resultSet.getString("nom_corr"));
            }}
        catch (SQLException ex) {
            ex.printStackTrace();
        }


        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.setVisible(false);
                DmdCL = new DmdClient();
                DmdCL.setVisible(true);
            }
        });
        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                demandeCorres();
                rafraichirTable();

            }
        });


        supprimerDemandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                System.out.println(selectedRow);



                if (selectedRow != -1) { // Vérifiez si une ligne est sélectionnée
                    int option = JOptionPane.showConfirmDialog(
                            dm,
                            "Êtes-vous sûr de vouloir supprimer cette demande ?",
                            "Confirmation de suppression",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) { // Vérifiez si une ligne est sélectionnée
                    Object objValue = table1.getValueAt(selectedRow, 0);
                    String strValue = objValue.toString();
                    int id = Integer.parseInt(strValue);
                    System.out.println(id);


                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
                        String query = "DELETE FROM demandecorrespondant WHERE id_dm_corr = ?";
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.setInt(1, id);
                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println(id+" supprimé avec succès !");
                        //JOptionPane.showMessageDialog(dm," supprimé avec succès !","Suppression",JOptionPane.INFORMATION_MESSAGE);
                                //return ;
                        }

                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }}
                }else {JOptionPane.showMessageDialog(dm," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                    return ;}
                rafraichirTable();
            }});
        imprimerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impression_demandeCorr();

            }
        });
    }

    public void load_demand_corr(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String correspondant , incoterm ,chargement, nature , livraison,id,colisage;
            while (resultSet.next()){
                id = resultSet.getString(1);
                correspondant=resultSet.getString(2);

                nature=resultSet.getString(3);

                chargement=resultSet.getString(4);

                livraison=resultSet.getString(5);

                incoterm=resultSet.getString(6);

                colisage=resultSet.getString(7);

                String[]row ={id,correspondant,nature,chargement,livraison,incoterm,colisage};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void rafraichirTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Efface toutes les lignes existantes dans le modèle

        // Appel à une méthode pour récupérer les données de la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model1.setColumnIdentifiers(colName);
            String correspondant , incoterm ,chargement, nature , livraison,id,colisage;
            while (resultSet.next()){
                id = resultSet.getString(1);
                correspondant=resultSet.getString(2);

                nature=resultSet.getString(3);

                chargement=resultSet.getString(4);

                livraison=resultSet.getString(5);

                incoterm=resultSet.getString(6);

                colisage=resultSet.getString(7);

                String[]row ={id,correspondant,nature,chargement,livraison,incoterm,colisage};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void impression_demandeCorr() {

        int selectedRow = table1.getSelectedRow();
        //System.out.println(selectedRow);
        if(selectedRow!=-1){


        /*****************************************/
        Object objV0 = table1.getValueAt(selectedRow, 0);
        String id = objV0.toString();
        Object objV1 = table1.getValueAt(selectedRow, 1);
        String correspondant = objV1.toString();
        Object objV2 = table1.getValueAt(selectedRow, 2);

        String nature = objV2.toString();
        Object objV4 = table1.getValueAt(selectedRow, 3);
        String chargement = objV4.toString();
        Object objV5 = table1.getValueAt(selectedRow, 4);
        String livraison = objV5.toString();
        Object objV6 = table1.getValueAt(selectedRow, 5);
        String incoterm = objV6.toString();
        Object objV7 = table1.getValueAt(selectedRow, 6);
        String colisage = objV7.toString();
        //System.out.println(client+nature+p+livraison+inco+colisag);

        dm.setVisible(false);
        ImpDmCorr impCorr = new ImpDmCorr();
        impCorr.id_t.setText("000"+id);
        impCorr.corr.setText(correspondant);

        impCorr.charge.setText(chargement);
        impCorr.liv.setText(livraison);
        impCorr.nature.setText(nature);
        impCorr.incot.setText(incoterm);
        impCorr.col.setText(colisage);

        impCorr.setVisible(true);}
            else {JOptionPane.showMessageDialog(dm," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
        return ;}

    }




    private void demandeCorres() {
        String correspondant = (String) comboBox1.getSelectedItem();

        String chargement = charge.getText();
        String livraison = liv.getText();
        String nat_trns = nature.getText();
        String inco =incoterm.getText();
        String colisag = coll.getText();

        //Controle de saisie
        if ( correspondant.isEmpty()){
            JOptionPane.showMessageDialog(this," Il faut remplir tous les champs !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;
        }
        if (!correspondant.matches("^[a-zA-Z]+$") ) {
            JOptionPane.showMessageDialog(this, "Le champs correspondant doit contenir uniquement des lettres !", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "");

            // Vérifier si le client existe dans la table "Client"
            String checkClientQuery = "SELECT COUNT(*) FROM correspondant WHERE nom_corr = ?";
            PreparedStatement checkClientStmt = conn.prepareStatement(checkClientQuery);
            checkClientStmt.setString(1, correspondant);
            ResultSet clientResult = checkClientStmt.executeQuery();
            clientResult.next();
            int corrCount = clientResult.getInt(1);

            if (corrCount == 0) {
                JOptionPane.showMessageDialog(this, "Le correspondant n'existe pas dans la liste des correspondants.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Vérifier si une demandeCorres existe déjà avec les mêmes informations
            String checkDemandeQuery = "SELECT COUNT(*) FROM demandeCorrespondant WHERE correspondant = ?  AND ville_chargement = ? AND ville_livraison = ? AND nature_transport = ? AND incoterm = ? AND colisage = ?";
            PreparedStatement checkDemandeStmt = conn.prepareStatement(checkDemandeQuery);
            checkDemandeStmt.setString(1, correspondant);

            checkDemandeStmt.setString(2, chargement);
            checkDemandeStmt.setString(3, livraison);
            checkDemandeStmt.setString(4, nat_trns);
            checkDemandeStmt.setString(5, inco);
            checkDemandeStmt.setString(6, colisag);
            ResultSet demandeResult = checkDemandeStmt.executeQuery();
            demandeResult.next();
            int demandeCount = demandeResult.getInt(1);

            if (demandeCount > 0) {
                JOptionPane.showMessageDialog(this, "Cette demande existe déjà.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }}catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de l'enregistrement de la demande.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }


        this.DmdCor.ajout_dmd_corr(correspondant ,nat_trns,chargement,livraison,inco,colisag);
        JOptionPane.showMessageDialog(this,"Demande bien ajoutée","succées",JOptionPane.INFORMATION_MESSAGE);


        charge.setText("");
        liv.setText("");
        nature.setText("");
        incoterm.setText("");
        coll.setText("");


    }



            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    DmdCorrespondant test = new DmdCorrespondant();
                    test.setVisible(true);
                });

        }}





