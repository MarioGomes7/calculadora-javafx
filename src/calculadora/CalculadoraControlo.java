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
    @FXML private AnchorPane rootPane;
    @FXML private ColorPicker colorPicker;

    private double num1 = 0;
    private String operador = "";
    private boolean limparDisplay = false;

    @FXML
    private void initialize() {
        colorPicker.setValue(Color.web("#2b2b2b"));
        colorPicker.setOnAction(e -> aplicarCor(colorPicker.getValue()));
    }

    private void aplicarCor(Color cor) {
        String hex = toHex(cor);
        rootPane.setStyle("-fx-background-color: " + hex + ";");
        for (var node : rootPane.getChildren()) {
            if (node instanceof Button b) {
                b.setStyle("-fx-background-color: derive(" + hex + ", 30%); -fx-text-fill: white; -fx-font-size: 16px;");
            }
        }
        display.setStyle("-fx-background-color: derive(" + hex + ", 20%); -fx-text-fill: white; -fx-font-size: 24px; -fx-alignment: center-right; -fx-padding: 10;");
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
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
        num1 = Double.parseDouble(display.getText());
        operador = ((Button) e.getSource()).getText();
        limparDisplay = true;
    }

    @FXML
    private void onIgual(ActionEvent e) {
        double num2 = Double.parseDouble(display.getText());
        double resultado = 0;
        switch (operador) {
            case "+": resultado = num1 + num2; break;
            case "-": resultado = num1 - num2; break;
            case "×": resultado = num1 * num2; break;
            case "÷":
                if (num2 == 0) { display.setText("Erro"); return; }
                resultado = num1 / num2;
                break;
        }
        if (resultado == (long) resultado) {
            display.setText(String.valueOf((long) resultado));
        } else {
            display.setText(String.valueOf(resultado));
        }
        limparDisplay = true;
    }

    @FXML
    private void onLimpar(ActionEvent e) {
        display.setText("0");
        num1 = 0;
        operador = "";
        limparDisplay = false;
    }

    @FXML
    private void onPonto(ActionEvent e) {
        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }
}
