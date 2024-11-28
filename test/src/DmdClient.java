import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DmdClient extends JFrame {
    private JComboBox comboBox1;
    private JTextField nat_trns;
    private JTextField prov;
    private JTextField dest;
    private JButton annulerButton;
    public JPanel DmdClient;
    private JTextArea colisage;
    private JTextField t_client;
    private JButton effacerButton;
    private JButton enregistrerButton1;
    private JTable table1;
    private JButton supprimerDemandeButton;
    private JButton imprimerButton;
    private JButton demandeCorrespondantButton;
    private JComboBox comboCl;
    public DmdCorrespondant correspondantWindow ;
    public DmdClient DmCl = this ;
    public DemandeCotation DmdCot = new DemandeCotation();

    public DmdClient(){

        setTitle("Demande Cotation Client");
      setContentPane(DmdClient);
        setSize(1200,600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - 1200) / 2;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);

        load_demand_c();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM client";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                comboCl.addItem(resultSet.getString("client"));
            }
            String query2 = "SELECT * FROM incoterm";
            Statement statement2 = con.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);
            while (resultSet2.next()){
                comboBox1.addItem(resultSet2.getString("nom_incoterm"));
            }}
        catch (SQLException ex) {
            ex.printStackTrace();
        }






        //setVisible(true);

        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Acceuil ac = new Acceuil();
                ac.setVisible(true);
            }
        });


        enregistrerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                demandeCli();
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
                            DmCl,
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
                        String query = "DELETE FROM demandecotation WHERE id_cot = ?";
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.setInt(1, id);
                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println(id+" supprimé avec succès !");
                        }

                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }}
                }else {JOptionPane.showMessageDialog(DmCl," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                    return ;}
                rafraichirTable();
            }});
        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impression_demandeCl();

            }
        });
        demandeCorrespondantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                demandeCorresp();
            }
        });
    }





    public void load_demand_c(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecotation";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String client , nature , incoterm , chargement , livraison,id,colisage;
            while (resultSet.next()){
                id = resultSet.getString(1);
                client=resultSet.getString(2);
                //System.out.println(nom);
                nature=resultSet.getString(3);
                // System.out.println(contct);
                incoterm=resultSet.getString(4);
                //System.out.println(contct);
                chargement=resultSet.getString(5);
                //System.out.println(contct);
                livraison=resultSet.getString(6);
                // System.out.println(contct);
                colisage=resultSet.getString(7);
                //System.out.println(contct);
                String[]row ={id,client,nature,incoterm,chargement,livraison,colisage};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void impression_demandeCl(){

        int selectedRow = table1.getSelectedRow();
        //System.out.println(selectedRow);


            if(selectedRow!=-1){
            /*****************************************/
        Object objV0 = table1.getValueAt(selectedRow, 0);
        String id = objV0.toString();
            Object objV1 = table1.getValueAt(selectedRow, 1);
            String client = objV1.toString();
            Object objV2 = table1.getValueAt(selectedRow, 2);
            String nature = objV2.toString();
            Object objV3 = table1.getValueAt(selectedRow, 3);
            String p = objV3.toString();
            Object objV4 = table1.getValueAt(selectedRow, 4);
            String livraison = objV4.toString();
            Object objV5 = table1.getValueAt(selectedRow, 5);
            String inco = objV5.toString();
            Object objV6 = table1.getValueAt(selectedRow, 6);
            String colisag = objV6.toString();
            //System.out.println(client+nature+p+livraison+inco+colisag);

        DmCl.setVisible(false);

        ImpressDmCl impCl= new ImpressDmCl(null);
        impCl.id_t.setText("000"+id);
        impCl.client.setText(client);
        impCl.nature.setText(nature);
        impCl.prove.setText(p);
        impCl.dest.setText(livraison);
        impCl.incoterm.setText(inco);
        impCl.col.setText(colisag);

        impCl.setVisible(true);}
            else {JOptionPane.showMessageDialog(DmCl," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                return ;}
    }



    public void demandeCorresp(){

        int selectedRow = table1.getSelectedRow();
        //System.out.println(selectedRow);
        if(selectedRow!=-1){



        /*****************************************/

       /* Object objV1 = table1.getValueAt(selectedRow, 1);
        String client = objV1.toString();*/
        Object objV2 = table1.getValueAt(selectedRow, 2);
        String nat_trans = objV2.toString();
        Object objV3 = table1.getValueAt(selectedRow, 3);
        String inco = objV3.toString();
        Object objV4 = table1.getValueAt(selectedRow, 4);
        String prove = objV4.toString();
        Object objV5 = table1.getValueAt(selectedRow, 5);
        String desti = objV5.toString();
        Object objV6 = table1.getValueAt(selectedRow, 6);
        String colisag = objV6.toString();



        correspondantWindow = new DmdCorrespondant();
        correspondantWindow.charge.setText(prove);
        correspondantWindow.liv.setText(desti);
        correspondantWindow.nature.setText(nat_trans);
        correspondantWindow.incoterm.setText(inco);
        correspondantWindow.coll.setText(colisag);

        DmCl.setVisible(false);
        correspondantWindow.setVisible(true);}
            else {JOptionPane.showMessageDialog(DmCl," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
        return ;}


    }





    private void demandeCli() {
        String client = (String) comboCl.getSelectedItem();
        String nat_trans = nat_trns.getText();
        String prove = prov.getText();
        String desti = dest.getText();
        String inco =(String) comboBox1.getSelectedItem(); ;
        String colisag = colisage.getText();

        //Controle de saisie
        if ( client.isEmpty()|| nat_trans.isEmpty()||prove.isEmpty()||desti.isEmpty()||colisag.isEmpty()){
            JOptionPane.showMessageDialog(this," Il faut remplir tous les champs !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "");

            // Vérifier si le client existe dans la table "Client"
            String checkClientQuery = "SELECT COUNT(*) FROM client WHERE client = ?";
            PreparedStatement checkClientStmt = conn.prepareStatement(checkClientQuery);
            checkClientStmt.setString(1, client);
            ResultSet clientResult = checkClientStmt.executeQuery();
            clientResult.next();
            int clientCount = clientResult.getInt(1);

            if (clientCount == 0) {
                JOptionPane.showMessageDialog(this, "Le client n'existe pas dans la liste des clients.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier si une demandeCotation existe déjà avec les mêmes informations
           }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de l'enregistrement de la demande.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }



        this.DmdCot.ajout_dmd_cot(client ,nat_trans,inco,prove,desti,colisag);
        JOptionPane.showMessageDialog(this,"Demande bien ajoutée","succées",JOptionPane.INFORMATION_MESSAGE);

        nat_trns.setText("");
        prov.setText("");
        dest.setText("");
        colisage.setText("");


    }



    private void rafraichirTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Efface toutes les lignes existantes dans le modèle

        // Appel à une méthode pour récupérer les données de la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM demandecotation";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model1.setColumnIdentifiers(colName);
            String client , nature , incoterm , chargement , livraison,id,colisage;
            while (resultSet.next()){
                id = resultSet.getString(1);
                client=resultSet.getString(2);
                //System.out.println(nom);
                nature=resultSet.getString(3);
                // System.out.println(contct);
                incoterm=resultSet.getString(4);
                //System.out.println(contct);
                chargement=resultSet.getString(5);
                //System.out.println(contct);
                livraison=resultSet.getString(6);
                // System.out.println(contct);
                colisage=resultSet.getString(7);
                //System.out.println(contct);
                String[]row ={id,client,nature,incoterm,chargement,livraison,colisage};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rafraîchir l'affichage de la JTable
        model.fireTableDataChanged();
    }
    public static void main(String[] args) {
        DmdClient test = new DmdClient();
    }

}
