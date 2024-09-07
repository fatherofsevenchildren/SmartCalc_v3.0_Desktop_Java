package edu.school21.smartcalc.presenter;

import edu.school21.smartcalc.view.MainWindowView;
import edu.school21.smartcalc.model.Model;

public class Presenter {
    private final MainWindowView mainView;
    private final Model model;

    public Presenter(MainWindowView mainView) {
        this.mainView = mainView;
        this.model = new Model();
    }

    public void calculate() {
        mainView.setAnswer(model.performCalculation(mainView.getExpression(), mainView.getValueX()));
    }

    public double calculateX(double xValue) {
        double res = 0.0;
        try {
            res = Double.parseDouble(model.performCalculation(mainView.getExpression(), String.valueOf(xValue)));
        } catch (Exception e) {
            System.out.println("was nan");
        }
        return res;
    }
}
