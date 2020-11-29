package dhl.businessLogicTest.agingTest.factory;

import dhl.businessLogicTest.agingTest.mocks.AgingMock;


public abstract class AgingTestAbstractFactory {

    private static AgingTestAbstractFactory uniqueInstance = null;

    protected AgingTestAbstractFactory() {

    }

    public static AgingTestAbstractFactory instance() {
        if (null == uniqueInstance) {
            uniqueInstance = new AgingTestMockFactory();
        }
        return uniqueInstance;
    }

    public static void setFactory(AgingTestAbstractFactory factory) {
        uniqueInstance = factory;
    }

    public abstract AgingMock createAgingMock();


}
