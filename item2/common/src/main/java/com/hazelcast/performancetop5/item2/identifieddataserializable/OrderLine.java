package com.hazelcast.performancetop5.item2.identifieddataserializable;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;

public class OrderLine implements IdentifiedDataSerializable {
    public String product;
    public int amount;

    @Override
    public int getFactoryId() {
        return OrderDataSerializableFactory.ID;
    }

    @Override
    public int getId() {
        return OrderDataSerializableFactory.ORDERLINE_TYPE;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(product);
        out.writeInt(amount);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        product = in.readUTF();
        amount = in.readInt();
    }
}
