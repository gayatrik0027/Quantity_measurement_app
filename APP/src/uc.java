public class uc {

    // Enum with conversion to base unit (FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),        // 1 inch = 1/12 feet
        YARD(3.0),               // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12.0); // 1 cm = 0.393701 inch → convert to feet

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

        // Convert everything to FEET
        private double toBase() {
            return unit.toFeet(value);
        }

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

    // Main method (demo)
    public static void main(String[] args) {

        System.out.println("1 yard == 3 feet: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET)));

        System.out.println("1 yard == 36 inch: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(36.0, LengthUnit.INCH)));

        System.out.println("2 yard == 2 yard: " +
                new Quantity(2.0, LengthUnit.YARD)
                        .equals(new Quantity(2.0, LengthUnit.YARD)));

        System.out.println("1 cm == 0.393701 inch: " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(0.393701, LengthUnit.INCH)));

        System.out.println("1 cm == 1 foot: " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(1.0, LengthUnit.FEET)));
    }
}