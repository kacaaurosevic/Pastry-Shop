/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.porudzbina;

import java.util.ArrayList;
import java.util.List;
import model.Porudzbina;
import model.StavkaPorudzbina;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class IzmeniPorudzbinuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Porudzbina)) {
            throw new Exception("Sistem ne može da zapamti porudzbinu.");
        }
        Porudzbina porudzbina = (Porudzbina) objekat;
        if (porudzbina.getKupac() == null || porudzbina.getPoslasticar() == null) {
            throw new Exception("Morate odabrati kupca i poslastičara za izmenu porudzbine.");
        }
        if (porudzbina.getStavke() == null || porudzbina.getStavke().isEmpty()) {
            throw new Exception("Račun mora da sadrži barem jednu stavku.");
        }
    }

    //rekla  mi je asistentkinja da ne mogu ovo ovako da radim, izmena porudzbine, 
    //to je najtezi slucaj jer moze da se neka nova stavka doda, neka postojaca stavka da se promeni ili 
    //da se obrise, ovim delete-om ja sam obrisala sve stavke i onda sam insertovala sve ponovo, 
    //to realno nije promena vec brisanje pa kreiranje, e sad nezgodno je jer mi sad moramo da 
    //znamo sta se desilo sa tom stavkom, da li je izmenjena dodata ili obrisana, e sad, 
    //moglo bi da se resi tako sto lista koja je stigla sa klijentske strane (porudzbina.getStavke) 
    //to je lista sa kojom se klijent igrao na klijentskoj strani i to su nove stavke i sad 
    //mozemo da napravimo da se ucitaju sve stare stavke iz baze i onda posto u bazi imamo 
    //trenutne stavke porudzbine, i sada te stare stavke uporedimo sa novim tj te dve liste uporedimo 
    //i ako se neka stavka npr nalazi u novoj listi a ne nalazi se u staroj tj u bazi i nad njom pozivamo 
    //add iz dbbrokera, a ako se stavka ne nalazi u toj novoj listi a nalazila se u bazi pa onda nad tom stavkom 
    //koristimo delete jer ju je neko obrisao, i ako se nalazi u obe liste onda nad njom pozivamo edit, 
    //za poredjenje koristimo redne brojeve npr i nikako ne smemo da update-ujemo redne brojeve jer redni br i update porudzbine su par koji odredjuje tu jednu stavku
    //ucitaj sve stare stavke iz baze, ovo ispod sa deleteWhere nije dobro rekla Dragica
    //broker.deleteWhere(new StavkaPorudzbina(), " WHERE porudzbina=" + porudzbina.getIdPorudzbina());
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {

        Porudzbina porudzbina = (Porudzbina) objekat;

        // 1. Izmeni glavnu porudzbinu
        broker.edit(porudzbina);

        // 2. Ucitaj stare stavke SAMO ove porudzbine
        String uslov
                = " JOIN poslastica ON stavkaporudzbina.poslastica = poslastica.idPoslastica"
                + " WHERE stavkaporudzbina.porudzbina = "
                + porudzbina.getIdPorudzbina();

        List<StavkaPorudzbina> stareStavke
                = (List<StavkaPorudzbina>) (List<?>) broker.getAll(new StavkaPorudzbina(), uslov);

        // vratiListu ih kreira bez reference na porudzbinu
        for (StavkaPorudzbina stara : stareStavke) {
            stara.setPorudzbina(porudzbina);
        }

        List<StavkaPorudzbina> noveStavke = porudzbina.getStavke();

        // 4. Ako se nova stavka nalazi u starim stavkama -> edit
        // Ako se ne nalazi -> add
        for (StavkaPorudzbina nova : noveStavke) {
            nova.setPorudzbina(porudzbina);

            boolean postojiUBazi = false;

            for (StavkaPorudzbina stara : stareStavke) {
                if (nova.getRb().equals(stara.getRb())) {
                    postojiUBazi = true;
                    broker.edit(nova);
                    break;
                }
            }

            if (!postojiUBazi) {
                broker.add(nova);
            }
        }

        // 5. Ako se stara stavka ne nalazi u novim stavkama -> delete
        for (StavkaPorudzbina stara : stareStavke) {
            boolean postojiUNovojListi = false;

            for (StavkaPorudzbina nova : noveStavke) {
                if (stara.getRb().equals(nova.getRb())) {
                    postojiUNovojListi = true;
                    break;
                }
            }

            if (!postojiUNovojListi) {
                broker.delete(stara);
            }
        }
    }
}
