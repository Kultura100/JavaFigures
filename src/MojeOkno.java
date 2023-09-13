import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import javax.swing.*;

public class MojeOkno extends JFrame implements ActionListener
{
    final GraphicsPanel panelDzialan = new GraphicsPanel();
    final Menu menu = new Menu();
    String sciezkaDoPlik;
    public MojeOkno() {
        super("Figury Przestrzenne");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        setJMenuBar(menu);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        add(panelDzialan);
        ustawNasluchZdarzen();
        dopasujSieDoZawartosci();
        setVisible(true);
        //domyslne
        menu.obsx.setText("1");
        menu.obsy.setText("1");
        menu.obsz.setText("1");
        menu.pobsx.setText("50");
        menu.pobsy.setText("50");
        menu.pobsz.setText("50");


    }
    private void ustawNasluchZdarzen()
    {
        menu.wczytaj.addActionListener(this);
        menu.odleglosc.addActionListener(this);
        menu.wysmacierz.addActionListener(this);
        menu.usunmacierz.addActionListener(this);
        menu.rysuj.addActionListener(this);
        menu.wierzcholki.addActionListener(this);
    }
    public void wczytajDaneZPliku()
    {
        JFileChooser otworz= new JFileChooser();
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("TXT", "txt");
        otworz.setFileFilter(filtr);
        if (otworz.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            sciezkaDoPlik= otworz.getSelectedFile().getPath();
            panelDzialan.wczytajTablice(sciezkaDoPlik);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object tmp = e.getSource();
        if(tmp == menu.wczytaj)
        {
            wczytajDaneZPliku();
        }
        else if(tmp == menu.odleglosc)
        {
            ustawObIPunkt();
            panelDzialan.dajDlugosc();
        }
        else if(tmp == menu.wysmacierz)
        {
            ustawObIPunkt();
            panelDzialan.liczMacierzPrzeksztalcen();
            panelDzialan.wyswietlMacierzPrzeksztalcen();
        }
        else if(tmp == menu.rysuj)
        {
            ustawObIPunkt();
            panelDzialan.rysujModelDruciany();
        }
        else if(tmp == menu.usunmacierz)
        {
            panelDzialan.resetujMacierzPrzeksztalcen();
            panelDzialan.wyswietlMacierzPrzeksztalcen();
        }
        else if(tmp == menu.wierzcholki)
        {
            panelDzialan.wypiszWierzcholki();
        }
    }

    public void ustawObIPunkt()
    {
        panelDzialan.obserwator.set(0, Double.parseDouble(menu.obsx.getText()));
        panelDzialan.obserwator.set(1, Double.parseDouble(menu.obsy.getText()));
        panelDzialan.obserwator.set(2, Double.parseDouble(menu.obsz.getText()));
        panelDzialan.punktObserwacji.set(0, Double.parseDouble(menu.pobsx.getText()));
        panelDzialan.punktObserwacji.set(1, Double.parseDouble(menu.pobsy.getText()));
        panelDzialan.punktObserwacji.set(2, Double.parseDouble(menu.pobsz.getText()));
        if(panelDzialan.obserwator.get(0) == 0)
            panelDzialan.obserwator.set(0,(1));
    }

    private void dopasujSieDoZawartosci()
    {
        pack();
        setLocationRelativeTo(null);
    }
}


