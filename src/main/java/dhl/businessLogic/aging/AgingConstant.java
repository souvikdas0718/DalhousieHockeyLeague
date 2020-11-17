package dhl.businessLogic.aging;

public enum  AgingConstant {
    NOOFDAYSINYEAR(365),
    LIKELIHOODFORGREATERTHANAVGAGE(80),
    LIKELIHOODFORLESSERTHANAVGAGE (20),
    RANGEDIVISION(3),
    INJUREDDAYSDEFAULTVALUE(-1);

    private final int value;

    AgingConstant(int constantValue) {
        value = constantValue;
    }

    public int getValue() {
        return value;
    }
}
