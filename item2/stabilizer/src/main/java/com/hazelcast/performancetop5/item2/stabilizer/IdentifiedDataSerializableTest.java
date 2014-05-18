package com.hazelcast.performancetop5.item2.stabilizer;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.performancetop5.item2.identifieddataserializable.Order;
import com.hazelcast.performancetop5.item2.identifieddataserializable.OrderLine;
import com.hazelcast.stabilizer.tests.TestContext;
import com.hazelcast.stabilizer.tests.TestRunner;
import com.hazelcast.stabilizer.tests.annotations.Performance;
import com.hazelcast.stabilizer.tests.annotations.Run;
import com.hazelcast.stabilizer.tests.annotations.Setup;
import com.hazelcast.stabilizer.tests.annotations.Teardown;
import com.hazelcast.stabilizer.tests.utils.ThreadSpawner;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class IdentifiedDataSerializableTest {
    private final static ILogger log = Logger.getLogger(IdentifiedDataSerializableTest.class);

    private final AtomicLong operations = new AtomicLong();
    private String[] products;

    //props
    public int threadCount = 10;
    public int logFrequency = 10000;
    public int performanceUpdateFrequency = 100;
    public int maxOrders = 100 * 1000;
    public int maxOrderLines = 5;

    private IMap<Object, Object> orderMap;
    private TestContext testContext;

    @Setup
    public void setup(TestContext testContext) throws Exception {
        this.testContext = testContext;
        HazelcastInstance targetInstance = testContext.getTargetInstance();

        orderMap = targetInstance.getMap("orders");
        products = new String[100];
        for (int k = 0; k < 100; k++) {
            products[k] = "product-" + k;
        }
    }

    @Run
    public void run() {
        ThreadSpawner spawner = new ThreadSpawner();
        for (int k = 0; k < threadCount; k++) {
            spawner.spawn(new Worker());
        }
        spawner.awaitCompletion();
    }

    @Teardown
    public void teardown() throws Exception {
        orderMap.destroy();
    }

    @Performance
    public long getOperationCount() {
        return operations.get();
    }

    private class Worker implements Runnable {
        Random random = new Random();

        @Override
        public void run() {
            long iteration = 0;
            while (!testContext.isStopped()) {
                Order order = createNewOrder();
                orderMap.set(order.orderId, order);

                if (iteration % logFrequency == 0) {
                    log.info(Thread.currentThread().getName() + " At iteration: " + iteration);
                }

                if (iteration % performanceUpdateFrequency == 0) {
                    operations.addAndGet(performanceUpdateFrequency);
                }
                iteration++;
            }
        }

        private Order createNewOrder() {
            Order order = new Order();
            order.orderId = random.nextInt(maxOrders);
            order.date = new Date();

            int orderlineCount = random.nextInt(maxOrderLines);
            for (int k = 0; k < orderlineCount; k++) {
                OrderLine orderLine = new OrderLine();
                orderLine.amount = random.nextInt(100);
                orderLine.product = products[random.nextInt(products.length)];
                order.orderLines.add(orderLine);
            }

            return order;
        }
    }

    public static void main(String[] args) throws Throwable {
        IdentifiedDataSerializableTest test = new IdentifiedDataSerializableTest();
        new TestRunner(test).run();
    }
}
