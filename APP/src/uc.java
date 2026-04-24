public class uc {

    // Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // Inches class
    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // Static method for Feet equality
    public static boolean checkFeetEquality(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    // Static method for Inches equality
    public static boolean checkInchesEquality(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    // Main method
    public static void main(String[] args) {

        // Feet comparison
        System.out.println("1.0 ft == 1.0 ft: " + checkFeetEquality(1.0, 1.0));

        // Inches comparison
        System.out.println("1.0 inch == 1.0 inch: " + checkInchesEquality(1.0, 1.0));

        // Additional checks
        System.out.println("1.0 ft == 2.0 ft: " + checkFeetEquality(1.0, 2.0));
        System.out.println("1.0 inch == 2.0 inch: " + checkInchesEquality(1.0, 2.0));
    }
}