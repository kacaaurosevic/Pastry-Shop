/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import forme.PrikazSmenaForma;
import forme.model.ModelTabeleSmene;
import java.util.List;
import javax.swing.JFrame;
import komunikacija.Komunikacija;
import model.Smena;

/**
 *
 * @author katar
 */
public class PrikazSmenaKontroler {

    private final PrikazSmenaForma psf;

    public PrikazSmenaKontroler(PrikazSmenaForma psf) {
        this.psf = psf;
        psf.setLocationRelativeTo(null);
        psf.setTitle("Prikaz smena");
        psf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
    }

    public void otvoriFormu() {
        pripremiFormu();
        psf.setVisible(true);
    }

    private void pripremiFormu() {
        List<Smena> smene = Komunikacija.getInstance().ucitajSmene();
        ModelTabeleSmene mts = new ModelTabeleSmene(smene);
        psf.getjTableSmene().setModel(mts);
    }

    private void addActionListener() {
    }
}
