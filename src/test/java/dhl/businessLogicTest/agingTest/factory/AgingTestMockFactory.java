package dhl.businessLogicTest.agingTest.factory;

import dhl.businessLogicTest.agingTest.mocks.AgingMock;

public class AgingTestMockFactory extends AgingTestAbstractFactory {
    public AgingMock createAgingMock() {
        return new AgingMock();
    }

}
