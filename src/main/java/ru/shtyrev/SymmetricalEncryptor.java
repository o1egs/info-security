package ru.shtyrev;

import java.util.Arrays;

public class SymmetricalEncryptor {
    public static String singleKeyPermutation(String key, String text) {
        var keyDistinct = Arrays.stream(key.split(""))
                .distinct()
                .toList();
        int columns = keyDistinct.size();
        int rows = text.length() % columns == 0 ? text.length() / columns : text.length() / columns + 1;
        char[][] matrix = new char[rows][columns];
        int textIterator = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (textIterator >= text.length()) {
                    matrix[i][j] = ' ';
                } else {
                    matrix[i][j] = text.charAt(textIterator);
                    textIterator++;
                }
            }
        }
        var keySorted = keyDistinct.stream()
                .sorted()
                .toList();
        var encrypted = new StringBuilder();
        for (String el : keySorted) {
            int tColumn = keyDistinct.lastIndexOf(el);
            for (int i = 0; i < rows; i++) {
                encrypted.append(matrix[i][tColumn]);
            }
        }
        return encrypted.toString();
    }

    public static String decrypt(String key, String text) {
        var keyDistinct = Arrays.stream(key.split(""))
                .distinct()
                .toList();
        int rows = text.length() / keyDistinct.size();
        int columns = keyDistinct.size();
        char[][] matrix = new char[rows][columns];
        var keySorted = keyDistinct.stream()
                .sorted()
                .toList();
        int textIterator = 0;
        for (String el : keySorted) {
            int tColumn = keyDistinct.lastIndexOf(el);
            for (int i = 0; i < rows; i++) {
                matrix[i][tColumn] = text.charAt(textIterator++);
            }
        }
        var encrypted = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                encrypted.append(matrix[i][j]);
            }
        }
        return encrypted.toString().strip();
    }
}
