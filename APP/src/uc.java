public class uc {

    // Inner class representing Feet measurement
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {

            // Same reference check (reflexive)
            if (this == obj) {
                return true;
            }

            // Null and type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Cast and compare values safely
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        // Recommended when equals is overridden
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // Main method to test
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("1.0 ft == 1.0 ft: " + f1.equals(f2)); // true
        System.out.println("1.0 ft == 2.0 ft: " + f1.equals(f3)); // false
        System.out.println("1.0 ft == null: " + f1.equals(null)); // false
        System.out.println("Same reference: " + f1.equals(f1));   // true
    }
}