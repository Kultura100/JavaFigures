import java.util.Scanner;
import java.awt.*;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.BorderFactory;

import org.ejml.simple.*;;

public class GraphicsPanel extends JPanel
{
    BufferedImage plotno;
    SimpleMatrix macierzPrzeksztalcen = new SimpleMatrix(4,4,true,new double[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1});
    int[][] tablicaWierzcholkow;
    int[][] tablicaScian;
    final SimpleMatrix obserwator = new SimpleMatrix(1,4,true,new double[]{-1,-1,-1,1});
    final SimpleMatrix punktObserwacji = new SimpleMatrix(1,4,true,new double[]{0,0,0,1});
    SimpleMatrix odleglosc ;
    public GraphicsPanel(){
        super();
        ustawRozmiar(new Dimension(850, 850));
        wyczysc();
    }

    public void ustawRozmiar(Dimension r){
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
        setMaximumSize(r);
    }

    public void wyczysc(){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.black));
        rysujUkladWspolrzednych();
        repaint();
    }

    public int[][] kopiujTablice(int[][]tab){
        int [][] pom = new int[tab.length][tab[0].length];
        for(int i=0; i<tab.length; i++){
            for(int j=0; j<tab[i].length; j++){
                pom[i][j] = tab[i][j];
            }
        }
        return pom;
    }

    public void obliczPolozenieWierzcholkow(int [][] tablica){
        double d = Math.abs(odleglosc.get(2));
        for(int i=0; i<tablica.length; i++){
            tablica[i][0] = (int)((tablica[i][0]*d)/(tablica[i][2]+d));
            tablica[i][1] = (int)((tablica[i][1]*d)/(tablica[i][2]+d));
            System.out.print("("+tablica[i][0]+","+tablica[i][1]+")");
        }
    }

    public void dajDlugosc()
    {
        double d = -odleglosc.get(2);
        System.out.println("d= "+d);
    }


    public void rysujModelDruciany(){
        wyczysc();
        rysujUkladWspolrzednych();
        int [][] tabWierzcholki = kopiujTablice(tablicaWierzcholkow);
        zmienpolozenieWierzcholkow(tabWierzcholki);
        obliczPolozenieWierzcholkow(tabWierzcholki);
        for(int i=0; i<tabWierzcholki.length; i++) {
            Graphics2D g = (Graphics2D) plotno.getGraphics();
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(425 + tabWierzcholki[i][0], 425 + tabWierzcholki[i][1], 2, 2);
        }
        int wierzx1, wierzx2, wierzy1, wierzy2,pierwszy, drugi;
        for(int i=0; i<tablicaScian.length; i++){
            for(int j=0; j<tablicaScian[i].length; j++){
                if(j == tablicaScian[i].length-1)
                {
                    pierwszy = tablicaScian[i][j];
                    drugi = tablicaScian[i][0];
                }
                else
                {
                    pierwszy = tablicaScian[i][j];
                    drugi = tablicaScian[i][j+1];
                }
                wierzx1 = tabWierzcholki[pierwszy][0];
                wierzy1 = tabWierzcholki[pierwszy][1];
                wierzx2 = tabWierzcholki[drugi][0];
                wierzy2 = tabWierzcholki[drugi][1];
                Graphics2D g = (Graphics2D) plotno.getGraphics();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(425+wierzx1,425+wierzy1,425+wierzx2,425+wierzy2);
                //System.out.println("("+tablica[tablicaScian[i][j]][0]+", "+tablica[tablicaScian[i][j]][1]+") ("+tablica[tablicaScian[i][(j+1)%tablicaScian[i].length]][0]+", "+tablica[tablicaScian[i][(j+1)%tablicaScian[i].length]][1]);
            }
        }
        repaint();
    }

    public void zmienpolozenieWierzcholkow(int [][] tablica){
        for(int i=0; i<tablica.length; i++){
            SimpleMatrix tmp = new SimpleMatrix(1,4,true,new double[]{tablica[i][0],tablica[i][1],tablica[i][2],1});
            tmp = tmp.mult(macierzPrzeksztalcen);
            tablica[i][0] = (int)tmp.get(0);
            tablica[i][1] = (int)tmp.get(1);
            tablica[i][2] = (int)tmp.get(2);
        }
    }

    public void rysujUkladWspolrzednych(){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.BLACK);
        g.drawLine(plotno.getWidth()/2, 0, plotno.getWidth()/2, plotno.getHeight());
        g.drawLine(0, plotno.getHeight()/2, plotno.getWidth(), plotno.getHeight()/2);
    }

    public void liczMacierzPrzeksztalcen(){
        SimpleMatrix przesuniecie = new SimpleMatrix(4,4,true,new double[]{1,0,0,0,0,1,0,0,0,0,1,0,-punktObserwacji.get(0), -punktObserwacji.get(1), -punktObserwacji.get(2),1});
        double s = Math.sqrt(Math.pow(obserwator.get(0), 2)+Math.pow(obserwator.get(1), 2));
        double t = Math.sqrt(Math.pow(s, 2)+Math.pow(obserwator.get(2), 2));
        double u = Math.sqrt((Math.pow(obserwator.get(1), 2)*(Math.pow(obserwator.get(2), 2)))+(Math.pow(obserwator.get(0), 2)*(Math.pow(t, 2))));
        SimpleMatrix O = new SimpleMatrix(1,4,true, new double[]{obserwator.get(0),obserwator.get(1),obserwator.get(2),1});
        O = O.mult(przesuniecie);
        SimpleMatrix macierzOZ = new SimpleMatrix(4,4,true,new double[]{-O.get(2)/t,0,-s/t,0,0,1,0,0,s/t,0,-O.get(2)/t,0,0,0,0,1});
        O = O.mult(macierzOZ);
        odleglosc = O;

        SimpleMatrix m2 = new SimpleMatrix(4,4,true,new double[]{obserwator.get(0)/s,-obserwator.get(1)/s,0,0,obserwator.get(1)/s,obserwator.get(0)/s,0,0,0,0,s/s,0,0, 0, 0,s/s});
        SimpleMatrix m3 = new SimpleMatrix(4,4,true,new double[]{-obserwator.get(2)/t,0,-s/t,0,0,t/t,0,0,s/t,0,-obserwator.get(2)/t,0,0,0, 0,t/t});
        SimpleMatrix m4 = new SimpleMatrix(4,4,true,new double[]{(t*obserwator.get(0))/u,(-obserwator.get(1)*obserwator.get(2))/u,0,0,
                (obserwator.get(1)*obserwator.get(2))/u,(t*obserwator.get(0))/u,0,0,0,0,u/u,0,0,0,0,u/u});
        SimpleMatrix p = new SimpleMatrix(4,4,true,new double[]{1,0,0,0,0,1,0,0,0,0,1,0,-punktObserwacji.get(0), -punktObserwacji.get(1), -punktObserwacji.get(2),1});
        //macierzPrzeksztalcen=macierzPrzeksztalcen.mult(p);
        p = p.mult(m2);
        macierzPrzeksztalcen=p;
        //macierzPrzeksztalcen=macierzPrzeksztalcen.mult(m2);
        macierzPrzeksztalcen=macierzPrzeksztalcen.mult(m3);
        macierzPrzeksztalcen=macierzPrzeksztalcen.mult(m4);
    }

    public void wyswietlMacierzPrzeksztalcen(){
        System.out.println("Macierz przekształceń");
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                System.out.print(macierzPrzeksztalcen.get(i, j)+"\t");
            }
            System.out.print("\n");
        }
        System.out.println("");
    }

    public void resetujMacierzPrzeksztalcen(){
        macierzPrzeksztalcen = new SimpleMatrix(4,4,true,new double[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1});
        //wyswietlMacierzPrzeksztalcen();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(plotno, 0, 0, this);
    }
/*
    public void wczytajBryle(String sciezka)
    {
        //ustawRozmiar(new Dimension(400, 400));
        wyczysc();
        //obiekt reprezentujący plik graficzny o podanej ścieżce
        File plik = new File(sciezka);
        //próba odczytania pliku graficznego do bufora
        try {
            plotno = ImageIO.read(plik);
            //odczytanie rozmiaru obrazka
            Dimension rozmiar = new Dimension(plotno.getWidth(), plotno.getHeight());
            //Dimension rozmiar = new Dimension(400, 400);
            //ustalenie rozmiaru panelu zgodnego z rozmiarem obrazka
            if((int)rozmiar.getWidth()>800 || (int)rozmiar.getHeight()>800){
                setPreferredSize(new Dimension(800, 800));
                setMaximumSize(new Dimension(800, 800));
            }
            else{
                setPreferredSize(rozmiar);
                setMaximumSize(rozmiar);
            }
            //ustalenie obramowania
            setBorder(BorderFactory.createLineBorder(Color.black));
            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Blad odczytu pliku: " + sciezka);
            e.printStackTrace();
        }
    }
*/
    public void wczytajTablice(String nazwa){
        Scanner wejscie = null;
        try{
            wejscie = new Scanner(new File(nazwa));
        }catch(java.io.FileNotFoundException e){
            System.out.println("Nie znaleziono pliku!");
        }
        try{
            int w = 0,wr=0,s=0,sr=0;
            if(wejscie.hasNextLine())
                wejscie.nextLine();

            if(wejscie.hasNextInt())
                w = wejscie.nextInt();

            if(wejscie.hasNextInt())
                wr = wejscie.nextInt();

            tablicaWierzcholkow = new int [w][wr];
            for(int i=0; i<tablicaWierzcholkow.length; i++){
                for(int j=0; j<tablicaWierzcholkow[i].length; j++)
                    tablicaWierzcholkow[i][j] = wejscie.nextInt();
            }
            if(wejscie.hasNextInt())
                s = wejscie.nextInt();

            if(wejscie.hasNextInt())
                sr = wejscie.nextInt();

            tablicaScian = new int [s][sr];
            for(int i=0; i<tablicaScian.length; i++){
                for(int j=0; j<tablicaScian[i].length; j++)
                    tablicaScian[i][j] = wejscie.nextInt();
            }
            wejscie.close();
        }catch(java.lang.RuntimeException e){
            e.printStackTrace();
        }
    }

    public void wypiszWierzcholki(){
        for(int i=0;i<tablicaWierzcholkow.length; i++){
            for(int j=0; j<tablicaWierzcholkow[i].length; j++)
                System.out.print(tablicaWierzcholkow[i][j]+" ");
            System.out.println("");
        }
    }


}