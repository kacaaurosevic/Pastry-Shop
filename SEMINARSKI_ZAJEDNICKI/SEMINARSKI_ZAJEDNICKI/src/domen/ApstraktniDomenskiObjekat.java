/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author katar
 */
public interface ApstraktniDomenskiObjekat extends Serializable {//serializable je kako bi mogle informacije da putuju mrezom

    //metode za CRUD operacije koje cu koristiti az genericki repozitorijum DbRepositoryFactory
    public String vratiNazivTabele();

    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;

    public String vratiKoloneZaUbacivanje();

    public String vratiVrednostiZaUbacivanje();

    public String vratiPrimarniKljuc();

    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;

    public String vratiVrednostiZaIzmenu();
}
