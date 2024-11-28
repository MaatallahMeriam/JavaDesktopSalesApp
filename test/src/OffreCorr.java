import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OffreCorr extends JFrame {
    private JPanel OffCorr;
    private JTextField transp;
    private JTextField nature;
    private JTextField charge;
    private JTextField trans;
    private JButton enregistrerEtImprimerButton;
    private JButton imprimerButton;
    private JButton envoyerOffreClientButton;
    private JButton annulerButton;
    private JTextField tarif;
    private JComboBox comboBox1;
    private JTextArea col;
    private JTextField t_corr;
    private JTable table1;
    private JButton supprimerButton;
    private JButton imprimerButton1;
    private JButton offreClientButton;
    private JButton précédentButton;
    private JComboBox comboBox2;
    private JButton effacerButton;
    public OffreCorr OffCrr = this ;
    OffreCorrespondant offCor = new OffreCorrespondant();
    public OffreCl ofCl = new OffreCl();

    public OffreCorr(){
        //super(parent);
        setTitle("Offre Correspondant");
        setContentPane(OffCorr);
        setSize(900,600);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - 900) / 2;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);

        comboBox1.addItem("FCA");
        comboBox1.addItem("CIP");
        comboBox1.addItem("CPT");
        comboBox1.addItem("DAT");
        comboBox1.addItem("DAP");
        comboBox1.addItem("DTP");
        comboBox1.addItem("DPP");
        comboBox1.addItem("FOB");
        comboBox1.addItem("CNI");
        comboBox1.addItem("CIF");
        comboBox1.addItem("CFR");
        load_offcr();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM correspondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                comboBox2.addItem(resultSet.getString("nom_corr"));
            }}
        catch (SQLException ex) {
            ex.printStackTrace();
        }







        enregistrerEtImprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offreCorr();
                rafraichirTable();

            }
        });

        précédentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Acceuil ac = new Acceuil();
                ac.setVisible(true);

            }
        });
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                System.out.println(selectedRow);



                if (selectedRow != -1) { // Vérifiez si une ligne est sélectionnée
                    int option = JOptionPane.showConfirmDialog(
                            OffCrr,
                            "Êtes-vous sûr de vouloir supprimer cette offre ?",
                            "Confirmation de suppression",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) { // Vérifiez si une ligne est sélectionnée
                    Object objValue = table1.getValueAt(selectedRow, 0);
                    String strValue = objValue.toString();
                    int id = Integer.parseInt(strValue);
                    System.out.println(id);


                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
                        String query = "DELETE FROM offrecorrespondant WHERE id_off_crr = ?";
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.setInt(1, id);
                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println(id+" supprimé avec succès !");
                        }

                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }}
                }else {JOptionPane.showMessageDialog(OffCrr," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                    return ;}
                rafraichirTable();
            }});
        imprimerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impressionOfCorr();

            }
        });
        offreClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Envoi_Cl();
            }
        });
    }


    public void Envoi_Cl(){
        int selectedRow = table1.getSelectedRow();
        //System.out.println(selectedRow);
        if(selectedRow!=-1){



            /*****************************************/

            Object objV2 = table1.getValueAt(selectedRow, 3);
            String chargement = objV2.toString();


            Object objV3 = table1.getValueAt(selectedRow, 2);
            String type= objV3.toString();



            Object objV4 = table1.getValueAt(selectedRow, 5);
            String livraison = objV4.toString();


            Object objV5 = table1.getValueAt(selectedRow, 4);
            String inco = objV5.toString();


            Object objV6 = table1.getValueAt(selectedRow, 6);
            String colisag = objV6.toString();




            ofCl.charge.setText(chargement);
            ofCl.liv.setText(livraison);
            ofCl.t_type.setText(type);
            ofCl.incoterm.setText(inco);
            ofCl.coll.setText(colisag);

            OffCrr.setVisible(false);
            ofCl.setVisible(true);}
        else {JOptionPane.showMessageDialog(OffCrr," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;}


    }


    public void load_offcr(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offrecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String correspondant , nature , incoterm , chargement , livraison,id,colisage,fret;
            while (resultSet.next()){
                id = resultSet.getString(1);
                correspondant=resultSet.getString(2);

                // System.out.println(contct);
                nature=resultSet.getString(3);
                //System.out.println(contct);
                chargement=resultSet.getString(4);
                //System.out.println(contct);
                incoterm=resultSet.getString(5);
                // System.out.println(contct);
                livraison=resultSet.getString(6);
                colisage=resultSet.getString(7);
                fret=resultSet.getString(8);
                //System.out.println(contct);
                String[]row ={id,correspondant,nature,chargement,incoterm,livraison,colisage,fret};
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
            String query = "SELECT * FROM offrecorrespondant";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model1.setColumnIdentifiers(colName);
            String correspondant, nature , incoterm , chargement , livraison,id,colisage,fret;
            while (resultSet.next()){
                id = resultSet.getString(1);
                correspondant=resultSet.getString(2);
                //System.out.println(nom);

                // System.out.println(contct);
                nature=resultSet.getString(3);
                //System.out.println(contct);
                chargement=resultSet.getString(4);
                //System.out.println(contct);
                incoterm=resultSet.getString(5);
                // System.out.println(contct);
                livraison=resultSet.getString(6);
                colisage=resultSet.getString(7);
                fret=resultSet.getString(8);
                //System.out.println(contct);
                String[]row ={id,correspondant,nature,chargement,incoterm,livraison,colisage,fret};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rafraîchir l'affichage de la JTable
        model.fireTableDataChanged();
    }








    public void impressionOfCorr(){
        int selectedRow = table1.getSelectedRow();
        //System.out.println(selectedRow);


        if(selectedRow!=-1){
            /*****************************************/
            Object objV0 = table1.getValueAt(selectedRow, 0);
            String id = objV0.toString();
            Object objV1 = table1.getValueAt(selectedRow, 1);
            String correspondant = objV1.toString();

            Object objV3 = table1.getValueAt(selectedRow, 2);
            String nature = objV3.toString();
            Object objV4 = table1.getValueAt(selectedRow, 3);
            String chargement = objV4.toString();
            Object objV5 = table1.getValueAt(selectedRow, 4);
            String inco = objV5.toString();
            Object objV6 = table1.getValueAt(selectedRow, 5);
            String livraison = objV6.toString();
            Object objV7 = table1.getValueAt(selectedRow, 6);
            String colisag = objV7.toString();
            Object objV8 = table1.getValueAt(selectedRow, 7);
            String fret = objV8.toString();
            //System.out.println(client+nature+p+livraison+inco+colisag);

            OffCrr.setVisible(false);

            ImpOfCorr impOfCorr= new ImpOfCorr();
            impOfCorr.id_of.setText("000"+id);
            impOfCorr.correspondant.setText(correspondant);
            impOfCorr.nature.setText(nature);
            impOfCorr.fret.setText(fret);

            impOfCorr.chargement.setText(chargement);
            impOfCorr.livraison.setText(livraison);
            impOfCorr.colisage.setText(colisag);
            impOfCorr.incoterm.setText(inco);


            impOfCorr.setVisible(true);}
        else {JOptionPane.showMessageDialog(OffCrr," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;}
    }




    private void offreCorr() {

    String corresp = (String) comboBox2.getSelectedItem();
    String nat = nature.getText();
    String chargement = charge.getText();
    String transport = trans.getText();
    String inco =(String) comboBox1.getSelectedItem(); ;
    String colisag = col.getText();
    String f = tarif.getText();

    //Controle de saisie
        if ( corresp.isEmpty()|| nat.isEmpty()||transport.isEmpty()|| chargement.isEmpty()||f.isEmpty()||colisag.isEmpty()){
        JOptionPane.showMessageDialog(this," Il faut remplir tous les champs !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
        return ;
    }
        try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "");

        // Vérifier si le client existe dans la table "Client"
        String checkClientQuery = "SELECT COUNT(*) FROM demandecorrespondant WHERE correspondant = ?";
        PreparedStatement checkClientStmt = conn.prepareStatement(checkClientQuery);
        checkClientStmt.setString(1, corresp);
        ResultSet clientResult = checkClientStmt.executeQuery();
        clientResult.next();
        int corr = clientResult.getInt(1);

        if (corr == 0) {
            JOptionPane.showMessageDialog(this, "Le correspondant n'existe pas dans la liste des demandes cotations.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
            float fret = Float.parseFloat(f);

        // Vérifier si une demandeCotation existe déjà avec les mêmes informations
        String checkDemandeQuery = "SELECT COUNT(*) FROM offrecorrespondant WHERE correspondant = ? AND nature_transport = ? AND ville_chargement = ? AND ville_livraison = ? AND Colisage = ? AND incoterm = ? AND fret = ?";
        PreparedStatement checkDemandeStmt = conn.prepareStatement(checkDemandeQuery);
        checkDemandeStmt.setString(1, corresp);

        checkDemandeStmt.setString(2, nat);
        checkDemandeStmt.setString(3, chargement);
        checkDemandeStmt.setString(4, transport);
        checkDemandeStmt.setString(5, colisag);
        checkDemandeStmt.setString(6, inco);

        checkDemandeStmt.setFloat(7, fret);

        ResultSet demandeResult = checkDemandeStmt.executeQuery();
        demandeResult.next();
        int offreCount = demandeResult.getInt(1);

        if (offreCount > 0) {
            JOptionPane.showMessageDialog(this, "Cette offre existe déjà en liste des offres correspondants ! .", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }}catch (
    SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de l'enregistrement de la demande.",
                "Erreur", JOptionPane.ERROR_MESSAGE);
    }


        float fretC = Float.parseFloat(f);

        this.offCor.ajout_offre_corr(corresp,nat,chargement,inco,transport,colisag,fretC);
        JOptionPane.showMessageDialog(this,"offre bien ajoutée","succées",JOptionPane.INFORMATION_MESSAGE);


        nature.setText("");
        charge.setText("");
        trans.setText("");
        col.setText("");
        tarif.setText("");

}


    public static void main(String[] args) {
        OffreCorr test = new OffreCorr();
    }
}
