package com.hazelcast.performancetop5.item2.dataserializable;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class OrderLine implements DataSerializable{
    public String product;
    public int amount;

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
