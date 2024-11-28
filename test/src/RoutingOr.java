import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RoutingOr extends JFrame{
    private JPanel Routing;

    private JTextField ch;
    private JTextField liv;
    private JComboBox comboBox1;
    private JTextArea coll;
    private JTextField fret;
    private JButton enregistrerButton;
    private JButton annulerButton;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTable table1;
    private JButton imprimerButton1;
    private JLabel t_tel;
    private JLabel t_mail;
    private JLabel t_cntct;
    private JTextField t_natur;
    private JTextArea t_obs;
    private JTextField valid;
    private JButton supprimerButton;
    private JButton imprimerButton;
    public RoutingOr rout = this ;
    public RoutingOrder r = new RoutingOrder();

    public RoutingOr(){
       // super(parent);
        setTitle("Routing Order");
        setContentPane(Routing);
        //setMinimumSize(new Dimension(900,600));
        setSize(900,700);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - 900) / 2;
        int y = (screenHeight - 700) / 2;

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
        comboBox2.addItem("Sélectionnez une option");
        load_routing();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "")) {
            String query = "SELECT * FROM client";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                comboBox2.addItem(resultSet.getString("client"));
            }
            String query2 = "SELECT * FROM correspondant";
            Statement statement2 = con.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);
            while (resultSet2.next()) {
                comboBox3.addItem(resultSet2.getString("nom_corr"));
            }

            String query3 = "SELECT * FROM fournisseur";
            Statement statement3 = con.createStatement();
            ResultSet resultSet3 = statement3.executeQuery(query3);
            while (resultSet3.next()) {
                comboBox4.addItem(resultSet3.getString("nom_fr"));
            }

        } catch (SQLException ex) {
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
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String s = (String) comboBox2.getSelectedItem();
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "")) {

                    String sql = "SELECT * FROM client WHERE client = ?";
                    PreparedStatement state4 = con.prepareStatement(sql);
                    state4.setString(1, s);
                    ResultSet resultSet4 = state4.executeQuery();

                    if (resultSet4.next()) {
                        t_tel.setText(resultSet4.getString("num_tel"));
                        t_mail.setText(resultSet4.getString("adresse_mail"));
                        t_cntct.setText(resultSet4.getString("contact"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register_or ();
                rafraichirTable();
            }
        });

        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impression_order();

            }
        });
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                System.out.println(selectedRow);



                if (selectedRow != -1) { // Vérifiez si une ligne est sélectionnée
                    int option = JOptionPane.showConfirmDialog(
                            rout,
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
                            String query = "DELETE FROM routingorder WHERE id_r = ?";
                            PreparedStatement statement = con.prepareStatement(query);
                            statement.setInt(1, id);
                            int rowsDeleted = statement.executeUpdate();
                            if (rowsDeleted > 0) {
                                System.out.println(id+" supprimé avec succès !");
                            }

                        }catch (SQLException ex) {
                            ex.printStackTrace();
                        }}
                }else {JOptionPane.showMessageDialog(rout," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                    return ;}
                rafraichirTable();
            }});
    }

    public void load_routing(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
        String query = "SELECT * FROM routingorder";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();

        int cols = rsmd.getColumnCount();
        String[] colName = new String [cols];
        for (int i=0 ; i<cols ; i++)
            colName[i]=rsmd.getColumnName(i+1);
        model.setColumnIdentifiers(colName);
        String id_r,type_r,client,transporteur,fournisseur,tarif,delais_offre,observation,colisage,chargement,livraison,incoterm;
            while (resultSet.next()){
            id_r = resultSet.getString(1);
                type_r = resultSet.getString(2);

            //System.out.println(nom);
            client=resultSet.getString(3);
            // System.out.println(contct);
            transporteur=resultSet.getString(4);
            //System.out.println(contct);
            fournisseur=resultSet.getString(5);
            //System.out.println(contct);
            tarif=resultSet.getString(6);
            // System.out.println(contct);
            delais_offre=resultSet.getString(7);
                observation=resultSet.getString(8);
                colisage=resultSet.getString(9);
                chargement=resultSet.getString(10);
                livraison=resultSet.getString(11);
                incoterm=resultSet.getString(12);

            String[]row ={id_r,type_r,client,transporteur,fournisseur,tarif,delais_offre,observation,colisage,chargement,livraison,incoterm};
            model.addRow(row);


        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void impression_order() {
    int selectedRow = table1.getSelectedRow();
    //System.out.println(selectedRow);
    if (selectedRow != -1) {
        /*****************************************/
        Object objV0 = table1.getValueAt(selectedRow, 0);
        String id_r = objV0.toString();
        Object objV1 = table1.getValueAt(selectedRow, 1);
        String type_r = objV1.toString();
        Object objV2 = table1.getValueAt(selectedRow, 2);
        String client = objV2.toString();
        Object objV3 = table1.getValueAt(selectedRow, 3);
        String transporteur = objV3.toString();
        Object objV4 = table1.getValueAt(selectedRow, 4);
        String fournisseur = objV4.toString();
        Object objV5 = table1.getValueAt(selectedRow, 5);
        String tarif = objV5.toString();
        Object objV6 = table1.getValueAt(selectedRow, 6);
        String delais = objV6.toString();
        Object objV7 = table1.getValueAt(selectedRow, 7);
        String observation = objV7.toString();
        Object objV8 = table1.getValueAt(selectedRow, 8);
        String colisage = objV8.toString();
        Object objV9 = table1.getValueAt(selectedRow, 9);
        String chargement = objV9.toString();
        Object objV10 = table1.getValueAt(selectedRow, 10);
        String livraison = objV10.toString();
        Object objV11 = table1.getValueAt(selectedRow, 11);
        String incoterm = objV11.toString();
        rout.setVisible(false);
        ImpressRouting imp = new ImpressRouting(null);
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale", "root", "")) {

            String sql = "SELECT * FROM client WHERE client = ?";
            PreparedStatement state4 = con.prepareStatement(sql);
            state4.setString(1, client);
            ResultSet resultSet4 = state4.executeQuery();

            if (resultSet4.next()) {
                imp.t_ttel.setText(resultSet4.getString("num_tel"));
                imp.t_tmail.setText(resultSet4.getString("adresse_mail"));
                imp.t_tcntct.setText(resultSet4.getString("contact"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        imp.t_id.setText("000" + id_r);
        imp.t_client.setText(client);

        imp.t_nature.setText(type_r);

        imp.t_trans.setText(transporteur);
        imp.t_four.setText(fournisseur);
        imp.t_col.setText(colisage);
        imp.t_ch.setText(chargement);
        imp.t_liv.setText(livraison);
        imp.t_inco.setText(incoterm);
        imp.t_fret.setText(tarif);
        imp.t_valid.setText(delais);
        imp.t_obs.setText(observation);
        imp.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(rout, " Selectionnez une ligne !", "Essayez de nouveau ..", JOptionPane.ERROR_MESSAGE);
        return;
    }}




    public void register_or () {
        String client = (String) comboBox2.getSelectedItem();
        String nature = t_natur.getText();
        String transporteur = (String) comboBox3.getSelectedItem();
        String four = (String) comboBox4.getSelectedItem();
        String col = coll.getText();
        String charge = ch.getText();
        String livraison = liv.getText();
        String inco = (String) comboBox1.getSelectedItem();
        String tarif = fret.getText();
        String valide = valid.getText();
        String obs = t_obs.getText();


        if (nature.isEmpty() || col.isEmpty() || charge.isEmpty() || livraison.isEmpty() || tarif.isEmpty()|| valide.isEmpty()) {
            JOptionPane.showMessageDialog(this, " Il faut remplir tous les champs !", "Essayez de nouveau ..", JOptionPane.ERROR_MESSAGE);
            return;
        }


        float fretC;
        try {
            fretC = Float.parseFloat(tarif);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le champ 'Tarif' doit être un nombre décimal valide !", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            java.util.Date validiteDate = dateFormat.parse(valide);
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlValiditeDate = new java.sql.Date(validiteDate.getTime());
            // If the parsing is successful, ensure the date is not in the past
            if (sqlValiditeDate.before(new java.sql.Date(System.currentTimeMillis()))) {
                throw new ParseException("", 0);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Le champ 'Validité' doit être une date valide au format dd/MM/yyyy et ne peut pas être une date passée!", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.r.ajout_routing(nature,client,transporteur,four,fretC,valide,obs,col,charge,livraison,inco);
        JOptionPane.showMessageDialog(this, "Routing bien ajoutée", "succées", JOptionPane.INFORMATION_MESSAGE);

        t_natur.setText("");
        coll.setText("");
        ch.setText("");
        liv.setText("");
        fret.setText("");
        valid.setText("");
        t_obs.setText("");
        t_tel.setText("");
        t_mail.setText("");
        t_cntct.setText("");


    }

    private void rafraichirTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Efface toutes les lignes existantes dans le modèle

        // Appel à une méthode pour récupérer les données de la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM routingorder";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model1.setColumnIdentifiers(colName);
            String id_r,type_r,client,transporteur,fournisseur,tarif,delais_offre,observation,colisage,chargement,livraison,incoterm;
            while (resultSet.next()){
                id_r = resultSet.getString(1);
                type_r = resultSet.getString(2);

                //System.out.println(nom);
                client=resultSet.getString(3);
                // System.out.println(contct);
                transporteur=resultSet.getString(4);
                //System.out.println(contct);
                fournisseur=resultSet.getString(5);
                //System.out.println(contct);
                tarif=resultSet.getString(6);
                // System.out.println(contct);
                delais_offre=resultSet.getString(7);
                observation=resultSet.getString(8);
                colisage=resultSet.getString(9);
                chargement=resultSet.getString(10);
                livraison=resultSet.getString(11);
                incoterm=resultSet.getString(12);

                String[]row ={id_r,type_r,client,transporteur,fournisseur,tarif,delais_offre,observation,colisage,chargement,livraison,incoterm};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Rafraîchir l'affichage de la JTable
        model.fireTableDataChanged();
    }



    public static void main(String[] args) {
        RoutingOr test = new RoutingOr();
    }
}
