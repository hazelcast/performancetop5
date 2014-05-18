package com.hazelcast.performancetop5.item2.identifieddataserializable;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order implements IdentifiedDataSerializable {

    public long orderId;
    public Date date;
    public List<OrderLine> orderLines = new LinkedList<OrderLine>();

    @Override
    public int getFactoryId() {
        return OrderDataSerializableFactory.ID;
    }

    @Override
    public int getId() {
        return OrderDataSerializableFactory.ORDER_TYPE;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeLong(orderId);
        out.writeObject(date);
        out.writeInt(orderLines.size());
        for(OrderLine line: orderLines){
            line.writeData(out);
        }
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        orderId = in.readLong();
        date = in.readObject();
        int orderCount = in.readInt();
        for(int k=0;k<orderCount;k++){
            OrderLine line = new OrderLine();
            line.readData(in);
            orderLines.add(line);
        }
    }
}
