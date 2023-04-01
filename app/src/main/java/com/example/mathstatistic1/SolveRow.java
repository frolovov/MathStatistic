package com.example.mathstatistic1;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SolveRow {

    private String[] inputedStrValues; // введёный пользователем числовой массив если он типа String
    private double[] inputedValues; // введённый пользователем числовой массив
    private String[] inputedStrAmountValues; // введённый пользователем массив количества каждого значения если он типа String
    private List<Double> uniqueListValues; // список без повторяющихся значений
    private double[] uniqueValues; // массив без повторяющихся значений
    private int[] amountValues; // массив состоящий из количеств повторений каждого значения
    private int maxRepeats; // максимальное количество повторений
    private double sumOfNums; // сумма всех значений числового массива
    private double sredneeArefm, dispersia, sigma, mediana; // параметры массива

    // конструктор принимающий числовой массив введённый пользователем
    public SolveRow(double[] inputedValues) {
        this.inputedValues = inputedValues;
    }

    // конструктор принимающий числовой массив введённый пользователем если он типа String
    public SolveRow(String[] inputedStrValues) {
        this.inputedStrValues = inputedStrValues;
    }

    // конструктор принимающий два числовых массива (Значения без повторений и их количество)
    public SolveRow(double[] inputedValues, int[] inputedAmountValues) {
        this.inputedValues = inputedValues;
        this.amountValues = inputedAmountValues;
    }

    // конструктор принимающий два числовых массива (Значения без повторений и их количество)
    // если они типа String
    public SolveRow(String[] inputedStrValues, String[] inputedStrAmountValues) {
        this.inputedStrValues = inputedStrValues;
        this.inputedStrAmountValues = inputedStrAmountValues;
    }

    // преобразования String[] в double[] (массив введённых значений)
    public void convertStringArrayToDoubleArray() {
        inputedValues = new double[inputedStrValues.length];
        for (int i = 0; i < inputedStrValues.length; i++) {
            inputedValues[i] = Double.parseDouble(inputedStrValues[i]);
        }
    }

    // преобразования String[] в int[] (массив количества повторений каждого значения)
    public void convertStringArrayToIntArray() {
        amountValues = new int[inputedStrAmountValues.length];
        for (int i = 0; i < inputedStrAmountValues.length; i++) {
            amountValues[i] = Integer.parseInt(inputedStrAmountValues[i]);
        }
    }

    // преобразования String[] в double[] для массива без повторяющихся значений
    public void convertStringUniqueArrayToDoubleUniqueArray() {
        uniqueValues = new double[inputedStrValues.length];
        for (int i = 0; i < inputedStrValues.length; i++) {
            uniqueValues[i] = Double.parseDouble(inputedStrValues[i]);
        }
    }

    // сортировка массива по возрастанию (для нахождения медианы)
    public void sort() {
        Double temp;
        for (int i = 0; i < inputedValues.length; i++) {
            for (int j = i + 1; j < inputedValues.length; j++) {
                if (inputedValues[j] < inputedValues[i]) {
                    temp = inputedValues[i];
                    inputedValues[i] = inputedValues[j];
                    inputedValues[j] = temp;
                }
            }
        }
    }

    // создание списка для получения значений числового массива без повторов
    public void createUniqueList() {
        uniqueListValues = new ArrayList<>();
        for (Double d : inputedValues) {
            if (!uniqueListValues.contains(d)) { // проверяем есть ли такое значение уже в списке
                uniqueListValues.add(d);
            }
        }
    }

    // конвертация списка в массив для удобной работы дальнейших методов как с изначально
    // готовым вариационным рядом так и без него
    public void convertDoubleListToDoubleArray() {
        uniqueValues = new double[uniqueListValues.size()];
        for (int i = 0; i < uniqueListValues.size(); i++) {
            uniqueValues[i] = uniqueListValues.get(i);
        }
    }

    // формирование полного массива значений из массива без повторов и массива количества повторений
    // каждого значения
    public void uniqueValToInputedValues() {
        int count = 0;
        for (int i = 0; i < amountValues.length; i++) {
            for (int j = 0; j < amountValues[i]; j++) {
                count++;
            }
        }
        inputedValues = new double[count];
        count = 0;
        for (int i = 0; i < amountValues.length; i++) {
            for (int j = 0; j < amountValues[i]; j++) {
                inputedValues[count] = uniqueValues[i];
                count++;
            }
        }
    }

    // количество повторений всех значений в массиве
    public void countValues() {
        amountValues = new int[uniqueValues.length];
        for (int i = 0; i < uniqueValues.length; i++) {
            for (int j = 0; j < inputedValues.length; j++) {
                if (uniqueValues[i] == inputedValues[j]) {
                    amountValues[i]++;
                }
            }
        }
    }

    // самое большое количество повторений одного значения в массиве
    public void maxRepeatValuesInRow() {
        for (int i = 0; i < uniqueValues.length; i++) {
            if (maxRepeats < amountValues[i]) {
                maxRepeats = amountValues[i];
            }
        }
    }

    // подсчёт среднего арефметического
    public void calcSredArefm() {
        for (int i = 0; i < uniqueValues.length; i++) {
            sumOfNums += uniqueValues[i] * amountValues[i];
        }
        sredneeArefm = sumOfNums / inputedValues.length;
        sumOfNums = 0;
    }

    // подсчёт дисперсии
    public void calcDisp() {
        for (int i = 0; i < uniqueValues.length; i++) {
            sumOfNums += uniqueValues[i] * uniqueValues[i] * amountValues[i];
        }
        dispersia = (sumOfNums / inputedValues.length) - (sredneeArefm * sredneeArefm);
        sumOfNums = 0;
    }

    // подсчёт среднеквадратичного отклонения
    public void calcSigma() {
        sigma = (float) Math.sqrt(dispersia);
    }

    // подсчёт медианы
    public void calcMediana() {
        mediana = inputedValues.length % 2 == 0 ? (inputedValues[inputedValues.length / 2] + inputedValues[(inputedValues.length / 2) - 1]) / 2 : inputedValues[inputedValues.length / 2];
    }

    // формирование строки результата
    public String Results() {
        String resultString = "Вариационный ряд\nxi; ni";
        // добавляем значения в вариационный ряд
        for (int i = 0; i < uniqueValues.length; i++) {
            resultString += "\n" + uniqueValues[i] + "; " + (amountValues[i] == maxRepeats ? amountValues[i] + " (Мо)" : amountValues[i]);
        }
        // добавляем вычисленные параметры ряда
        resultString += String.format("\nКол-во чисел: %d\nСред-Арефм: %.3f\nДисперсия: %.3f\nСигма: %.3f\nМе: %.3f", inputedValues.length, sredneeArefm, dispersia, sigma, mediana);
        return resultString;
    }
}
