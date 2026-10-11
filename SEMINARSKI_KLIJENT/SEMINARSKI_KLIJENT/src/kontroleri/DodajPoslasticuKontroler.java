/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.DodajPoslasticaForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Poslastica;

/**
 *
 * @author katar
 */
public class DodajPoslasticuKontroler {

    private final DodajPoslasticaForma dpf;

    public DodajPoslasticuKontroler(DodajPoslasticaForma dpf) {
        this.dpf = dpf;
        dpf.setTitle("Dodavanje/Izmena poslastice");
        dpf.setLocationRelativeTo(null);
        dpf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dpf.getjTextAreaNoviOpis().setLineWrap(true);
        dpf.getjTextAreaNoviOpis().setWrapStyleWord(true);
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        dpf.setVisible(true);
    }

    private void addActionListener() {
        dpf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String noviNaziv = dpf.getjTextFieldNoviNaziv().getText().trim();
                String noviOpis = dpf.getjTextAreaNoviOpis().getText();
                int cena;

                try {
                    cena = Integer.parseInt(dpf.getjTextFieldCena().getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dpf, "Cena mora biti broj.", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Poslastica novaP = new Poslastica(-1L, noviNaziv, noviOpis, cena);

                try {
                    Komunikacija.getInstance().dodajPoslastica(novaP);
                    JOptionPane.showMessageDialog(dpf, "Sistem je zapamtio poslasticu.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    dpf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dpf, "Sistem ne moze da zapamti poslasticu.", "Greška!", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        dpf.izmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                long sifra = Integer.parseInt(dpf.getjTextFieldSifra().getText());
                String noviNaziv = dpf.getjTextFieldNoviNaziv().getText().trim();
                String noviOpis = dpf.getjTextAreaNoviOpis().getText();
                int novaCena;

                try {
                    novaCena = Integer.parseInt(dpf.getjTextFieldCena().getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dpf, "Cena mora biti broj.", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Poslastica novaP = new Poslastica(sifra, noviNaziv, noviOpis, novaCena);

                try {
                    Komunikacija.getInstance().izmeniPoslastica(novaP);
                    JOptionPane.showMessageDialog(dpf, "Sistem je zapamtio poslasticu.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    dpf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dpf, "Sistem ne moze da zapamti poslasticu.", "Greška!", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                dpf.getjTextFieldSifra().setEnabled(false);
                dpf.getjButtonIzmeni().setVisible(false);
                dpf.getjButtonDodaj().setVisible(true);
                dpf.getjButtonDodaj().setEnabled(true);
                break;
            case IZMENI:
                dpf.getjButtonIzmeni().setVisible(true);
                dpf.getjButtonDodaj().setVisible(false);
                dpf.getjButtonIzmeni().setEnabled(true);

                Poslastica p = (Poslastica) Cordinator.getInstance().vratiParam("poslastica");
                dpf.getjTextFieldSifra().setText(p.getIdPoslastica() + "");
                dpf.getjTextFieldNoviNaziv().setText(p.getNaziv());
                dpf.getjTextAreaNoviOpis().setText(p.getOpis());
                dpf.getjTextFieldCena().setText(String.valueOf(p.getCenaP()));

                break;
            default:
                throw new AssertionError();
        }
    }
}
