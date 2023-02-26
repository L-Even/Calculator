import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setOperate("+");
        calculator.setNewNumber(new BigDecimal("10"));
        System.out.println(calculator.display());
        calculator.count();
        System.out.println(calculator.display());
        calculator.setOperate("*");
        calculator.setNewNumber(new BigDecimal("30"));
        System.out.println(calculator.display());
        calculator.count();
        System.out.println(calculator.display());
        calculator.undo();
        System.out.println(calculator.display());
        calculator.setOperate("+");
        calculator.setNewNumber(new BigDecimal("80"));
        calculator.count();
        System.out.println(calculator.display());
        calculator.undo();
        calculator.setOperate("*");
        calculator.setNewNumber(new BigDecimal("10"));
        calculator.count();
        System.out.println(calculator.display());
        calculator.undo();
        calculator.redo();
        calculator.redo();
    }
}
