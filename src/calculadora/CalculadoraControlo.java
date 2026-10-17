package calculadora;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CalculadoraControlo {

    @FXML private Label display;
    @FXML private Label historico;
    @FXML private AnchorPane rootPane;
    @FXML private ColorPicker colorPicker;

    private double num1 = 0;
    private String operador = "";
    private boolean limparDisplay = false;
    private boolean modoEscuro = false;

    @FXML
    private void initialize() {
        colorPicker.setValue(Color.web("#0067c0"));
        colorPicker.setOnAction(e -> aplicarCor(colorPicker.getValue()));
    }

    private void aplicarCor(Color cor) {
        String hex = toHex(cor);
        rootPane.setStyle("-fx-background-color: " + hex + ";");
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    private void atualizarHistorico(String texto) {
        historico.setText(texto);
    }

    @FXML
    private void onNumero(ActionEvent e) {
        String valor = ((Button) e.getSource()).getText();
        if (limparDisplay || display.getText().equals("0")) {
            display.setText(valor);
            limparDisplay = false;
        } else {
            display.setText(display.getText() + valor);
        }
    }

    @FXML
    private void onOperador(ActionEvent e) {
        num1 = Double.parseDouble(display.getText().replace(",", "."));
        operador = ((Button) e.getSource()).getText();
        atualizarHistorico(formatar(num1) + " " + operador);
        limparDisplay = true;
    }

    @FXML
    private void onIgual(ActionEvent e) {
        double num2 = Double.parseDouble(display.getText().replace(",", "."));
        double resultado = 0;
        switch (operador) {
            case "+": resultado = num1 + num2; break;
            case "-": resultado = num1 - num2; break;
            case "×": resultado = num1 * num2; break;
            case "÷":
                if (num2 == 0) { display.setText("Erro"); return; }
                resultado = num1 / num2;
                break;
            default: return;
        }
        atualizarHistorico(formatar(num1) + " " + operador + " " + formatar(num2) + " =");
        display.setText(formatar(resultado));
        limparDisplay = true;
        operador = "";
    }

    @FXML
    private void onLimpar(ActionEvent e) {
        display.setText("0");
        historico.setText("");
        num1 = 0;
        operador = "";
        limparDisplay = false;
    }

    @FXML
    private void onApagar(ActionEvent e) {
        String t = display.getText();
        if (t.length() > 1) {
            display.setText(t.substring(0, t.length() - 1));
        } else {
            display.setText("0");
        }
    }

    @FXML
    private void onPonto(ActionEvent e) {
        if (!display.getText().contains(",")) {
            display.setText(display.getText() + ",");
        }
    }

    @FXML
    private void onInverterSinal(ActionEvent e) {
        double v = Double.parseDouble(display.getText().replace(",", "."));
        display.setText(formatar(-v));
    }

    @FXML
    private void onPorcento(ActionEvent e) {
        double v = Double.parseDouble(display.getText().replace(",", "."));
        display.setText(formatar(v / 100));
    }

    @FXML
    private void onInverso(ActionEvent e) {
        double v = Double.parseDouble(display.getText().replace(",", "."));
        if (v == 0) { display.setText("Erro"); return; }
        display.setText(formatar(1 / v));
    }

    @FXML
    private void onQuadrado(ActionEvent e) {
        double v = Double.parseDouble(display.getText().replace(",", "."));
        display.setText(formatar(v * v));
    }

    @FXML
    private void onRaiz(ActionEvent e) {
        double v = Double.parseDouble(display.getText().replace(",", "."));
        if (v < 0) { display.setText("Erro"); return; }
        display.setText(formatar(Math.sqrt(v)));
    }

    private String formatar(double v) {
        if (v == (long) v) {
            return String.valueOf((long) v);
        }
        return String.valueOf(v).replace(".", ",");
    }
}
