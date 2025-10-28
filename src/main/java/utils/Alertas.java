package utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 *
 * @author João Juliano Pinheiro joaojulianopinheiro@hotmail.com
 */
public class Alertas {

    public static boolean exibirConfirmacao(String titulo, String cabecalho, String conteudo) {

        // 1. Cria o Alerta do tipo CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);

        // 2. Cria os botões "Sim" e "Não"
        // ButtonData.YES informa ao JavaFX que este é um botão de "aceitação"
        ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        // ButtonData.NO informa que é um botão de "negação"
        ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);

        // 3. Remove os botões padrão (OK/Cancel) e adiciona os novos
        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        // 4. Exibe o alerta e espera a resposta do usuário
        // showAndWait() bloqueia a execução até que o usuário clique em um botão
        Optional<ButtonType> resultado = alert.showAndWait();

        // 5. Verifica qual botão foi clicado e retorna o booleano
        // Retorna true APENAS se o Optional tiver um valor E se esse valor for o 'botaoSim'
        return resultado.isPresent() && resultado.get() == botaoSim;
    }

    public static void exibirInformacao(String titulo, String cabecalho, String conteudo) {

        // 1. Cria o Alerta do tipo INFORMATION
        // Este tipo já inclui o botão "OK" por padrão.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // 2. Define os textos
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);

        // 3. Exibe o alerta e espera a resposta (o clique no "OK" ou o fechamento da janela)
        // Como o retorno é void, não precisamos capturar o resultado.
        alert.showAndWait();
    }
}
