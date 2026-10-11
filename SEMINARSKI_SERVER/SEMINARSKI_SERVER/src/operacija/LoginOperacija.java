/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija;

import java.util.List;
import model.Poslasticar;

/**
 *
 * @author katar
 */
public class LoginOperacija extends ApstraktnaGenerickaOperacija {

    Poslasticar poslasticar;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Poslasticar)) {
            throw new Exception("Korisničko ime i šifra nisu ispravni.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Poslasticar uneti = (Poslasticar) objekat;

        List<Poslasticar> sviPoslasticari = broker.getAll(new Poslasticar(), null);

        for (Poslasticar p : sviPoslasticari) {
            if (p.getKorisnickoIme().equals(uneti.getKorisnickoIme())
                    && p.getSifra().equals(uneti.getSifra())) {
                poslasticar = p;
                return;
            }
        }

        poslasticar = null;
    }

    public Poslasticar getPoslasticar() {
        return poslasticar;
    }

    public void setPoslasticar(Poslasticar poslasticar) {
        this.poslasticar = poslasticar;
    }

}
