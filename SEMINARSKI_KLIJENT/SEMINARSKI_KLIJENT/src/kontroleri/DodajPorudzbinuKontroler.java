/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.DodajPorudzbinaForma;
import forme.FormaMod;
import forme.model.ModelTabeleStavkaPorudzbina;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Kupac;
import model.Porudzbina;
import model.Poslastica;
import model.Poslasticar;
import model.StavkaPorudzbina;

/**
 *
 * @author katar
 */
public class DodajPorudzbinuKontroler {

    private final DodajPorudzbinaForma dpf;
    private Porudzbina porudzbina;
    private ModelTabeleStavkaPorudzbina modelStavke;
    Poslasticar poslasticar = Cordinator.getInstance().getUlogovani();

    public DodajPorudzbinuKontroler(DodajPorudzbinaForma dpf, FormaMod mod, Porudzbina porudzbina) throws Exception {
        this.dpf = dpf;
        this.porudzbina = porudzbina;

        dpf.setTitle("Kreiranje/Izmena porudzbine");
        dpf.setLocationRelativeTo(null);
        dpf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        popuniComboBoxeve();
        dodajActionListenere();
        pripremiFormu(mod);
    }

    public void otvoriFormu() {
        dpf.setVisible(true);
    }

    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                dpf.getjButtonIzmeniRacun().setEnabled(false);
                dpf.getjButtonKreirajRacun().setVisible(true);
                dpf.getjTextFieldID().setEnabled(false);
                dpf.getjTextFieldCenaStavke().setEditable(false);
                dpf.getjTextFieldPoslasticar().setText(poslasticar.getImePrezime());
                dpf.getjTextFieldPoslasticar().setEditable(false);
                modelStavke = new ModelTabeleStavkaPorudzbina(new ArrayList<>());
                break;
            case IZMENI:
                dpf.getjButtonKreirajRacun().setEnabled(false);
                dpf.getjButtonIzmeniRacun().setVisible(true);
                dpf.getjTextFieldID().setEditable(false);
                dpf.getjTextFieldCenaStavke().setEditable(false);
                dpf.getjTextFieldPoslasticar().setText(porudzbina.getPoslasticar().getImePrezime());
                dpf.getjTextFieldPoslasticar().setEditable(false);
                popuniFormuZaIzmenu(porudzbina);
                break;
        }
        dpf.getjTableStavke().setModel(modelStavke);
    }

    private void popuniFormuZaIzmenu(Porudzbina porudzbina) {
        dpf.getjTextFieldID().setText(String.valueOf(porudzbina.getIdPorudzbina()));

        for (int i = 0; i < dpf.getjComboBoxKupci().getItemCount(); i++) {
            Kupac k = (Kupac) dpf.getjComboBoxKupci().getItemAt(i);
            if (k != null && k.getIdKupac().equals(porudzbina.getKupac().getIdKupac())) {
                dpf.getjComboBoxKupci().setSelectedItem(k);
                break;
            }
        }

        if (porudzbina.isObradjena()) {
            dpf.getjComboBoxObradjen().setSelectedItem("Da");
        } else {
            dpf.getjComboBoxObradjen().setSelectedItem("Ne");
        }

        try {
            List<StavkaPorudzbina> stavke = Komunikacija.getInstance().ucitajStavkePorudzbina(porudzbina);

            if (stavke == null) {
                stavke = new ArrayList<>();
            }

            modelStavke = new ModelTabeleStavkaPorudzbina(stavke);

        } catch (Exception ex) {
            modelStavke = new ModelTabeleStavkaPorudzbina(new ArrayList<>());

            JOptionPane.showMessageDialog(dpf, "Sistem ne može da učita stavke porudzbine.", "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void dodajActionListenere() {
        dpf.getjComboBoxPoslastice().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dpf.getjComboBoxPoslastice().getSelectedItem() instanceof Poslastica) {
                    Poslastica odabranaPoslastica
                            = (Poslastica) dpf.getjComboBoxPoslastice().getSelectedItem();

                    dpf.getjTextFieldCenaStavke().setText(
                            String.valueOf(odabranaPoslastica.getCenaP())
                    );
                }
            }
        });

        dpf.getjButtonDodajStavku().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajStavku();
            }
        });

        dpf.getjButtonObrisiStavku().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisiStavku();
            }
        });

        dpf.getjButtonKreirajRacun().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreirajRacun();
            }
        });

        dpf.getjButtonIzmeniRacun().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeniRacun();
            }
        });
    }

    private void azurirajUkupanIznos() {
        double ukupno = 0;
        for (StavkaPorudzbina stavka : modelStavke.getListaStavki()) {
            ukupno += stavka.getIznosSP();
        }
        dpf.getjTextFieldUkupanIznos().setText(String.valueOf(ukupno));
    }

    private void dodajStavku() {
        if (dpf.getjComboBoxPoslastice().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(dpf, "Morate izabrati poslasticu.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int kolicina = 0;
        try {
            kolicina = Integer.parseInt(dpf.getjTextFieldKolicinaStavke().getText().trim());
            if (kolicina <= 0) {
                JOptionPane.showMessageDialog(dpf, "Količina mora biti veća od nule.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dpf, "Količina mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Poslastica poslastica = (Poslastica) dpf.getjComboBoxPoslastice().getSelectedItem();

        boolean stavkaPostoji = false;
        // Provera da li stavka sa istim kolacem postoji
        for (StavkaPorudzbina stavka : modelStavke.getListaStavki()) {
            if (stavka.getPoslastica().equals(poslastica)) {
                // Ako postoji, povecavam kolicinu i cenu
                stavka.setKolicina(stavka.getKolicina() + kolicina);
                stavka.setIznosSP(stavka.getIznosSP() + (poslastica.getCenaP() * kolicina));
                stavkaPostoji = true;
                break;
            }
        }
        if (!stavkaPostoji) {
            // Ako ne postoji, dodajem novu stavku
            StavkaPorudzbina novaStavka = new StavkaPorudzbina();

            Long noviRb = 1L;

            for (StavkaPorudzbina s : modelStavke.getListaStavki()) {
                if (s.getRb() != null && s.getRb() >= noviRb) {
                    noviRb = s.getRb() + 1;
                }
            }

            novaStavka.setRb(noviRb);

            novaStavka.setPoslastica(poslastica);
            novaStavka.setKolicina(kolicina);
            novaStavka.setOsnovnaCena(poslastica.getCenaP());
            novaStavka.setIznosSP(poslastica.getCenaP() * kolicina);

            modelStavke.dodajStavku(novaStavka);
        }

        modelStavke.fireTableDataChanged();
        azurirajUkupanIznos();
        dpf.getjComboBoxPoslastice().setSelectedIndex(-1);
        dpf.getjTextFieldCenaStavke().setText("");
        dpf.getjTextFieldKolicinaStavke().setText("");
    }

    private void obrisiStavku() {
        int red = dpf.getjTableStavke().getSelectedRow();
        if (red == -1) {
            JOptionPane.showMessageDialog(dpf, "Niste izabrali ni jednu stavku.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modelStavke.obrisiStavku(red);
        double ukupno = 0;
        for (StavkaPorudzbina stavka : modelStavke.getListaStavki()) {
            ukupno += stavka.getIznosSP();
        }
        dpf.getjTextFieldUkupanIznos().setText(String.valueOf(ukupno));

        dpf.getjComboBoxPoslastice().setSelectedIndex(-1);
        dpf.getjTextFieldCenaStavke().setText("");
        dpf.getjTextFieldKolicinaStavke().setText("");
    }

    private void kreirajRacun() {
        try {
            Kupac kupac = (Kupac) dpf.getjComboBoxKupci().getSelectedItem();
            if (kupac == null) {
                JOptionPane.showMessageDialog(dpf, "Morate izabrati kupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<StavkaPorudzbina> stavkePorudzbine = modelStavke.getListaStavki();
            if (stavkePorudzbine.isEmpty()) {
                JOptionPane.showMessageDialog(dpf, "Račun mora imati barem jednu stavku.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean obradjen = false;
            if (dpf.getjComboBoxObradjen().getSelectedItem() != null && dpf.getjComboBoxObradjen().getSelectedItem().equals("Da")) {
                obradjen = true;
            }

            Porudzbina novaPorudzbina = new Porudzbina();
            novaPorudzbina.setKupac(kupac);
            novaPorudzbina.setPoslasticar(poslasticar);
            novaPorudzbina.setStavke(stavkePorudzbine);
            novaPorudzbina.setObradjena(obradjen);

            double ukupnaCena = 0.0;
            for (StavkaPorudzbina stavka : stavkePorudzbine) {
                ukupnaCena += stavka.getIznosSP();
            }
            novaPorudzbina.setUkupanIznos(ukupnaCena);

            Komunikacija.getInstance().kreirajPorudzbina(novaPorudzbina);

            JOptionPane.showMessageDialog(dpf, "Sistem je zapamtio porudzbinu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            prikaziPodatkeORacunuKreiranje(novaPorudzbina);

            dpf.dispose();
            Cordinator.getInstance().vratiSeNaGlavnuFormu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dpf, "Sistem ne može da zapamti porudzbina.", "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void izmeniRacun() {
        try {
            if (this.porudzbina.isObradjena()) {
                JOptionPane.showMessageDialog(dpf, "Sistem ne može da izmeni obrađenu porudzbinu.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kupac kupac = (Kupac) dpf.getjComboBoxKupci().getSelectedItem();

            if (kupac == null || poslasticar == null) {
                JOptionPane.showMessageDialog(dpf, "Morate izabrati kupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.porudzbina.setKupac(kupac);
            this.porudzbina.setPoslasticar(poslasticar);

            boolean obradjen = false;
            if (dpf.getjComboBoxObradjen().getSelectedItem() != null && dpf.getjComboBoxObradjen().getSelectedItem().equals("Da")) {
                obradjen = true;
            }
            this.porudzbina.setObradjena(obradjen);

            List<StavkaPorudzbina> stavkeRacuna = modelStavke.getListaStavki();
            double ukupnaCena = 0.0;
            for (StavkaPorudzbina stavka : stavkeRacuna) {
                ukupnaCena += stavka.getIznosSP();
            }
            this.porudzbina.setUkupanIznos(ukupnaCena);

            this.porudzbina.setStavke(stavkeRacuna);

            Komunikacija.getInstance().izmeniPorudzbina(this.porudzbina);
            JOptionPane.showMessageDialog(dpf, "Sistem je zapamtio porudzbinu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            prikaziPodatkeORacunuIzmena(this.porudzbina);
            dpf.dispose();
            Cordinator.getInstance().vratiSeNaGlavnuFormu();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dpf, "Sistem ne može da zapamti porudzbina.", "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void popuniComboBoxeve() throws Exception {
        List<Kupac> kupci = Komunikacija.getInstance().ucitajKupce();
        List<Poslastica> poslastice = Komunikacija.getInstance().ucitajPoslastice();
        dpf.getjComboBoxKupci().removeAllItems();
        dpf.getjComboBoxPoslastice().removeAllItems();

        for (Poslastica p : poslastice) {
            dpf.getjComboBoxPoslastice().addItem(p);
        }
        dpf.getjComboBoxPoslastice().setSelectedIndex(-1);

        for (Kupac k : kupci) {
            dpf.getjComboBoxKupci().addItem(k);
        }
        dpf.getjComboBoxKupci().setSelectedIndex(-1);

        dpf.getjComboBoxObradjen().setSelectedIndex(-1);
    }

    private void prikaziPodatkeORacunuIzmena(Porudzbina porudzbina) {
        String poruka
                = "ID računa: " + porudzbina.getIdPorudzbina() + "\n"
                + "Ukupna vrednost: " + porudzbina.getUkupanIznos() + "\n"
                + "Status obrade: " + (porudzbina.isObradjena() ? "Obrađena" : "Neobrađena") + "\n"
                + "Poslastičar: " + porudzbina.getPoslasticar().toString() + "\n"
                + "Kupac: " + porudzbina.getKupac().toString();

        JOptionPane.showMessageDialog(dpf, poruka, "Podaci o porudzbini", JOptionPane.INFORMATION_MESSAGE);
    }

    private void prikaziPodatkeORacunuKreiranje(Porudzbina porudzbina) {
        String poruka
                = "Ukupna vrednost: " + porudzbina.getUkupanIznos() + "\n"
                + "Status obrade: " + (porudzbina.isObradjena() ? "Obrađena" : "Neobrađena") + "\n"
                + "Poslastičar: " + porudzbina.getPoslasticar().toString() + "\n"
                + "Kupac: " + porudzbina.getKupac().toString();

        JOptionPane.showMessageDialog(dpf, poruka, "Podaci o porudzbini", JOptionPane.INFORMATION_MESSAGE);
    }
}
