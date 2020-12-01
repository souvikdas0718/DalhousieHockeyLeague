package dhl.importJsonTest;

import dhl.importJsonTest.mocks.SerializedJsonMock;

public class ImportJsonTestMockFactory extends ImportJsonTestAbstractFactory {

    public SerializedJsonMock createSerializedJsonMock() {
        return new SerializedJsonMock();
    }
}
