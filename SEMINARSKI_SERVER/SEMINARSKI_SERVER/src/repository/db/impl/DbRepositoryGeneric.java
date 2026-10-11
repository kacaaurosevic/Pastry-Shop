/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domen.ApstraktniDomenskiObjekat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.StavkaPorudzbina;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author katar
 */
public class DbRepositoryGeneric implements DbRepository<ApstraktniDomenskiObjekat> {//bice povezan sa kontorlerom

    @Override
    public List<ApstraktniDomenskiObjekat> getAll(ApstraktniDomenskiObjekat param, String uslov) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        //select * from poslastica
        String upit = "SELECT * FROM " + param.vratiNazivTabele();
        if (uslov != null) {
            upit += uslov;//uslov je isto sto i WHERE u sql-u 
        }
        System.out.println(upit);

        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();//otvara se konekcija i pravi objekat koji moze da izvrsi upit
        ResultSet rs = st.executeQuery(upit);
        lista = param.vratiListu(rs);

        rs.close();
        st.close();
        return lista;
    }

    @Override
    public void add(ApstraktniDomenskiObjekat param) throws Exception {
        //insert into poslastica(naziv, opis) VALUES ('...', '...')
        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaUbacivanje() + ") VALUES (" + param.vratiVrednostiZaUbacivanje() + ") ";
        System.out.println(upit);
        try {
            Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
            st.executeUpdate(upit);
            st.close();
            commit(); // čuvam promene
        } catch (Exception e) {
            rollback(); // ako nešto pođe po zlu, vratim na staro
            throw e; // prosleđujem grešku dalje ka Controlleru
        }

    }

    @Override
    public void edit(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " + param.vratiVrednostiZaIzmenu() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        try {
            Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
            st.executeUpdate(upit);
            st.close();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public void delete(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        try {
            Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
            st.executeUpdate(upit);
            st.close();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
    }

    @Override
    public List<ApstraktniDomenskiObjekat> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int addReturnKey(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.vratiNazivTabele() + " ("
                + param.vratiKoloneZaUbacivanje() + ") VALUES ("
                + param.vratiVrednostiZaUbacivanje() + ")";
        System.out.println(upit);

        Connection connection = DbConnectionFactory.getInstance().getConnection(); // uzmi ISTI connection
        try ( PreparedStatement ps = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Neuspesno dodavanje objekta i dobijanje kljuca. Greska: " + e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public void deleteWhere(ApstraktniDomenskiObjekat param, String whereClause) throws Exception {
        String upit = "DELETE FROM " + param.vratiNazivTabele() + " " + whereClause;
        System.out.println(upit);

        try ( Statement st = DbConnectionFactory.getInstance().getConnection().createStatement()) {
            st.executeUpdate(upit);
        } catch (SQLException e) {
            throw new Exception("Neuspešno brisanje objekata. Greška: " + e.getMessage(), e);
        }
    }
}
