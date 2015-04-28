package fishing;

public class MethodClass {
    public static void printErrors(Exception e) {
        System.out.println("Class: " + e.getClass());
        System.out.println("Message: " + e.getMessage());
        System.out.println("Cause: " + e.getCause());
        e.printStackTrace();
    }
}
