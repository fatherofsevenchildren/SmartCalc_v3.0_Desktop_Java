package edu.school21.smartcalc.view;


import edu.school21.smartcalc.presenter.Presenter;
import edu.school21.smartcalc.serializer.Serializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowView implements Initializable {

    @FXML
    private Label expression;
    @FXML
    private TextField valueX;
    @FXML
    private Label answer;
    @FXML
    private LineChart<Number, Number> plot;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Spinner<Double> xMin;
    @FXML
    private Spinner<Double> xMax;
    @FXML
    private Spinner<Double> yMin;
    @FXML
    private Spinner<Double> yMax;
    @FXML
    private ListView<String> listView;

    private Presenter presenter;
    private final Serializer serializer = new Serializer();

    private int flagBracket = 0;
    private boolean flagPoint = false;  // считаем чтобы не было точек больше 1
    private int eCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        presenter = new Presenter(this);
        setValidators();
        initSpinners();
        loadlistView();
    }


    public void setAnswer(String answer) {
        this.answer.setText(answer);
    }

    public String getValueX() {
        String textX = valueX.getText();
        return textX.isEmpty() ? "0" : textX;
    }

    public String getExpression() {
        return expression.getText();
    }

    public void setExpression(String expression) {
        this.expression.setText(expression);
    }


    @FXML
    protected void onEquallButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if ("1234567890x)".contains("" + lastChar) && flagBracket == 0 && expressionText.length() < 255) {
            presenter.calculate();
            serializer.addExpression(expressionText);
            loadlistView();
        }
    }

    @FXML
    protected void onClearButtonClick() {
        serializer.clearExpressions();
        loadlistView();
        setExpression("0");
    }

    @FXML
    protected void onPlotButtonClick() {
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(xMin.getValue());
        xAxis.setUpperBound(xMax.getValue());
        xAxis.setTickUnit(1);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(yMin.getValue());
        yAxis.setUpperBound(yMax.getValue());
        yAxis.setTickUnit(1);
        plot.setData(getGrafData());
    }

    @FXML
    protected void onNumberButtonClick(ActionEvent event) {
        String expressionText = expression.getText();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (lastChar != 'x' && lastChar != ')' && eCount < 3) {
            Button button = (Button) event.getSource();
            if ("0".equals(expressionText)) {
                expression.setText(button.getText());
            } else {
                expression.setText(expression.getText() + button.getText());
            }
        }
    }

    @FXML
    protected void onACButtonClick() {
        flagBracket = eCount = 0;
        flagPoint = false;
        setExpression("0");
    }

    @FXML
    protected void onOperationButtonClick(ActionEvent event) {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (!"/*+-^%(d".contains("" + lastChar)) {
            Button button = (Button) event.getSource();
            setExpression(expressionText + button.getText());
        }
    }

    @FXML
    protected void onMinusButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if ("0".equals(expressionText)) {
            setExpression("-");
        } else if (!"/*+-^%d".contains("" + lastChar)) {
            setExpression(expressionText + "-");
            flagPoint = false;
        }
    }

    @FXML
    protected void onTriganometryOperationButtonClick(ActionEvent event) {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        Button button = (Button) event.getSource();
        if ("0".equals(expressionText)) {
            setExpression(button.getText() + "(");
        } else if (!(".)1234567890ed").contains("" + lastChar)) {
            setExpression(expressionText + button.getText() + "(");
        }
        flagPoint = false;
        flagBracket++;
    }

    @FXML
    protected void onPointButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (!flagPoint && Character.isDigit(lastChar)) {
            setExpression(expressionText + ".");
            flagPoint = true;
        }
    }

    @FXML
    protected void onBracketOpenButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if ("0".equals(expressionText)) {
            setExpression("(");
            flagBracket++;
        } else if (!"x.)e1234567890".contains(lastChar + "")) {
            setExpression(expressionText + "(");
            flagBracket++;
        }
    }

    @FXML
    protected void onBracketClouseButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (flagBracket != 0 && (")x1234567890".contains(lastChar + ""))) {
            setExpression(expressionText + ")");
            flagBracket--;
        }
    }

    @FXML
    protected void onXButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if ("0".equals(expressionText)) {
            setExpression("x");
        } else if (!"x.)e1234567890".contains(lastChar + "")) {
            setExpression(expressionText + "x");
        }
    }


    @FXML
    protected void onEButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (Character.isDigit(lastChar)) {
            setExpression(expressionText + "e");
            eCount++;
        }
    }


    @FXML
    protected void onCleanButtonClick() {
        String expressionText = getExpression();
        char lastChar = expressionText.charAt(expressionText.length() - 1);
        if (lastChar == '.') {
            flagPoint = false;
        } else if (lastChar == ')') {
            flagBracket++;
        }
        if (expressionText.length() == 1) {
            setExpression("0");
        } else {
            do {
                expressionText = expressionText.substring(0, expressionText.length() - 1);
            } while (Character.isLetter(expressionText.charAt(expressionText.length() - 1)) && expressionText.length() > 1);
            setExpression(expressionText);
        }
    }

    private void initSpinners() {
        xMin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100000, 100000, -10));
        xMax.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100000, 100000, 10));
        yMin.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100000, 100000, -10));
        yMax.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-100000, 100000, 10));
    }

    private ObservableList<XYChart.Series<Number, Number>> getGrafData() {
        XYChart.Series<Number, Number> productOne = new XYChart.Series<>();
        for (double i = 0, j = 0; i < (xMax.getValue() - xMin.getValue()); i += (xMax.getValue() - xMin.getValue()) / 100, j += (yMax.getValue() - yMin.getValue()) / 100) {
            productOne.getData().addAll(new XYChart.Data<>(xMin.getValue() + i, presenter.calculateX(yMin.getValue() + j)));
        }
        ObservableList<XYChart.Series<Number, Number>> data = FXCollections.observableArrayList();
        data.addAll(productOne);
        return data;
    }

    private void loadlistView() {
        listView.getItems().clear();
        listView.getItems().addAll(serializer.getExpressions());
        listView.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> expression.setText(listView.getSelectionModel().getSelectedItem()));
    }

    private void setValidators() {
        valueX.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
            if (!keyEvent.getCharacter().matches("[\\d.-]") || (keyEvent.getCharacter().equals(".") && valueX.getText().contains(".")) || (keyEvent.getCharacter().equals("-") && !valueX.getText().isEmpty())) {
                keyEvent.consume();
            }
        });
    }
}