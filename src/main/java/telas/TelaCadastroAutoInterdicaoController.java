package telas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import model.classes.AutoInterdicao;

/**
 * FXML Controller class
 *
 * @author Juliano
 */
public class TelaCadastroAutoInterdicaoController implements Initializable {

    AutoInterdicao aiSalvo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public AutoInterdicao getAISalvo(){
        return aiSalvo;
    }
}
