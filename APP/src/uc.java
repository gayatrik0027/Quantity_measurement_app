public class uc {

    // Enum with conversion factors (base = FEET)
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
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        // Convert to another unit (instance method)
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = toBase();
            double converted = targetUnit.fromFeet(base);
            return new Quantity(converted, targetUnit);
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

    // ✅ Static conversion API (MAIN REQUIREMENT)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        // normalize → base (feet)
        double base = source.toFeet(value);

        // convert → target
        return target.fromFeet(base);
    }

    // 🔁 Overloaded demo methods (Method Overloading)

    // Method 1
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Method 2
    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity converted = q.convertTo(to);
        System.out.println(q + " = " + converted);
    }

    // Equality demo
    public static void demonstrateLengthEquality(Quantity q1, Quantity q2) {
        System.out.println(q1 + " == " + q2 + " : " + q1.equals(q2));
    }

    // Main method
    public static void main(String[] args) {

        // Basic conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        // Using Quantity object
        Quantity q = new Quantity(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET);

        // Equality check
        demonstrateLengthEquality(
                new Quantity(1.0, LengthUnit.FEET),
                new Quantity(12.0, LengthUnit.INCH)
        );
    }
}