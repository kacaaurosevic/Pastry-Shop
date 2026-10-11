/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import model.Poslasticar;

/**
 *
 * @author katar
 */
public class LoginKontroler {

    private final LoginForma lf;

    public LoginKontroler() {
        this.lf = null;
        addActionListeners();
    }

    public LoginKontroler(LoginForma lf) {
        this.lf = lf;
        lf.setTitle("Prijava poslastičara");
        lf.setLocationRelativeTo(null);
        addActionListeners();
    }

    private void addActionListeners() {
        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            private void prijava(ActionEvent e) {
                String ki = lf.getjTextFieldUsername().getText().trim();
                String pass = String.valueOf(lf.getjPasswordField().getPassword()).trim();

                Komunikacija.getInstance().konekcija();

                Poslasticar ulogovani = Komunikacija.getInstance().login(ki, pass);

                if (ulogovani == null) {
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra nisu ispravni.", "Greska!", JOptionPane.ERROR_MESSAGE);
                } else {
                    Cordinator.getInstance().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra su ispravni.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        Cordinator.getInstance().otvoriGlavnuFormu();
                    } catch (Exception exc) {
                        JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni.", "Greska!", JOptionPane.ERROR_MESSAGE);
                        Cordinator.getInstance().otvoriGlavnuFormu();
                    }
                    lf.dispose();
                }
            }
        });
    }

    public void otvoriFormu() {
        lf.setVisible(true);
    }
}
