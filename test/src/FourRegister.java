import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FourRegister extends JDialog {
    public Fournisseur fournisseur=new Fournisseur();
    private JTextField t_nom;
    private JTextField t_adr;
    private JTextField t_tel;
    private JTextField t_mail;
    private JTextField t_contact;
    private JTable table1;
    private JButton enregistrerButton;
    private JButton annulerButton;
    private JPanel FouPanel;
    private JButton supprimerFournisseurButton;
    private FourRegister fr = this ;

    public FourRegister(JFrame parent){
        super(parent);
        setTitle("Gestion Fournisseurs");
        setContentPane(FouPanel);
        setSize(900,600);
        setModal(true);
        setLocationRelativeTo(parent);
        //setVisible(true);

        load_Four();
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Acceuil ac = new Acceuil();
                ac.setVisible(true);
            }
        });
        enregistrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFour();
                rafraichirTable();

            }
        });
        supprimerFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                System.out.println(selectedRow);



                if (selectedRow != -1) {
                     // Vérifiez si une ligne est sélectionnée
                        int option = JOptionPane.showConfirmDialog(
                                fr,
                                "Êtes-vous sûr de vouloir supprimer ce fournisseur ?",
                                "Confirmation de suppression",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (option == JOptionPane.YES_OPTION) {// Vérifiez si une ligne est sélectionnée
                    Object objValue = table1.getValueAt(selectedRow, 0);
                    String strValue = objValue.toString();
                    int id = Integer.parseInt(strValue);
                    System.out.println(id);


                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
                        String query = "DELETE FROM fournisseur WHERE id_fr = ?";
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.setInt(1, id);
                        int rowsDeleted = statement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println(id+" supprimé avec succès !");}

                    }catch (SQLException ex) {
                        ex.printStackTrace();
                    }}
                }else {JOptionPane.showMessageDialog(fr," Selectionnez une ligne !","Essayez de nouveau ..",JOptionPane.ERROR_MESSAGE);
                    return ;}
                rafraichirTable();
            }});
    }


    public void RegisterFour() {
        String nom = t_nom.getText();
        String adresse = t_adr.getText();
        String tel = t_tel.getText();
        String mail = t_mail.getText();
        String cntct = t_contact.getText();

        //Controle de saisie
        if (nom.isEmpty() || adresse.isEmpty() || tel.isEmpty() || mail.isEmpty() || cntct.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il faut remplir tous les champs !", "Essayez de nouveau ..", JOptionPane.ERROR_MESSAGE);
            return;
        }



        // Validate t_mail as an email address
        if (!isValidEmail(mail)) {
            JOptionPane.showMessageDialog(this, "Le champ 'Mail' doit être une adresse email valide !", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate t_tel as an 8-digit number
        try {
            // Validate t_tel as an 8-digit number
            if (!tel.startsWith("+") ) {
                throw new NumberFormatException();
            }
            // Remove the "+" sign and parse the remaining digits as an integer

            // Continue with the rest of your code using the telInt variable
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le champ 'Tel' non  valide (doit être précédé d'un '+') !", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.fournisseur.ajout_Frn(nom, adresse, tel, mail, cntct);
        JOptionPane.showMessageDialog(this, "Fournisseur bien ajouté", "Succès", JOptionPane.INFORMATION_MESSAGE);
        t_nom.setText("");
        t_adr.setText("");
        t_tel.setText("");
        t_mail.setText("");
        t_contact.setText("");
    }

    // Helper method to validate email address
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private void rafraichirTable() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Efface toutes les lignes existantes dans le modèle

        // Appel à une méthode pour récupérer les données de la base de données
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM fournisseur";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model1 = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model1.setColumnIdentifiers(colName);
            String nom , adresse , num_tel , mail,id,contct;
            while (resultSet.next()){
                id = resultSet.getString(1);
                nom=resultSet.getString(2);
                //System.out.println(nom);
                adresse=resultSet.getString(3);
                // System.out.println(contct);
                num_tel=resultSet.getString(4);
                //System.out.println(contct);
                mail=resultSet.getString(5);
                //System.out.println(contct);

                contct=resultSet.getString(6);
                //System.out.println(contct);
                String[]row ={id,nom,adresse,num_tel,mail,contct};
                model1.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rafraîchir l'affichage de la JTable
        model.fireTableDataChanged();
    }


    public void load_Four(){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/commerciale","root","")) {
            String query = "SELECT * FROM fournisseur";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String [cols];
            for (int i=0 ; i<cols ; i++)
                colName[i]=rsmd.getColumnName(i+1);
            model.setColumnIdentifiers(colName);
            String nom , adresse , num_tel , mail ,id_four,contct;
            while (resultSet.next()){
                id_four = resultSet.getString(1);
                nom=resultSet.getString(2);
                adresse=resultSet.getString(3);
                // System.out.println(contct);
                num_tel=resultSet.getString(4);
                //System.out.println(contct);
                mail=resultSet.getString(5);
                //System.out.println(contct);
                contct=resultSet.getString(6);
                // System.out.println(contct);

                String[]row ={id_four,nom,adresse,num_tel,mail,contct};
                model.addRow(row);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        FourRegister test = new FourRegister(null);
    }
}
