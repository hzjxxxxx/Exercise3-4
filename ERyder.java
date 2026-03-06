public class ERyder {
    private int bikeID;
    private int batteryLevel;      // 0 to 100
    private boolean isAvailable;
    private double kmDriven;

    // Default constructor
    public ERyder() {
        this.bikeID = 0;
        this.batteryLevel = 100;
        this.isAvailable = true;
        this.kmDriven = 0.0;
    }

    // Constructor with all parameters
    public ERyder(int bikeID, int batteryLevel, boolean isAvailable, double kmDriven) {
        this.bikeID = bikeID;
        setBatteryLevel(batteryLevel); // must be 0-100
        this.isAvailable = isAvailable;
        this.kmDriven = kmDriven;
    }

    public void ride() {
        if (batteryLevel > 0 && isAvailable) {
            System.out.println("The bike is available.");
        } else {
            System.out.println("The bike is not available.");
        }
    }

    public void printBikeDetails() {
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Available: " + isAvailable);
        System.out.println("Distance Travelled: " + kmDriven + " km");
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            System.out.println("Invalid battery level. Must be between 0 and 100.");
            return;
        }
        this.batteryLevel = batteryLevel;
    }
}