package dhl.importJsonTest;

import dhl.importJsonTest.mocks.SerializedJsonMock;

public abstract class ImportJsonTestAbstractFactory {

    private static ImportJsonTestAbstractFactory uniqueInstance = null;

    protected ImportJsonTestAbstractFactory() {

    }

    public static ImportJsonTestAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new ImportJsonTestMockFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(ImportJsonTestAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract SerializedJsonMock createSerializedJsonMock();
}
