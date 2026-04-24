public class uc {

    // ✅ Standalone Enum (UC8 refactoring)
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        // Convert → base unit (FEET)
        public double convertToBaseUnit(double value) {
            return value * toFeet;
        }

        // Convert from base unit (FEET) → this unit
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / toFeet;
        }
    }

    // ✅ Quantity class
    static class Quantity {

        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        // convert to base (FEET)
        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        // 🔁 convert to any unit
        public Quantity convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = toBase();
            double result = targetUnit.convertFromBaseUnit(base);

            return new Quantity(result, targetUnit);
        }

        // ➕ add (default unit = first operand)
        public Quantity add(Quantity other) {
            return add(other, this.unit);
        }

        // ➕ add with explicit target unit (UC7 + UC8 support)
        public Quantity add(Quantity other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sum = this.toBase() + other.toBase();
            double result = targetUnit.convertFromBaseUnit(sum);

            return new Quantity(result, targetUnit);
        }

        // 🔍 equality (based on base unit)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Quantity)) return false;

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

    // ✅ Main method (test)
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println("Convert: " + q1.convertTo(LengthUnit.INCH));
        System.out.println("Add (FEET): " + q1.add(q2, LengthUnit.FEET));
        System.out.println("Add (INCH): " + q1.add(q2, LengthUnit.INCH));
        System.out.println("Add (YARD): " + q1.add(q2, LengthUnit.YARD));

        System.out.println("Equals: " +
                q1.equals(new Quantity(12.0, LengthUnit.INCH)));

        System.out.println("YARD + FEET → YARD: " +
                new Quantity(1.0, LengthUnit.YARD)
                        .add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));
    }
}