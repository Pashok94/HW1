import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaHW1 {
    public static void main(String[] args) {
        calculate("  13*3-  12/ 3");
    }

    public static void calculate(String str) {
        Matcher m = Pattern.compile("[^0-9*\\-+/ ]").matcher(str);
        if (m.find()) {
            System.out.println("Невалидный пример");
            return;
        }
        str = str.replaceAll(" ", "");

        List<Float> allNumbers = getAllNumbFromString(str);
        List<String> allOperations = getAllOperationsFromString(str);

        executeHighPriorOperations(allNumbers, allOperations);
        executeLowPriorOperation(allNumbers, allOperations);

        System.out.println(str + " = " + allNumbers.get(0));
    }

    private static void executeLowPriorOperation(List<Float> allNumbers, List<String> allOperations) {
        Iterator<String> iterator = allOperations.iterator();
        while (iterator.hasNext()) {
            String currentOperation = iterator.next();
            float result = executeCalc(allNumbers.get(0), allNumbers.get(1), currentOperation);
            allNumbers.remove(1);
            allNumbers.remove(0);

            allNumbers.add(0, result);
            iterator.remove();
        }
    }

    private static void executeHighPriorOperations(List<Float> allNumbers, List<String> allOperations) {
        Iterator<String> iterator = allOperations.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String currentOperation = iterator.next();
            if (currentOperation.equals("*") || currentOperation.equals("/")) {
                float result = executeCalc(allNumbers.get(i), allNumbers.get(i + 1), currentOperation);
                allNumbers.remove(i + 1);
                allNumbers.remove(i);

                allNumbers.add(i, result);
                iterator.remove();
            } else
                i++;
        }
    }

    private static float executeCalc(float numb1, float numb2, String operation) {
        switch (operation) {
            case "+":
                return numb1 + numb2;
            case "-":
                return numb1 - numb2;
            case "*":
                return numb1 * numb2;
            case "/":
                return numb1 / numb2;
            default:
                throw new ClassCastException();
        }
    }

    private static List<Float> getAllNumbFromString(String str) {
        Matcher numbMatcher = Pattern.compile("[0-9]+").matcher(str);
        List<String> allNumbers = new ArrayList<>();
        while (numbMatcher.find()) {
            allNumbers.add(numbMatcher.group());
        }
        List<Float> result = new ArrayList<>();
        for (String number : allNumbers) {
            result.add(Float.parseFloat(number));
        }
        return result;
    }

    private static List<String> getAllOperationsFromString(String str) {
        Matcher operationMatcher = Pattern.compile("[*/+-]").matcher(str);

        List<String> allOperations = new ArrayList<>();
        while (operationMatcher.find()) {
            allOperations.add(operationMatcher.group());
        }
        return allOperations;
    }
}
