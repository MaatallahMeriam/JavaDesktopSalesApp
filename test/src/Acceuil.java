import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Acceuil extends JFrame {
    private JButton demandesCotationButton;
    private JButton offresButton;
    private JButton routingOrderButton;
    private JButton gestionCorrespondantButton;
    private JButton gestionFournisseurButton;
    private JButton gestionClientButton;
    private JPanel Acc;
    public Acceuil a = this;

    public Acceuil() {
        setTitle("Home");
        setContentPane(Acc);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - 1100) / 2;
        int y = (screenHeight - 600) / 2;

        // Définir les coordonnées de la fenêtre
        setLocation(x, y);
        System.out.println("BIENVENUE !");


        ClientRegister gestCl = new ClientRegister(this);
        CorrespRegister gestCorr = new CorrespRegister(this);
        FourRegister gestFour = new FourRegister(this);
        RoutingOr rouOr = new RoutingOr();
        DmdClient DmCl = new DmdClient();
        OffreCorr offCor = new OffreCorr();


        setVisible(true);

        gestionClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                gestCl.setVisible(true);
            }
        });
        gestionFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                gestFour.setVisible(true);

            }
        });
        gestionCorrespondantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                gestCorr.setVisible(true);
            }
        });
        demandesCotationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                DmCl.setVisible(true);
            }
        });
        offresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                offCor.setVisible(true);

            }
        });
        routingOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.setVisible(false);
                rouOr.setVisible(true);
            }
        });
    }
}
