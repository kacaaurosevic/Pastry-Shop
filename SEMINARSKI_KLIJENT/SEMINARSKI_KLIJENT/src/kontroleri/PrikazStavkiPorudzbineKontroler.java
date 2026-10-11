/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import forme.PrikazStavkiPorudzbineForma;
import forme.model.ModelTabeleStavkaPorudzbina;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Porudzbina;
import model.StavkaPorudzbina;

/**
 *
 * @author katar
 */
public class PrikazStavkiPorudzbineKontroler {

    private PrikazStavkiPorudzbineForma forma;
    private Porudzbina porudzbina;
    private boolean isInitialLoad = true;

    public PrikazStavkiPorudzbineKontroler(PrikazStavkiPorudzbineForma forma, Porudzbina porudzbina) {
        this.forma = forma;
        this.porudzbina = porudzbina;
        otvoriFormu();
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        forma.setLocationRelativeTo(null);
        forma.setTitle("Prikaz stavki odabranog racuna");
        forma.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        forma.setVisible(true);
        forma.getjLabelBroj().setText("Stavke racuna broj " + porudzbina.getIdPorudzbina());
    }

    private void pripremiFormu() {
        try {
            // Učitavam listu stavki računa sa servera
            List<StavkaPorudzbina> listaStavki = Komunikacija.getInstance().ucitajStavkePorudzbina(porudzbina);

            // Kreiram i postavljamo model tabele
            ModelTabeleStavkaPorudzbina model = new ModelTabeleStavkaPorudzbina(listaStavki);
            forma.getjTableStavkeRacuna().setModel(model);

            if (isInitialLoad) {
                JOptionPane.showMessageDialog(forma, "Sistem je učitao stavke računa.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                isInitialLoad = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(forma, "Sistem ne može da učita stavke računa.", "Greška", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
    }

    private void addActionListener() {

    }

    public void osveziFormu() {
        pripremiFormu();
    }
}
