/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.PrikazPoslasticaForma;
import forme.model.ModelTabelePoslastica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Poslastica;

/**
 *
 * @author katar
 */
public class PrikazPoslasticaKontroler {

    private final PrikazPoslasticaForma ppf;

    public PrikazPoslasticaKontroler(PrikazPoslasticaForma ppf) {
        this.ppf = ppf;
        ppf.setLocationRelativeTo(null);
        ppf.setTitle("Prikaz kolača");
        ppf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        ppf.setVisible(true);
    }

    private void pripremiFormu() {
        List<Poslastica> p = Komunikacija.getInstance().ucitajPoslastice();
        ModelTabelePoslastica mtk = new ModelTabelePoslastica(p);
        ppf.getjTablePrikazPoslastica().setModel(mtk);
    }

    private void addActionListener() {
        ppf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ppf.getjTablePrikazPoslastica().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ppf, "Nije odabrana ni jedna poslastica za brisanje!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                } else {
                    ModelTabelePoslastica mtp = (ModelTabelePoslastica) ppf.getjTablePrikazPoslastica().getModel();
                    Poslastica p = mtp.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiPoslastica(p);
                        JOptionPane.showMessageDialog(ppf, "Sistem je obrisao poslasticu.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ppf, "Sistem ne može da obriše poslasticu jer je već korišćena u porudžbini.", "Greska!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        ppf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ppf.getjTablePrikazPoslastica().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ppf, "Nije odabran ni jedan kolač za izmenu!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                } else {
                    ModelTabelePoslastica mtp = (ModelTabelePoslastica) ppf.getjTablePrikazPoslastica().getModel();
                    Poslastica p = mtp.getLista().get(red);
                    Cordinator.getInstance().dodajParam("poslastica", p);
                    Cordinator.getInstance().otvoriIzmeniPoslasticFormu();
                }
            }
        });

        ppf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = ppf.getjTextFieldPretragaNaziv().getText().trim();
                String opis = ppf.getjTextFieldPretragaOpis().getText().trim();

                ModelTabelePoslastica mtp = (ModelTabelePoslastica) ppf.getjTablePrikazPoslastica().getModel();
                mtp.pretrazi(naziv, opis);
            }
        });

        ppf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ppf.getjTextFieldPretragaNaziv().setText("");
                ppf.getjTextFieldPretragaOpis().setText("");
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }
}
