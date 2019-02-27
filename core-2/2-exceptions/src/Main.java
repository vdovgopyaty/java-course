public class Main {

    private final static int MATRIX_SIZE = 4;

    public static void main(String[] args) {
        String[][] matrix = {
            {"1", "2", "3", "4"},
            {"5", "6", "7", "8"},
            {"9", "10", "11", "12"},
            {"13", "14", "15", "16"}
        };

        try {
            int sum = getElementsSum(matrix);
            System.out.println("Сумма элементов массива: " + sum);
        } catch (MyArraySizeException e) {
            System.out.println("Матрица неверного размера! " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("В матрице неверный символ! " + e.getMessage());
        }
    }

    public static int getElementsSum(String[][] matrix) {
        return elementsSum(toIntMatrix(matrixCheck(matrix)));
    }

    private static int elementsSum(int[][] matrix) {
        int sum = 0;
        for (int[] array : matrix) {
            for (int item : array) {
                sum += item;
            }
        }
        return sum;
    }

    private static int[][] toIntMatrix(String[][] stringMatrix) {
        int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < stringMatrix.length; i++) {
            for (int j = 0; j < stringMatrix[i].length; j++) {
                try {
                    matrix[i][j] = Integer.parseInt(stringMatrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("В (" + i + "," + j + ") ячейке матрицы лежит символ '" + stringMatrix[i][j] + "', а ожидалось число");
                }
            }
        }
        return matrix;
    }

    private static String[][] matrixCheck(String[][] matrix) {
        if (matrix.length != MATRIX_SIZE)
            throw new MyArraySizeException("Матрица должна содержать 4 вложенных массива");
        for (String[] array: matrix) {
            if (array.length != MATRIX_SIZE)
                throw new MyArraySizeException("Вложенный массив должен содержать 4 элемента");
        }
        return matrix;
    }

    private static class MyArraySizeException extends RuntimeException {
        MyArraySizeException(String message) {
            super(message);
        }
    }

    private static class MyArrayDataException extends RuntimeException {
        MyArrayDataException(String message) {
            super(message);
        }
    }
}