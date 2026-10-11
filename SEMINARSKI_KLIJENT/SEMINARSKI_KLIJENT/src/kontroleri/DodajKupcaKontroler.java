/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.DodajKupcaForma;
import forme.FormaMod;
import static forme.FormaMod.DODAJ;
import static forme.FormaMod.IZMENI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Kupac;
import model.Mesto;

/**
 *
 * @author HP
 */
public class DodajKupcaKontroler {

    private final DodajKupcaForma dkuf;

    public DodajKupcaKontroler(DodajKupcaForma dkuf) {
        this.dkuf = dkuf;
        dkuf.setTitle("Dodavanje/Izmena kupca");
        dkuf.setLocationRelativeTo(null);
        dkuf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
        ucitajMesta();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dkuf.setVisible(true);
    }

    private void addActionListener() {
        dkuf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String imePrezime = dkuf.getjTextFieldImePrezime().getText().trim();
                String kontakt = dkuf.getjTextFieldKontakt().getText().trim();
                Mesto izabranoMesto = (Mesto) dkuf.getjComboBoxMesto().getSelectedItem();

                if (imePrezime.isEmpty() || izabranoMesto == null || kontakt.isEmpty()) {
                    JOptionPane.showMessageDialog(dkuf, "Sistem ne moze da zapamti kupca.", "Greska!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Kupac noviKupac = new Kupac(-1L, imePrezime, kontakt, izabranoMesto);
                //JOptionPane.showMessageDialog(dkuf, "Sistem je kreirao kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);

                try {
                    Komunikacija.getInstance().dodajKupca(noviKupac);
                    JOptionPane.showMessageDialog(dkuf, "Sistem je zapamtio kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    dkuf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkuf, "Sistem ne moze da zapamti kupca.", "Greska!", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        dkuf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                long id = Integer.parseInt(dkuf.getjTextFieldIdKupca().getText().trim());
                String imePrezime = dkuf.getjTextFieldImePrezime().getText().trim();
                Mesto izabranoMesto = (Mesto) dkuf.getjComboBoxMesto().getSelectedItem();
                String kontakt = dkuf.getjTextFieldKontakt().getText().trim();

                Kupac noviKupac = new Kupac(id, imePrezime, kontakt, izabranoMesto);

                try {
                    Komunikacija.getInstance().izmeniKupca(noviKupac);
                    JOptionPane.showMessageDialog(dkuf, "Sistem je zapamtio kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    dkuf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkuf, "Sistem ne moze da zapamti kupca.", "Greska!", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                dkuf.getjTextFieldIdKupca().setEnabled(false);
                dkuf.getjButtonIzmeni().setVisible(false);
                dkuf.getjButtonDodajKupca().setVisible(true);
                dkuf.getjButtonDodajKupca().setEnabled(true);
                break;
            case IZMENI:
                dkuf.getjButtonIzmeni().setVisible(true);
                dkuf.getjButtonDodajKupca().setVisible(false);
                dkuf.getjButtonIzmeni().setEnabled(true);
                Kupac ku = (Kupac) Cordinator.getInstance().vratiParam("kupac");
                dkuf.getjTextFieldKontakt().setText(ku.getKontakt());
                dkuf.getjTextFieldIdKupca().setText(ku.getIdKupac() + "");
                dkuf.getjTextFieldImePrezime().setText(ku.getImePrezimeKupca());

                for (int i = 0; i < dkuf.getjComboBoxMesto().getItemCount(); i++) {
                    Mesto m = (Mesto) dkuf.getjComboBoxMesto().getItemAt(i);
                    // Poređenje mesta po id-u
                    if (m.getIdMesto() == ku.getMesto().getIdMesto()) {
                        dkuf.getjComboBoxMesto().setSelectedItem(m);
                        break; // Prekini petlju kada pronađeš podudaranje
                    }
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    public void ucitajMesta() {
        try {
            List<Mesto> mesta = Komunikacija.getInstance().ucitajMesta();
            dkuf.getjComboBoxMesto().removeAllItems();
            for (Mesto mesto : mesta) {
                dkuf.getjComboBoxMesto().addItem(mesto);
            }
            dkuf.getjComboBoxMesto().setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dkuf, "Greška prilikom učitavanja mesta: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
