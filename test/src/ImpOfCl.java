import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class ImpOfCl extends JFrame {
    private JPanel ImpOfCl;
    private JButton imprimerButton;
    private JButton annulerButton;
    public JLabel t_client;
    public JLabel charge;
    public JLabel liv;
    public JLabel incoterm;
    public JLabel coll;
    public JLabel fret;
    public JLabel t_valid;
    public JLabel type;
    public JLabel obs;
    public JLabel id;
    public ImpOfCl ofC = this ;

    public ImpOfCl() {
        //super(parent);
        setTitle("Impression Offre Client");
        setContentPane(ImpOfCl);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ofC.setVisible(false);
                OffreCl offre = new OffreCl();
                offre.setVisible(true);

            }
        });
        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\HP\\Desktop\\OffreClient.pdf"));
                    document.open();
                    com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Font font1 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Image image = Image.getInstance("C:\\Users\\HP\\Desktop\\camion-de-livraison.png");
                    document.add(image);
                    document.add(new Paragraph("\t\t\t N° Offre : \t" + id.getText(), font1));
                    document.add(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Impression Offre Client \n\n\n",font));

                    document.add(new Paragraph("Client : \t" + t_client.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Chargement : \t" + charge.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Livraison : \t" + liv.getText()+"\n\n", font1));
                    document.add(new Paragraph("Type Chargement : \t" + type.getText()+"\n\n", font1));
                    document.add(new Paragraph("Incoterm : \t" + incoterm.getText()+"\n\n", font1));
                    document.add(new Paragraph("Colisage : \t" + coll.getText()+"\n\n\n\n", font1));
                    document.add(new Paragraph("Tarif : \t" + fret.getText()+"\n\n", font1));
                    document.add(new Paragraph("Validité : \t" + t_valid.getText()+"\n\n", font1));
                    document.add(new Paragraph("Observations : \t" + obs.getText()+"\n\n", font1));

                    document.close();
                    Desktop.getDesktop().open(new File("C:\\Users\\HP\\Desktop\\OffreClient.pdf"));

                   // JOptionPane.showMessageDialog(ImpOfCl.this, "Le document PDF a été généré avec succès !");
                } catch (DocumentException | FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ImpOfCl.this, "Une erreur s'est produite lors de la génération du PDF.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
