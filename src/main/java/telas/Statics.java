
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

    public static String usuarioBD = getUsuarioDB();
    public static String senhaBD = getSenhaDB();
    
    public static Municipio municipioPadrao = new UtilitarioService().getMunicipioPadrao();
    public static List<Veterinario> listaVeterinarios = new VeterinarioService().getAll();
    public static List<String> listaRedatores = new UtilitarioService().getRedatores();
    public static List<Programa> listaProgramas = new UtilitarioService().getProgramas();
    
    
    public static String getUsuarioDB() {
        String usuarioWindows = "root";
        String usuarioLinux = "juliano";
        String so = System.getProperty("os.name").toLowerCase();
        String usuario;

        if (so.contains("win")) {
            usuario = usuarioWindows;
        } else {
            usuario = usuarioLinux;
        }
        return usuario;
    }
    
    public static String getSenhaDB() {
        String senhaWindows = "";
        String senhaLinux = "5423gfe";
        String so = System.getProperty("os.name").toLowerCase();
        String senha;

        if (so.contains("win")) {
            senha = senhaWindows;
        } else {
            senha = senhaLinux;
        }
        return senha;
    }
}
