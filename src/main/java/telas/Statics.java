
package telas;

import java.util.List;
import model.classes.Municipio;
import model.classes.Programa;
import model.classes.Veterinario;
import model.services.UtilitarioService;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class Statics {

    public static Municipio municipioPadrao = new UtilitarioService().getMunicipioPadrao();
    public static List<Veterinario> listaVeterinarios = new VeterinarioService().getAll();
    public static List<String> listaRedatores = new UtilitarioService().getRedatores();
    public static List<Programa> listaProgramas = new UtilitarioService().getProgramas();
}
