import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;


public class ImpressDmCl extends JDialog {
    private JPanel ImpCl;
    private JButton annulerButton;
    private JButton imprimerButton;
    public JLabel client;
    public JLabel nature;
    public JLabel prove;
    public JLabel dest;
    public JLabel incoterm;
    public JLabel col;
    public JLabel id_t;
    public ImpressDmCl imp =this;


    public ImpressDmCl(JFrame parent){
        super(parent);
        setTitle("Impression Demande Client");
        setContentPane(ImpCl);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        setModal(true);
        setLocationRelativeTo(parent);
        //setVisible(true);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imp.setVisible(false);
                DmdClient dmC = new DmdClient();
                dmC.setVisible(true);
            }
        });
        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\HP\\Desktop\\demandeClient.pdf"));
                    document.open();
                    Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
                    Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                    Image image = Image.getInstance("C:\\Users\\HP\\Desktop\\camion-de-livraison.png");
                    document.add(image);
                    document.add(new Paragraph("\t\t\t N° Demande : \t" + id_t.getText(), font1));
                    document.add(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Impression Demande Client \n\n\n",font));

                    document.add(new Paragraph("Client : \t" + client.getText()+"\n\n", font1));
                    document.add(new Paragraph("Nature de Transport : \t" + nature.getText()+"\n\n", font1));
                    document.add(new Paragraph("Incoterm : \t" + prove.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Chargement : \t" + dest.getText()+"\n\n", font1));
                    document.add(new Paragraph("Ville de Livraison : \t" + incoterm.getText()+"\n\n", font1));
                    document.add(new Paragraph("Colisage : \t" + col.getText(), font1));
                    document.close();
                    Desktop.getDesktop().open(new File("C:\\Users\\HP\\Desktop\\demandeClient.pdf"));

                   // JOptionPane.showMessageDialog(ImpressDmCl.this, "Le document PDF a été généré avec succès !");
                } catch (DocumentException | FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ImpressDmCl.this, "Une erreur s'est produite lors de la génération du PDF.",
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
        ImpressDmCl imp = new ImpressDmCl(null);
    }


}
