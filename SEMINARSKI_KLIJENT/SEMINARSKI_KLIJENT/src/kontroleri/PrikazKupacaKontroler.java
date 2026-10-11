/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.*;
import java.util.List;
import model.Kupac;
import komunikacija.Komunikacija;
import forme.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Poslastica;
import model.Mesto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author katar
 */
public class PrikazKupacaKontroler {

    private final PrikazKupacaForma pkuf;

    public PrikazKupacaKontroler(PrikazKupacaForma pkuf) {
        this.pkuf = pkuf;
        pkuf.setLocationRelativeTo(null);
        pkuf.setTitle("Prikaz kupaca");
        pkuf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkuf.setVisible(true);
        ucitajMesta();
    }

    public void pripremiFormu() {
        try {
            List<Kupac> kupci = Komunikacija.getInstance().ucitajKupce();

            if (kupci == null) {
                kupci = new ArrayList<>();
            }

            ModelTabeleKupci mtku = new ModelTabeleKupci(kupci);
            pkuf.getjTablePrikazKupaca().setModel(mtku);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pkuf, "Sistem ne može da učita kupce.", "Greška!", JOptionPane.ERROR_MESSAGE);
            ModelTabeleKupci mtku = new ModelTabeleKupci(new ArrayList<>());
            pkuf.getjTablePrikazKupaca().setModel(mtku);
        }
    }

    private void addActionListener() {
        pkuf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkuf.getjTablePrikazKupaca().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkuf, "Sistem ne može da obriše kupca.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pkuf, "Sistem je nasao kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleKupci mtku = (ModelTabeleKupci) pkuf.getjTablePrikazKupaca().getModel();
                    Kupac ku = mtku.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiKupca(ku);
                        JOptionPane.showMessageDialog(pkuf, "Sistem je obrisao kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                        pkuf.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pkuf, "Sistem ne moze da obrise kupca.", "Greska!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        pkuf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkuf.getjTablePrikazKupaca().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkuf, "Sistem ne može da nađe kupca.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pkuf, "Sistem je nasao kupca.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleKupci mtku = (ModelTabeleKupci) pkuf.getjTablePrikazKupaca().getModel();
                    Kupac ku = mtku.getLista().get(red);
                    Cordinator.getInstance().dodajParam("kupac", ku);
                    Cordinator.getInstance().otvoriIzmeniKupacFormu();
                }
            }
        });

        pkuf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String imePrezime
                        = pkuf.getjTextFieldPretragaImePrezime().getText().trim();

                Mesto mesto
                        = (Mesto) pkuf.getjComboBoxMesto().getSelectedItem();

                if (imePrezime.isEmpty() && mesto == null) {
                    JOptionPane.showMessageDialog(
                            pkuf,
                            "Sistem ne može da nađe kupce po zadatim kriterijumima.",
                            "Upozorenje!",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                ModelTabeleKupci mtku
                        = (ModelTabeleKupci) pkuf.getjTablePrikazKupaca().getModel();

                mtku.pretrazi(imePrezime, mesto);

                if (mtku.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(
                            pkuf,
                            "Sistem ne moze da nadje kupce po zadatim kriterijumima.",
                            "Greska!",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            pkuf,
                            "Sistem je našao kupce po zadatim kriterijumima.",
                            "Uspeh!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        pkuf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pkuf.getjTextFieldPretragaImePrezime().setText("");
                pkuf.getjComboBoxMesto().setSelectedIndex(-1);
                pripremiFormu();
            }
        });
        pkuf.getjTablePrikazKupaca().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int red = pkuf.getjTablePrikazKupaca().getSelectedRow();

                if (red != -1 && e.getClickCount() == 1) {
                    JOptionPane.showMessageDialog(
                            pkuf,
                            "Sistem je našao kupca.",
                            "Uspeh!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

    private void ucitajMesta() {
        try {
            List<Mesto> svaMesta = Komunikacija.getInstance().ucitajMesta();
            pkuf.getjComboBoxMesto().addItem(null);
            for (Mesto m : svaMesta) {
                pkuf.getjComboBoxMesto().addItem(m);
            }
            pkuf.getjComboBoxMesto().setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pkuf, "Sistem ne može da učita listu mesta.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

}
