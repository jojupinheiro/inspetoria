
package com.mycompany.inspetoria;

import model.classes.Municipio;
import model.services.UtilitarioService;

/**
 *
 * @author João Juliano Pinheiro
 * joaojulianopinheiro@hotmail.com
 */
public class Statics {

    public static Municipio municipioPadrao = new UtilitarioService().getMunicipioPadrao();
}
