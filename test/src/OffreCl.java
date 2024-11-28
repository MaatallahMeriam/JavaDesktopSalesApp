import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OffreCl extends JFrame {
    private JPanel OffreCl;
    private JComboBox comboBox1;
    private JTextField t_valid;
    private JTextArea t_obs;
    private JTextField t_client;
    private JTextField tarif;
    private JButton enregistrerButton;
    private JButton imprimerButton;
    private JButton annulerButton;
    public JLabel charge;
    public JLabel liv;
    public JLabel t_type;
    public JLabel incoterm;
    public JLabel coll;
    private JButton supprimerButton;
    private JButton imprimerButton1;
    private JTable table1;
    public OffreCl offCl = this;
    public OffreClient ofre = new OffreClient();


    public OffreCl(){
        //super(parent);
        setTitle("Offre Client");
        setContentPane(OffreCl);
        //setMinimumSize(new Dimension(900,600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        load_offcr();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM client";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                comboBox1.addItem(resultSet.getString("client"));
            }}
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        //setVisible(true);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                OffreCorr ac = new OffreCorr();
                ac.setVisible(true);
            }
        });

        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enregis_Offre();
                rafraichirTable();
            }
        });
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supprimer();
                rafraichirTable();
            }
        });
        imprimerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimer_cl();
            }
        });
    }


    public void imprimer_cl(){
        int selectedRow = table1.getSelectedRow();


        if(selectedRow!=-1){
            /*****************************************/
            Object objV0 = table1.getValueAt(selectedRow, 0);
            String id = objV0.toString();
            Object objV1 = table1.getValueAt(selectedRow, 1);
            String client = objV1.toString();
            Object objV2 = table1.getValueAt(selectedRow, 2);
            String chargement= objV2.toString();
            Object objV3 = table1.getValueAt(selectedRow, 3);
            String livraison = objV3.toString();
            Object objV4 = table1.getValueAt(selectedRow, 4);
            String type = objV4.toString();
            Object objV5 = table1.getValueAt(selectedRow, 5);
            String inco = objV5.toString();
            Object objV6 = table1.getValueAt(selectedRow, 6);
            String colisag = objV6.toString();
            Object objV7 = table1.getValueAt(selectedRow, 7);
            String tarif = objV7.toString();
            Object objV8 = table1.getValueAt(selectedRow, 8);
            String validite = objV8.toString();
            Object objV9 = table1.getValueAt(selectedRow, 9);
            String observation = objV9.toString();
            //System.out.println(client+nature+p+livraison+inco+colisag);

            offCl.setVisible(false);

            ImpOfCl impcl= new ImpOfCl();
            impcl.id.setText("000"+id);
            impcl.t_client.setText(client);
            impcl.charge.setText(chargement);
            impcl.liv.setText(livraison);
            impcl.type.setText(type);
            impcl.incoterm.setText(inco);
            impcl.coll.setText(colisag);
            impcl.fret.setText(tarif);
            impcl.t_valid.setText(validite);
            impcl.obs.setText(observation);


            impcl.setVisible(true);}
        else {JOptionPane.showMessageDialog(offCl," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;}
    }










    public void Supprimer(){

        int selectedRow = table1.getSelectedRow();
        System.out.println(selectedRow);



        if (selectedRow != -1) {
             // Vérifiez si une ligne est sélectionnée
                int option = JOptionPane.showConfirmDialog(
                        offCl,
                        "Êtes-vous sûr de vouloir supprimer cette offre ?",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {// Vérifiez si une ligne est sélectionnée
            Object objValue = table1.getValueAt(selectedRow, 0);
            String strValue = objValue.toString();
            int id = Integer.parseInt(strValue);
            System.out.println(id);


            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
                String query = "DELETE FROM offreclient WHERE id_off_cl = ?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println(id+" supprimé avec succès !");
                }

            }catch (SQLException ex) {
                ex.printStackTrace();
            }}
        }else {JOptionPane.showMessageDialog(offCl," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;}
        rafraichirTable();

    }




    private void rafraichirTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Efface toutes les lignes existantes dans le modèle

        // Appel à une méthode pour récupérer les données de la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offreclient";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String id,client,chargement , livraison , type , incoterm , colisage,tarif,validite,obs;
            while (resultSet.next()){
                id = resultSet.getString(1);
                client=resultSet.getString(2);
                //System.out.println(nom);
                chargement=resultSet.getString(3);
                // System.out.println(contct);
                livraison=resultSet.getString(4);
                //System.out.println(contct);
                type=resultSet.getString(5);
                //System.out.println(contct);
                incoterm=resultSet.getString(6);
                // System.out.println(contct);
                colisage=resultSet.getString(7);
                tarif=resultSet.getString(8);
                validite=resultSet.getString(9);
                obs=resultSet.getString(10);
                //System.out.println(contct);
                String[]row ={id,client,chargement,livraison,type,incoterm,colisage,tarif,validite,obs};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rafraîchir l'affichage de la JTable
        model.fireTableDataChanged();
    }

    public void Enregis_Offre(){
        String client = (String) comboBox1.getSelectedItem();
        String chargement = charge.getText();
        String livraison = liv.getText();
        String type = t_type.getText();
        String inco = incoterm.getText();
        String colisag = coll.getText();
        String fre = tarif.getText();
        String validite = t_valid.getText();
        String observation = t_obs.getText();

        //Controle de saisie
        if ( client.isEmpty()|| fre.isEmpty()||validite.isEmpty()||observation.isEmpty()){
            JOptionPane.showMessageDialog(this," Il faut remplir tous les champs !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
            return ;
        }


        float fretC;
        try {
            fretC = Float.parseFloat(fre);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le champ 'Tarif' doit être un nombre décimal valide !", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            java.util.Date validiteDate = dateFormat.parse(validite);
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

        this.ofre.ajout_offreCl(client,chargement,livraison,type,inco,colisag,fretC,validite,observation);
        JOptionPane.showMessageDialog(this,"offre bien ajoutée","succées",JOptionPane.INFORMATION_MESSAGE);

        charge.setText("");
        liv.setText("");
        t_type.setText("");
        incoterm.setText("");
        coll.setText("");
        tarif.setText("");
        t_valid.setText("");
        t_obs.setText("");

    }













    public void load_offcr(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM offreclient";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String id,client,chargement , livraison , type , incoterm , colisage,tarif,validite,obs;
            while (resultSet.next()){
                id = resultSet.getString(1);
                client=resultSet.getString(2);
                //System.out.println(nom);
                chargement=resultSet.getString(3);
                // System.out.println(contct);
                livraison=resultSet.getString(4);
                //System.out.println(contct);
                type=resultSet.getString(5);
                //System.out.println(contct);
                incoterm=resultSet.getString(6);
                // System.out.println(contct);
                colisage=resultSet.getString(7);
                tarif=resultSet.getString(8);
                validite=resultSet.getString(9);
                obs=resultSet.getString(10);
                //System.out.println(contct);
                String[]row ={id,client,chargement,livraison,type,incoterm,colisage,tarif,validite,obs};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OffreCl test = new OffreCl();
    }
}
