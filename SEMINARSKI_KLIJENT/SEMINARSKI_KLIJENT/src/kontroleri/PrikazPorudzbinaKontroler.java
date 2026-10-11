/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.PrikazPorudzbinaForma;
import forme.model.ModelTabelePorudzbina;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import komunikacija.Komunikacija;
import model.Kupac;
import model.Porudzbina;
import model.Poslasticar;

/**
 *
 * @author katar
 */
public class PrikazPorudzbinaKontroler {

    private PrikazPorudzbinaForma forma;
    private boolean isInitialLoad = true;

    public PrikazPorudzbinaKontroler(PrikazPorudzbinaForma forma) {
        this.forma = forma;
        otvoriFormu();
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        forma.setLocationRelativeTo(null);
        forma.setTitle("Prikaz porudzbina");
        forma.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        forma.setVisible(true);

    }

    private void pripremiFormu() {
        try {
            // Učitavam listu računa sa servera
            List<Porudzbina> listaP = Komunikacija.getInstance().ucitajListuPorudzbina();

            // Kreiram i postavljam model tabele
            ModelTabelePorudzbina model = new ModelTabelePorudzbina(listaP);
            forma.getjTablePorudzbine().setModel(model);

            TableRowSorter<ModelTabelePorudzbina> sorter = new TableRowSorter<>(model);
            forma.getjTablePorudzbine().setRowSorter(sorter);

            popuniComboBoxevePretraga();

            if (isInitialLoad) {
                JOptionPane.showMessageDialog(forma, "Sistem je učitao listu porudzbina.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                isInitialLoad = false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(forma, "Sistem ne može da učita listu porudzbina. Detalji: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListener() {
        dodajActionListenerNaDugmePrikaziStavke();
        dodajActionListenerNaDugmePretraziPorudzbinu();
        dodajActionListenerNaDugmeResetuj();
        dodajActionListenerNaDugmeIzmeni();
        dodajActionListenerNaTabelu();
    }

    private void dodajActionListenerNaDugmePrikaziStavke() {
        forma.addBtnPrikaziSRActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = forma.getjTablePorudzbine().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(forma, "Sistem ne može da nađe porudzbina.", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                } else {
                    int modelRow = forma.getjTablePorudzbine().convertRowIndexToModel(red);
                    ModelTabelePorudzbina model = (ModelTabelePorudzbina) forma.getjTablePorudzbine().getModel();
                    Porudzbina odabranaP = model.getListaPorudzbina().get(modelRow);
                    Cordinator.getInstance().otvoriPrikazStavkiRFormu(odabranaP);
                }
            }
        });
    }

    private void dodajActionListenerNaDugmePretraziPorudzbinu() {
        forma.addBtnPretraziRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pretraziPorudzbine();
            }
        });
    }

    private void dodajActionListenerNaDugmeResetuj() {
        forma.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resetovanje polja za pretragu na default vrednosti
                forma.getjTextFieldID().setText("");
                forma.getjComboBoxPretragaPoslasticar().setSelectedIndex(-1);
                forma.getjComboBoxPretragaKupac().setSelectedIndex(-1);
                forma.getjComboBoxStatus().setSelectedIndex(-1);

                // Osvežavanje tabele sa svim računima
                osveziTabelu();
            }
        });
    }

    private void dodajActionListenerNaDugmeIzmeni() {
        forma.addBtnIzmeniRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeniOdabranuPorudzbinu();
            }
        });
    }

    private void dodajActionListenerNaTabelu() {
        forma.getjTablePorudzbine().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = forma.getjTablePorudzbine().getSelectedRow();

                if (red == -1) {
                    JOptionPane.showMessageDialog(forma, "Sistem ne može da nađe porudzbinu.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (e.getClickCount() == 1) {
                    try {
                        int modelRow = forma.getjTablePorudzbine().convertRowIndexToModel(red);
                        ModelTabelePorudzbina model = (ModelTabelePorudzbina) forma.getjTablePorudzbine().getModel();
                        Porudzbina odabranaPorudzbina = model.getListaPorudzbina().get(modelRow);

                        // Prvi JOptionPane
                        JOptionPane.showMessageDialog(forma, "Sistem je našao porudzbina.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                        // Drugi JOptionPane sa detaljima o računu
                        String poruka = "ID računa: " + odabranaPorudzbina.getIdPorudzbina() + "\n"
                                + "Ukupna vrednost: " + odabranaPorudzbina.getUkupanIznos() + "\n"
                                + "Status obrade: " + (odabranaPorudzbina.isObradjena() ? "Obrađena" : "Neobrađena") + "\n"
                                + "Poslastičar: " + odabranaPorudzbina.getPoslasticar().toString() + "\n"
                                + "Kupac: " + odabranaPorudzbina.getKupac().toString();

                        JOptionPane.showMessageDialog(forma, poruka, "Podaci o porudzbini", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(forma, "Sistem ne može da nađe porudzbina.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void pretraziPorudzbine() {
        long idP = 0;
        if (!forma.getjTextFieldID().getText().isEmpty()) {
            try {
                idP = Integer.parseInt(forma.getjTextFieldID().getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(forma, "ID porudzbine mora biti broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Poslasticar poslasticar = (Poslasticar) forma.getjComboBoxPretragaPoslasticar().getSelectedItem();
        Kupac kupac = (Kupac) forma.getjComboBoxPretragaKupac().getSelectedItem();

        // Inicijalizujem listu van try-catch bloka da bude vidljiva svuda
        List<Porudzbina> filtriraneP = new ArrayList<>();
        String status = (String) forma.getjComboBoxStatus().getSelectedItem();

        try {
            if (status == null) {
                // SCENARIO 1: Nije izabran status
                // Prvi zahtev: pretraga za OBRADJENE racune
                Porudzbina racunZaPretraguTrue = new Porudzbina(idP, 0.0, true, poslasticar, kupac);
                List<Porudzbina> obradjeniRacuni = Komunikacija.getInstance().pretraziPorudzbine(racunZaPretraguTrue);
                filtriraneP.addAll(obradjeniRacuni);

                // Drugi zahtev: pretraga za NEOBRADJENE racune
                Porudzbina racunZaPretraguFalse = new Porudzbina(idP, 0.0, false, poslasticar, kupac);
                List<Porudzbina> neobradjeniRacuni = Komunikacija.getInstance().pretraziPorudzbine(racunZaPretraguFalse);
                filtriraneP.addAll(neobradjeniRacuni);

            } else {
                // SCENARIO 2: Izabran je status (Obrađeni ili Neobrađeni)
                boolean obradjen = status.equals("Obrađena");
                Porudzbina racunZaPretragu = new Porudzbina(idP, 0.0, obradjen, poslasticar, kupac);
                filtriraneP = Komunikacija.getInstance().pretraziPorudzbine(racunZaPretragu);
            }

            // Ažuriranje i osvežavanje tabele
            ModelTabelePorudzbina model = (ModelTabelePorudzbina) forma.getjTablePorudzbine().getModel();
            model.setListaPorudzbina(filtriraneP);
            model.fireTableDataChanged();

            // Prikaz poruke korisniku
            if (filtriraneP.isEmpty()) {
                JOptionPane.showMessageDialog(forma, "Sistem ne može da nađe porudzbinu po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(forma, "Sistem je našao porudzbine po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(forma, "Sistem ne može da nađe porudzbinu po zadatim kriterijumima.","Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void izmeniOdabranuPorudzbinu() {
        int red = forma.getjTablePorudzbine().getSelectedRow();

        if (red == -1) {
            JOptionPane.showMessageDialog(forma, "Morate izabrati porudzbinu za izmenu.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int modelRow = forma.getjTablePorudzbine().convertRowIndexToModel(red);
            ModelTabelePorudzbina model = (ModelTabelePorudzbina) forma.getjTablePorudzbine().getModel();

            Porudzbina odabranaPorudzbina = model.getListaPorudzbina().get(modelRow);

            if (odabranaPorudzbina.isObradjena()) {
                JOptionPane.showMessageDialog(
                        forma,
                        "Sistem ne može da izmeni obrađenu porudzbinu.",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Cordinator.getInstance().otvoriIzmeniPorudzbinaFormu(odabranaPorudzbina);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(forma, "Sistem ne može da učita odabranu porudzbinu za izmenu", "Greška", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void popuniComboBoxevePretraga() {
        try {
            List<Kupac> kupci = Komunikacija.getInstance().ucitajKupce();
            List<Poslasticar> poslasticari = Komunikacija.getInstance().ucitajPoslasticare();

            forma.getjComboBoxPretragaKupac().removeAllItems();
            forma.getjComboBoxPretragaKupac().addItem(null);
            for (Kupac kupac : kupci) {
                forma.getjComboBoxPretragaKupac().addItem(kupac);
            }
            forma.getjComboBoxPretragaKupac().setSelectedIndex(-1);

            forma.getjComboBoxPretragaPoslasticar().removeAllItems();
            forma.getjComboBoxPretragaPoslasticar().addItem(null);
            for (Poslasticar poslasticar : poslasticari) {
                forma.getjComboBoxPretragaPoslasticar().addItem(poslasticar);
            }
            forma.getjComboBoxPretragaPoslasticar().setSelectedIndex(-1);

            forma.getjComboBoxStatus().removeAllItems();
            forma.getjComboBoxStatus().addItem(null);
            forma.getjComboBoxStatus().addItem("Obrađena");
            forma.getjComboBoxStatus().addItem("Neobrađena");
            forma.getjComboBoxStatus().setSelectedIndex(-1);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(forma, "Greška prilikom popunjavanja polja za pretragu.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void osveziTabelu() {
        pripremiFormu();
    }
}
