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

public class ImpressRouting extends JDialog {
    private JPanel ImpRout;
    private JButton imprimerButton;
    private JButton annulerButton;
    public JLabel t_client;
    public JLabel t_ttel;
    public JLabel t_tmail;
    public JLabel t_tcntct;
    public JLabel t_trans;
    public JLabel t_four;
    public JLabel t_col;
    public JLabel t_ch;
    public JLabel t_liv;
    public JLabel t_inco;
    public JLabel t_fret;
    public JLabel t_valid;
    public JLabel t_obs;
    public JLabel t_id;
    public JLabel t_nature;

    public ImpressRouting(JFrame parent){
        super(parent);
        setTitle("Impression Routing");
        setContentPane(ImpRout);
        //setMinimumSize(new Dimension(900,600));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setSize(screenWidth, 600);


        int screenHeight = screenSize.height;
        int x = 0;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        //setLayout(new BorderLayout());
        //add(new JScrollPane(table1), BorderLayout.EAST);
        setModal(true);
        setLocationRelativeTo(parent);
        //setVisible(true);
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RoutingOr r = new RoutingOr();
                r.setVisible(true);

            }
        });
        imprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Document document = new Document(PageSize.A4);
                    PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\HP\\Desktop\\RoutingOrder.pdf"));
                    document.open();
                    com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Font font1 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, com.itextpdf.text.Font.NORMAL);
                    com.itextpdf.text.Image image = Image.getInstance("C:\\Users\\HP\\Desktop\\camion-de-livraison.png");
                    document.add(image);
                    document.add(new Paragraph("\t\t\t N° Routing : \t" + t_id.getText(), font1));
                    document.add(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Impression Routing Order \n\n\n",font));
                    document.add(new Paragraph("Nature de transport : "+t_nature.getText()+"\n\n", font));
                    document.add(new Paragraph("Client : \t" + t_client.getText()+"\n\n", font1));
                    document.add(new Paragraph("Tel : \t" + t_ttel.getText()+"\n\n", font1));
                    document.add(new Paragraph("Email : \t" + t_tmail.getText()+"\n\n", font1));
                    document.add(new Paragraph("Contact : \t" + t_tcntct.getText()+"\n\n", font1));
                    document.add(new Paragraph("\n\n\n"));
                    document.add(new Paragraph("Transporteur : \t" + t_trans.getText(), font1));
                    document.add(new Paragraph("Fournisseur : \t" + t_four.getText(), font1));
                    document.add(new Paragraph("Colisage : \t" + t_col.getText(), font1));
                    document.add(new Paragraph("Chargement : \t" + t_ch.getText(), font1));
                    document.add(new Paragraph("Livraison : \t" + t_liv.getText(), font1));
                    document.add(new Paragraph("Incoterm : \t" + t_inco.getText(), font1));
                    document.add(new Paragraph("\n\n\n"));
                    document.add(new Paragraph("Tarif : \t" + t_fret.getText(), font1));
                    document.add(new Paragraph("Validité : \t" + t_valid.getText(), font1));
                    document.add(new Paragraph("Observations : \t" + t_obs.getText(), font1));

                    document.close();
                    Desktop.getDesktop().open(new File("C:\\Users\\HP\\Desktop\\RoutingOrder.pdf"));

                   // JOptionPane.showMessageDialog(ImpressRouting.this, "Le document PDF a été généré avec succès !");
                } catch (DocumentException | FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ImpressRouting.this, "Une erreur s'est produite lors de la génération du PDF.",
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
