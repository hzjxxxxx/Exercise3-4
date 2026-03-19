import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class UserRegistration {
    private static final double VIP_DISCOUNT_UNDER_18_BIRTHDAY = 25.0;
    private static final double VIP_DISCOUNT_UNDER_18 = 20.0;
    private static final double VIP_BASE_FEE = 100.0;
    
    private String fullName;
    private String emailAddress;
    private String dateOfBirth;
    private long cardNumber;
    private String cardProvider;
    private String cardExpiryDate;
    private double feeToCharge;
    private int cvv;
    private String userType;
    private boolean emailValid;
    private boolean minorAndBirthday;
    private boolean minor;
    private boolean ageValid;
    private boolean cardNumberValid;
    private boolean cardStillValid;
    private boolean validCVV;
    
    private Scanner scanner;
    
    public UserRegistration() {
        scanner = new Scanner(System.in);
    }
    
    public void registration() {
        System.out.println("Welcome to the ERyder Registration.");
        System.out.println("Here are your two options:");
        System.out.println("1. Register as a Regular User");
        System.out.println("2. Register as a VIP User");
        System.out.print("Please enter your choice (1 or 2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            userType = "Regular User";
        } else {
            userType = "VIP User";
        }
        
        System.out.print("Enter your full name: ");
        fullName = scanner.nextLine();
        
        System.out.print("Enter your email address: ");
        emailAddress = scanner.nextLine();
        
        // Check email
        if (emailAddress.contains("@") && emailAddress.contains(".")) {
            System.out.println("Email is valid");
            emailValid = true;
        } else {
            System.out.println("Invalid email address. Going back to the start of the registration");
            registration();
            return;
        }
        
        System.out.print("Enter your date of birth (YYYY-MM-DD): ");
        dateOfBirth = scanner.nextLine();
        LocalDate dob = LocalDate.parse(dateOfBirth);
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        boolean isBirthday = (dob.getMonthValue() == currentDate.getMonthValue()) && 
                             (dob.getDayOfMonth() == currentDate.getDayOfMonth());
        
        minorAndBirthday = false;
        minor = false;
        
        if (userType.equals("VIP User")) {
            if (isBirthday && age <= 18 && age > 12) {
                System.out.println("Happy Birthday!");
                System.out.println("You get 25% discount on the VIP subscription fee for being born today and being under 18!");
                minorAndBirthday = true;
            } else if (!isBirthday && age <= 18 && age > 12) {
                System.out.println("You get 20% discount on the VIP subscription fee for being under 18!");
                minor = true;
            }
        }
        
        if (age <= 12 || age > 120) {
            System.out.println("Looks like you are either too young or already dead. Sorry, you can't be our user. Have a nice day");
            System.exit(0);
        }
        
        ageValid = true;
        
        System.out.print("Enter your card number (only Visa, MasterCard, and American Express are accepted): ");
        cardNumber = scanner.nextLong();
        scanner.nextLine();
        String cardNumStr = String.valueOf(cardNumber);
        int firstTwoDigits = Integer.parseInt(cardNumStr.substring(0, 2));
        int firstFourDigits = Integer.parseInt(cardNumStr.substring(0, 4));
        
        if ((cardNumStr.length() == 13 || cardNumStr.length() == 15) && cardNumStr.startsWith("4")) {
            cardProvider = "VISA";
            cardNumberValid = true;
        } else if (cardNumStr.length() == 16 && 
                  ((firstTwoDigits >= 51 && firstTwoDigits <= 55) || 
                   (firstFourDigits >= 2221 && firstFourDigits <= 2720))) {
            cardProvider = "MasterCard";
            cardNumberValid = true;
        } else if (cardNumStr.length() == 15 && 
                  (cardNumStr.startsWith("34") || cardNumStr.startsWith("37"))) {
            cardProvider = "American Express";
            cardNumberValid = true;
        } else {
            System.out.println("Sorry, but we accept only VISA, MasterCard, or American Express cards. Please try again with a valid card.");
            System.out.println("Going back to the start of the registration.");
            registration();
            return;
        }
        
        System.out.print("Enter your card expiry date (MM/YY): ");
        cardExpiryDate = scanner.nextLine();
        int month = Integer.parseInt(cardExpiryDate.substring(0, 2));
        int year = Integer.parseInt(cardExpiryDate.substring(3, 5)) + 2000;
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        
        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            System.out.println("The card is still valid");
            cardStillValid = true;
        } else {
            System.out.println("Sorry, your card has expired. Please use a different card.");
            System.out.println("Going back to the start of the registration process...");
            registration();
            return;
        }
        
        System.out.print("Enter your card's CVV: ");
        cvv = scanner.nextInt();
        scanner.nextLine();
        String cvvStr = String.valueOf(cvv);
        
        if ((cardProvider.equals("American Express") && cvvStr.length() == 4) ||
            (cardProvider.equals("VISA") && cvvStr.length() == 3) ||
            (cardProvider.equals("MasterCard") && cvvStr.length() == 3)) {
            System.out.println("Card CVV is valid.");
            validCVV = true;
        } else {
            System.out.println("Invalid CVV for the given card.");
            System.out.println("Going back to the start of the registration process.");
            registration();
            return;
        }
        
        if (emailValid && ageValid && cardNumberValid && cardStillValid && validCVV) {
            chargeFees();
        } else {
            System.out.println("Sorry, your registration was unsuccessful due to the following reason(s)");
            if (!emailValid) {
                System.out.println("Invalid email address");
            }
            if (!ageValid) {
                System.out.println("Invalid age");
            }
            if (!cardNumberValid) {
                System.out.println("Invalid card number");
            }
            if (!cardStillValid) {
                System.out.println("Card has expired");
            }
            if (!validCVV) {
                System.out.println("Invalid CVV");
            }
            System.out.println("Going back to the start of the registration process.");
            registration();
            return;
        }
        
        scanner.close();
    }
    
    private void chargeFees() {
        if (minorAndBirthday) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18_BIRTHDAY / 100);
        } else if (minor) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18 / 100);
        } else {
            feeToCharge = VIP_BASE_FEE;
        }
        
        String cardNumStr = String.valueOf(cardNumber);
        String lastFourDigits = cardNumStr.substring(cardNumStr.length() - 4);
        
        System.out.println("Thank you for your payment.");
        System.out.println("A fee of " + feeToCharge + " has been charged to your card ending with " + lastFourDigits);
    }
    
    @Override
    public String toString() {
        String cardNumberStr = String.valueOf(cardNumber);
        String censoredPart = cardNumberStr.substring(0, cardNumberStr.length() - 4).replaceAll(".", "*");
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);
        String censoredNumber = censoredPart + lastFourDigits;
        
        return "Registration successful! Here are your details:\n" +
               "User Type: " + userType + "\n" +
               "Full Name: " + fullName + "\n" +
               "Email Address: " + emailAddress + "\n" +
               "Date of Birth: " + dateOfBirth + "\n" +
               "Card Number: " + censoredNumber + "\n" +
               "Card Provider: " + cardProvider + "\n" +
               "Card Expiry Date: " + cardExpiryDate;
    }
}
