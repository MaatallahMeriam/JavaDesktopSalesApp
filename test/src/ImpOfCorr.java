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

public class ImpOfCorr extends JFrame {
    private JPanel ImpOfCor;
    private JButton imprimerButton;
    private JButton annulerButton;
    public JLabel correspondant;
    public JLabel nature;
    public JLabel chargement;
    public JLabel livraison;
    public JLabel incoterm;
    public JLabel colisage;
    public JLabel fret;
    public JLabel id_of;
    public ImpOfCorr of = this ;
    public ImpOfCorr(){
        //super(parent);
        setTitle("Impression Offre Correspondant");
        setContentPane(ImpOfCor);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        setVisible(true);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                of.setVisible(false);
                OffreCorr OfC = new OffreCorr();
                OfC.setVisible(true);
            }
        });
        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\HP\\Desktop\\OffreCorrespondant.pdf"));
                    document.open();
                    com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Font font1 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Image image = Image.getInstance("C:\\Users\\HP\\Desktop\\camion-de-livraison.png");
                    document.add(image);
                    document.add(new Paragraph("\t\t\t N° Offre: \t" + id_of.getText(), font1));
                    document.add(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Impression Offre Correspondant \n\n\n",font));

                    document.add(new Paragraph("Correspondant : \t" + correspondant.getText()+"\n\n", font1));
                    document.add(new Paragraph("Nature de Transport : \t" + nature.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Chargement : \t" + chargement.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Destination : \t" + livraison.getText()+"\n\n", font1));
                    document.add(new Paragraph("Incoterm : \t" + incoterm.getText()+"\n\n", font1));
                    document.add(new Paragraph("Colisage : \t" + colisage.getText(), font1));
                    document.add(new Paragraph("Fret : \t" + fret.getText(), font1));
                    document.close();
                    Desktop.getDesktop().open(new File("C:\\Users\\HP\\Desktop\\OffreCorrespondant.pdf"));

                    //JOptionPane.showMessageDialog(ImpOfCorr.this, "Le document PDF a été généré avec succès !");
                } catch (DocumentException | FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ImpOfCorr.this, "Une erreur s'est produite lors de la génération du PDF.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        ImpOfCorr test = new ImpOfCorr();
    }}
