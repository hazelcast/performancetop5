package com.hazelcast.performancetop5.item2.dataserializable;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order implements DataSerializable {

    public long orderId;
    public Date date;
    public List<OrderLine> orderLines = new LinkedList<OrderLine>();

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
