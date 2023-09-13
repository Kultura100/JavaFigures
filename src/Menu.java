import javax.swing.*;

public class Menu extends JMenuBar {
    final JMenu plik = new JMenu("Plik");
    final JMenuItem wczytaj = new JMenuItem("Wczytaj z pliku");
    final JMenu obs = new JMenu("Obserwator");
    final JMenu pobs = new JMenu("Punkt Obserwowany");
    final JMenu Operacje = new JMenu("Operacje");
    final JMenu macierz = new JMenu("Macierz");
    final JMenuItem wysmacierz = new JMenuItem("Wyswietl Macierz");
    final JMenuItem usunmacierz = new JMenuItem("Resetuj Macierz");

    final JLabel obsxn = new JLabel("X:");
    final JTextField obsx = new JTextField(3);
    final JLabel obsyn = new JLabel("Y:");
    final JTextField obsy = new JTextField(3);
    final JLabel obszn = new JLabel("Z:");
    final JTextField obsz = new JTextField(3);

    final JLabel pobsxn = new JLabel("X:");
    final JTextField pobsx = new JTextField(3);
    final JLabel pobsyn = new JLabel("Y:");
    final JTextField pobsy = new JTextField(3);
    final JLabel pobszn = new JLabel("Z:");
    final JTextField pobsz = new JTextField(3);

    final JMenuItem rysuj = new JMenuItem("Model Druciany");
    final JMenuItem odleglosc = new JMenuItem("Wyswietl odleglosc");
    final JMenuItem wierzcholki = new JMenuItem("Wyswietl wierzcholki");
    public Menu(){
        JPanel roboczy = new JPanel();
        JPanel roboczy2 = new JPanel();
        roboczy.add(obsxn);
        roboczy.add(obsx);
        roboczy.add(obsyn);
        roboczy.add(obsy);
        roboczy.add(obszn);
        roboczy.add(obsz);
        //roboczy2
        roboczy2.add(pobsxn);
        roboczy2.add(pobsx);
        roboczy2.add(pobsyn);
        roboczy2.add(pobsy);
        roboczy2.add(pobszn);
        roboczy2.add(pobsz);
        plik.add(wczytaj);
        obs.add(roboczy);
        pobs.add(roboczy2);
        Operacje.add(rysuj);
        Operacje.add(odleglosc);
        Operacje.add(wierzcholki);
        macierz.add(wysmacierz);
        macierz.add(usunmacierz);
        add(plik);
        add(obs);
        add(pobs);
        add(Operacje);
        add(macierz);

    }
}
