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

        // 🔒 Private helper (DRY)
        private static double addInBase(Quantity q1, Quantity q2) {
            return q1.toBase() + q2.toBase();
        }

        // ✅ UC6 method (kept for backward compatibility)
        public Quantity add(Quantity other) {
            if (other == null) throw new IllegalArgumentException("Other cannot be null");

            double sum = addInBase(this, other);
            double result = this.unit.fromFeet(sum);

            return new Quantity(result, this.unit);
        }

        // ✅ UC7 method (explicit target unit)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = addInBase(this, other);
            double result = targetUnit.fromFeet(sum);

            return new Quantity(result, targetUnit);
        }

        // ✅ Static version
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = addInBase(q1, q2);
            double result = targetUnit.fromFeet(sum);

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

        Quantity f = new Quantity(1.0, LengthUnit.FEET);
        Quantity i = new Quantity(12.0, LengthUnit.INCH);

        // Explicit target units
        System.out.println("Feet result: " + f.add(i, LengthUnit.FEET));   // 2 ft
        System.out.println("Inch result: " + f.add(i, LengthUnit.INCH));   // 24 inch
        System.out.println("Yard result: " + f.add(i, LengthUnit.YARD));   // ~0.667 yard

        // Other combinations
        System.out.println("Yard + Feet → Yard: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));

        System.out.println("Inch + Yard → Feet: " +
                new Quantity(36.0, LengthUnit.INCH)
                        .add(new Quantity(1.0, LengthUnit.YARD), LengthUnit.FEET));

        System.out.println("CM + Inch → CM: " +
                new Quantity(2.54, LengthUnit.CENTIMETER)
                        .add(new Quantity(1.0, LengthUnit.INCH), LengthUnit.CENTIMETER));
    }
}