package dhl.businessLogicTest.AgingTest.factory;

import dhl.businessLogicTest.AgingTest.mocks.AgingMock;

public class AgingTestMockFactory extends AgingTestAbstractFactory {
    public AgingMock createAgingMock() {
        return new AgingMock();
    }

}
