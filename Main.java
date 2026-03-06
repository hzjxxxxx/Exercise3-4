public class Main {
    public static void main(String[] args) {
        ERyder bike1 = new ERyder();
        bike1.printBikeDetails();

        System.out.println();

        ERyder bike2 = new ERyder(1001, 50, true, 12.5);
        bike2.ride();
        bike2.printBikeDetails();
    }
}