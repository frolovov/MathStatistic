package com.example.mathstatistic1;

import android.util.Log;

public class SolveIntervalRow {
    private String[] inputedStrValues, inputedStrAmountIntervals;
    private double[] inputedValues;
    private int[] amountIntervals; // массив состоящий из количеств повторений каждого значения
    private double min;
    private double max;
    private double h;
    private int maxAmount = -1;
    private double[] intervalArray;
    private double sumOfValues;
    private int indexOfMaxAmount;
    private int medianInterval;
    private int sumAmountsBeforeMedianInterval;
    private int n;
    private boolean flagModa;
    private double sredneeArefm, dispersia, sigma, moda, mediana, koefVariac; // параметры массива

    public SolveIntervalRow(String[] inputedStrValues) {
        this.inputedStrValues = inputedStrValues;
    }

    public SolveIntervalRow(String[] inputedStrValues, String[] inputedStrAmountIntervals) {
        this.inputedStrValues = inputedStrValues;
        this.inputedStrAmountIntervals = inputedStrAmountIntervals;
    }

    public SolveIntervalRow(double[] inputedValues) {
        this.inputedValues = inputedValues;
    }

    public SolveIntervalRow(double[] inputedValues, int[] inputedAmountIntervals) {
        this.inputedValues = inputedValues;
        this.amountIntervals = inputedAmountIntervals;
    }

    // когда вариационный ряд отсутствует
    // преобразования String[] в double[] (массив введённых значений)
    public void convertStringArrayToDoubleArray() {
        inputedValues = new double[inputedStrValues.length];
        for (int i = 0; i < inputedStrValues.length; i++) {
            inputedValues[i] = Double.parseDouble(inputedStrValues[i]);
//            Log.d("my", "input " + i + " value: " + inputedValues[i]);
        }
    }

    // когда вариационный ряд есть
    // преобразования String[] в double[] (массив введённых значений)
    public void convertStringBorderArrayToDoubleBorderArray() {
        intervalArray = new double[inputedStrValues.length];
        for (int i = 0; i < inputedStrValues.length; i++) {
            intervalArray[i] = Double.parseDouble(inputedStrValues[i]);
//            Log.d("my", "input " + i + " value: " + inputedStrAmountIntervals[i]);
        }
    }

    // когда вариационный ряд есть
    // преобразования String[] в int[] (массив количества повторений каждого значения)
    public void convertStringArrayToIntArray() {
        amountIntervals = new int[inputedStrAmountIntervals.length];
        for (int i = 0; i < inputedStrAmountIntervals.length; i++) {
            amountIntervals[i] = Integer.parseInt(inputedStrAmountIntervals[i]);
        }
    }

    // когда вариационный ряд отсутствует
    // нахождение минимального в массиве чисел
     public void min() {
        min = inputedValues[0];
        for(int i = 1; i < inputedValues.length; i++) {
            min = Math.min(min, inputedValues[i]);
        }
//        Log.d("my", "min: " + min);
     }

    // когда вариационный ряд отсутствует
    // нахождение максимального в массиве чисел
    public void max() {
        max = inputedValues[0];
        for(int i = 1; i < inputedValues.length; i++) {
            max = Math.max(max, inputedValues[i]);
        }
//        Log.d("my", "max: " + max);
    }

    // когда вариационный ряд отсутствует
    // Находим значение h
    public void findIntervalSize() {
        h = (max - min) / (1 + 3.322 * Math.log10(inputedValues.length));
        h = h % 0.01 >= 0.005? h - (h % 0.01) + 0.01 : h - (h % 0.01); // округление до сотых
        Log.d("my", h + " " + max + " " + min);
//        Log.d("my", "h: " + h);
    }

    // когда вариационный ряд есть
    // Находим значение h
    public void findIntervalSizeFromBorders() {
        h = intervalArray[1] - intervalArray[0];
//        Log.d("my", "h: " + h);
    }

    // когда вариационный ряд отсутствует
    // получаем массив состоящий из интервалов без повторений
    public void createIntervalArray() {
        double test = min - (h/2);
        int count = 0;
        // если длинна интервала равна нулю (когда введённый пользователем массив состоит только из
        // одного повторяющегося числа) вайл зацикливается до бесконечности и приложение вылетает
        while(test <= max) {
            count++;
            test += h;
        }
        Log.d("my", "interval");
        intervalArray = new double[count + 1];
        Log.d("my", "interval");
        test = min - (h/2);
        Log.d("my", "interval");
        for (int i = 0; i < intervalArray.length; i++) {
            intervalArray[i] = test + (i*h);
            Log.d("my", "interval " + i + ": " + intervalArray[i]);
        }
    }

    // когда вариационный ряд отсутствует
    public void countValuesInEachInterval() {
        amountIntervals = new int[intervalArray.length];
        for (int i = 0; i < intervalArray.length - 1; i++) {
            for (int j = 0; j < inputedValues.length; j++) {
                if (inputedValues[j] >= intervalArray[i] && inputedValues[j] < intervalArray[i + 1]) {
                    amountIntervals[i]++;
//                    Log.d("my", "val: " + inputedValues[j] + " more than " + intervalArray[i] + " and less than " + intervalArray[i + 1]);
                }
            }
//            Log.d("my", "amount " + i + " interval: " + amountIntervals[i]);
        }
    }

    // общее для отсутствия и присутствия вариационного ряда
    public void findInputedValuesLength() {
        for (int i = 0; i < amountIntervals.length; i++) {
            n += amountIntervals[i];
        }
    }

    // общее для отсутствия и присутствия вариационного ряда
    // среднее арефметическое
    public void calcSredArefm() {
        for(int i = 0; i < intervalArray.length - 1; i++) {
            sumOfValues += (intervalArray[i] + (h/2)) * amountIntervals[i];
            Log.d("my", (intervalArray[i] + (h/2)) + " * " + amountIntervals[i]);
        }
        Log.d("my", "sumOfValues: " + sumOfValues);
        sredneeArefm = sumOfValues / n;
        Log.d("my", "sredArefm: " + sredneeArefm + " size: " + n);
        sumOfValues = 0;
//        Log.d("my", "sredArefm: " + sredneeArefm);
    }

    // общее для отсутствия и присутствия вариационного ряда
    // дисперсия
    public void calcDisp() {
        for(int i = 0; i < intervalArray.length - 1; i++) {
            sumOfValues += (intervalArray[i] + (h/2)) * (intervalArray[i] + (h/2)) * amountIntervals[i];
        }
        dispersia = (sumOfValues / n) - (sredneeArefm * sredneeArefm);
        sumOfValues = 0;
//        Log.d("my", "" + dispersia);
    }

    // общее для отсутствия и присутствия вариационного ряда
    // среднеквадратическое отклонение
    public void calcSigma() {
        sigma = Math.sqrt(dispersia);
//        Log.d("my", "" + sigma);
    }

    // общее для отсутствия и присутствия вариационного ряда
    // самое большое количество повторений
    public void maxAmountIntervals() {
        for (int i = 0; i < amountIntervals.length; i++) {
            if (amountIntervals[i] > maxAmount) {
                maxAmount = amountIntervals[i];
                indexOfMaxAmount = i;
            }
        }
//        Log.d("my", "maxAmountIntervals: " + maxAmount + " in index " + indexOfMaxAmount);
    }

    // общее для отсутствия и присутствия вариационного ряда
    public void medianInterval() {
        int count = 0;
        int i = 0;
        while (count < n / 2) {
            count += amountIntervals[i];
            if (count < n / 2) {
                medianInterval++;
            } else {
                break;
            }
            i++;
        }
//        Log.d("my", "medianInterval: " + medianInterval);
    }

    // общее для отсутствия и присутствия вариационного ряда
    // мода
    public void calcModa() {
        flagModa = true;
        // проверка на достаточное количество введённых значений чтобы можно было вычислить моду
        if (intervalArray.length > 3 && indexOfMaxAmount != 0 && indexOfMaxAmount != (intervalArray.length - 1)) {
            moda = intervalArray[indexOfMaxAmount] + ((h * (amountIntervals[indexOfMaxAmount] - amountIntervals[indexOfMaxAmount - 1])) / ((amountIntervals[indexOfMaxAmount] - amountIntervals[indexOfMaxAmount - 1]) + (amountIntervals[indexOfMaxAmount] - amountIntervals[indexOfMaxAmount + 1])));
        } else {
            flagModa = false;
        }
//        Log.d("my", "moda: " + moda);
    }

    // общее для отсутствия и присутствия вариационного ряда
    public void sumAmountsBeforeMedianInterval() {
        for (int i = 0; i < medianInterval; i ++) {
            sumAmountsBeforeMedianInterval += amountIntervals[i];
        }
//        Log.d("my", "sumAmountBeforeMedianInterval: " + sumAmountsBeforeMedianInterval);
    }

    // общее для отсутствия и присутствия вариационного ряда
    public void calcMediana() {
        mediana = intervalArray[indexOfMaxAmount] + (h * ((0.5 * n) - sumAmountsBeforeMedianInterval) / amountIntervals[indexOfMaxAmount]);
//        Log.d("my", "mediana: " + mediana);
    }

    // общее для отсутствия и присутствия вариационного ряда
    public double calcKoefVariac() {
        koefVariac = (sigma / sredneeArefm) * 100;
        return koefVariac;
//        Log.d("my", "" + koefVariac);
    }

    // общее для отсутствия и присутствия вариационного ряда
    public String Results() {
        String resultString = "Вариационный ряд\nxi; ni";
        // добавляем значения в вариационный ряд
        for (int i = 0; i < intervalArray.length - 1; i++) {
            resultString += "\n" + (float)intervalArray[i] + "-" + (float)intervalArray[i+1] + "; " + (int)amountIntervals[i];
        }
        // добавляем вычисленные параметры ряда (ПЛОХО НАПИСАН КОД НАВЕРНОЕ)
        resultString += flagModa? String.format("\nКол-во чисел: %d\nШаг h: %.3f\nСред-Арефм: %.3f\nДисперсия: %.3f\nСигма: %.3f\nМо: %.3f\nМе: %.3f\nКоэф вариац: %.3f", n, h, sredneeArefm, dispersia, sigma, moda, mediana, koefVariac) :
                String.format("\nКол-во чисел: %d\nШаг h: %.3f\nСред-Арефм: %.3f\nДисперсия: %.3f\nСигма: %.3f\nМо: %.3f (невозможно вычислить)\nМе: %.3f\nКоэф вариац: %.3f", n, h, sredneeArefm, dispersia, sigma, moda, mediana, koefVariac);
        return resultString.toString();
    }
}
