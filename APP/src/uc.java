public class uc {

    // Enum (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // Quantity class (immutable)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        // ✅ UC6: Add method (instance)
        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");

            double sumInFeet = this.toBase() + other.toBase();
            double result = this.unit.fromFeet(sumInFeet);

            return new Quantity(result, this.unit); // result in unit of first operand
        }

        // ✅ Static version (optional)
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumInFeet = q1.toBase() + q2.toBase();
            double result = targetUnit.fromFeet(sumInFeet);

            return new Quantity(result, targetUnit);
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

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Demo
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        // Instance addition
        System.out.println("1 ft + 12 inch = " + q1.add(q2)); // 2 feet

        // Reverse (unit changes)
        System.out.println("12 inch + 1 ft = " +
                new Quantity(12.0, LengthUnit.INCH).add(q1)); // 24 inches

        // Yard example
        System.out.println("1 yard + 3 ft = " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET)));

        // Centimeter example
        System.out.println("2.54 cm + 1 inch = " +
                new Quantity(2.54, LengthUnit.CENTIMETER)
                        .add(new Quantity(1.0, LengthUnit.INCH)));

        // Static method with target unit
        System.out.println("Static add (in FEET): " +
                Quantity.add(q1, q2, LengthUnit.FEET));
    }
}