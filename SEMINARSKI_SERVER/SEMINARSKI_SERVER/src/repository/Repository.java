/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.util.List;
import model.StavkaPorudzbina;

/**
 *
 * @author katar
 */
public interface Repository<T> {//genericki interfejs, znaci da cu da ga odredim kasnije,
    //sve operacije koje cu raditi kasnije nad svim enitetima, crud operacije

    //sve ovo radim nad domenskim klasama
    List<T> getAll(T param, String uslov) throws Exception;//vraca objekat koji zadovoljava ovaj uslov

    void add(T param) throws Exception;

    void edit(T param) throws Exception;

    void delete(T param) throws Exception;//delete nad objektom T, prosledjujemo id objekta 

    List<T> getAll(); //vraca sve objekte iz liste, umesto T moze da stavimo bilo koji Tip

    //int addReturnKey(T param) throws Exception;
    //void deleteWhere(T param, String whereClause) throws Exception;
    int addReturnKey(T param) throws Exception;

    void deleteWhere(T param, String whereClause) throws Exception;
}
