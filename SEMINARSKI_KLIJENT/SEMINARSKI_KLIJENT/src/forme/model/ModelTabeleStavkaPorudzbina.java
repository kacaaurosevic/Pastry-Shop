/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.StavkaPorudzbina;

/**
 *
 * @author katar
 */
public class ModelTabeleStavkaPorudzbina extends AbstractTableModel {

    private List<StavkaPorudzbina> listaStavki;
    private String[] kolone = {"R.B.", "Količina", "Cena", "Poslastica"};

    public ModelTabeleStavkaPorudzbina(List<StavkaPorudzbina> listaStavki) {
        this.listaStavki = listaStavki;
    }

    public StavkaPorudzbina getStavka(int rowIndex) {
        return listaStavki.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        if (listaStavki == null) {
            return 0;
        }
        return listaStavki.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaPorudzbina stavka = listaStavki.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stavka.getRb();
            case 1:
                return stavka.getKolicina();
            case 2:
                return stavka.getOsnovnaCena();
            case 3:
                return stavka.getPoslastica().getNaziv();
            default:
                return null;
        }
    }

    public void dodajStavku(StavkaPorudzbina stavka) {
        long trenutniRb = listaStavki.size() + 1;
        stavka.setRb(trenutniRb);
        listaStavki.add(stavka);
        fireTableDataChanged();
    }

    public void obrisiStavku(int red) {
        listaStavki.remove(red);
        fireTableDataChanged();
    }

    public List<StavkaPorudzbina> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List<StavkaPorudzbina> listaStavki) {
        this.listaStavki = listaStavki;
    }
}
