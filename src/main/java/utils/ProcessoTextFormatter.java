package utils;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 * Classe utilitária para criar um TextFormatter para um número de processo
 * no formato XX/XXXX-XXXXXXX-X (14 dígitos).
 */
public class ProcessoTextFormatter {

    /**
     * Cria e retorna um TextFormatter para o campo de processo.
     * A máscara lida com digitação, deleção e colagem de valores.
     *
     * @return um TextFormatter configurado.
     */
    public static TextFormatter<String> createFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            
            String newText = change.getControlNewText();
            String digitsOnly = newText.replaceAll("[^0-9]", "");

            if (digitsOnly.length() > 14) {
                digitsOnly = digitsOnly.substring(0, 14);
            }

            // Salva a contagem de dígitos ANTES da posição do cursor.
            int digitsBeforeCaret = getDigitsBeforeCaret(change);

            String formatted = formatProcesso(digitsOnly);

            // Calcula a nova posição do cursor
            int newCaretPos = getNewCaretPosition(formatted, digitsBeforeCaret);

            change.setText(formatted);
            change.setRange(0, change.getControlText().length());
            change.setCaretPosition(newCaretPos);
            change.setAnchor(newCaretPos);
            
            return change;
        };

        return new TextFormatter<>(filter);
    }

    /**
     * Formata uma string de dígitos no padrão de processo.
     */
    private static String formatProcesso(String digitsOnly) {
        int len = digitsOnly.length();
        if (len == 0) return "";
        
        if (len <= 2) { // 12
            return digitsOnly;
        } else if (len <= 6) { // 12/3456
            return digitsOnly.substring(0, 2) + "/" +
                   digitsOnly.substring(2);
        } else if (len <= 13) { // 12/3456-7890123
            return digitsOnly.substring(0, 2) + "/" +
                   digitsOnly.substring(2, 6) + "-" +
                   digitsOnly.substring(6);
        } else { // 12/3456-7890123-4 (máximo de 14 dígitos)
            return digitsOnly.substring(0, 2) + "/" +
                   digitsOnly.substring(2, 6) + "-" +
                   digitsOnly.substring(6, 13) + "-" +
                   digitsOnly.substring(13);
        }
    }

    /**
     * Calcula quantos *dígitos* existem antes da posição do cursor no
     * texto proposto (ControlNewText).
     */
    private static int getDigitsBeforeCaret(TextFormatter.Change change) {
        // Texto que estaria no campo
        String newText = change.getControlNewText();
        
        // Posição do cursor que estaria no campo
        // ESTA É A LINHA CORRIGIDA:
        int caret = change.getControlCaretPosition(); 
        
        // Garante que o índice do cursor não ultrapasse o comprimento do texto
        int end = Math.min(caret, newText.length());

        // Conta os dígitos da string do início até o cursor
        return newText.substring(0, end).replaceAll("[^0-9]", "").length();
    }


    /**
     * Encontra a nova posição do cursor no texto recém-formatado,
     * baseando-se em quantos dígitos deveriam estar antes dele.
     */
    private static int getNewCaretPosition(String formattedText, int digitsBefore) {
        int newCaret = 0;
        int digitCount = 0;

        // Itera pelo texto formatado...
        for (int i = 0; i < formattedText.length(); i++) {
            // Se já encontramos o número de dígitos que procuramos,
            // esta 'i' é a nova posição do cursor.
            if (digitCount == digitsBefore) {
                newCaret = i;
                break;
            }
            // Se o caractere for um dígito, incrementa a contagem
            if (Character.isDigit(formattedText.charAt(i))) {
                digitCount++;
            }
        }

        // Se o loop terminou (cursor no final),
        // mas a contagem de dígitos bate, define o cursor para o final.
        if (digitCount == digitsBefore) {
            newCaret = formattedText.length();
        }

        return newCaret;
    }
}