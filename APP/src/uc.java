public class uc {

    // Enum for units with conversion to base unit (feet)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Generic Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toBase() {
            return unit.toFeet(value);
        }

        // Equality check with conversion
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBase());
        }
    }

    // Main method for demo
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("1 ft == 12 inch: " + q1.equals(q2)); // true
        System.out.println("1 inch == 1 inch: " + q3.equals(new Quantity(1.0, LengthUnit.INCH))); // true
        System.out.println("1 ft == 2 ft: " + q1.equals(new Quantity(2.0, LengthUnit.FEET))); // false
    }
}