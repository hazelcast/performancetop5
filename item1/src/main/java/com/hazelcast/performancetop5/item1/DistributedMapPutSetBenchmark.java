package com.hazelcast.performancetop5.item1;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.Random;

@State(value = Scope.Thread)
@OperationsPerInvocation(DistributedMapPutSetBenchmark.OPERATIONS_PER_INVOCATION)
public class DistributedMapPutSetBenchmark {

    public static final int OPERATIONS_PER_INVOCATION = 50000;

    private HazelcastInstance hz;
    private IMap<Integer, String> map;
    private Integer[] keys;
    private String[] values;

    @Setup
    public void setUp() {
        MapConfig mapConfig = new MapConfig("somemap");
        mapConfig.setAsyncBackupCount(0);
        mapConfig.setBackupCount(0);
        mapConfig.setStatisticsEnabled(false);

        Config config = new Config();
        //config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        //config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        //config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("192.168.1.107");
        //config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1");
        config.addMapConfig(mapConfig);

        hz = Hazelcast.newHazelcastInstance(config);
        Hazelcast.newHazelcastInstance(config);
        Hazelcast.newHazelcastInstance(config);
        Hazelcast.newHazelcastInstance(config);
        Hazelcast.newHazelcastInstance(config);

        map = hz.getMap(mapConfig.getName());

        int size = 5000;
        keys = new Integer[size];
        values = new String[size];

        for (int k = 0; k < keys.length; k++) {
            keys[k] = k;
            values[k] = randomString(500);
            map.put(keys[k], values[k]);
        }
    }

    private String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int k = 0; k < length; k++) {
            char c = (char) random.nextInt(Character.MAX_VALUE);
            sb.append(c);
        }
        return sb.toString();
    }

    @TearDown
    public void tearDown() {
        Hazelcast.shutdownAll();
    }

    @GenerateMicroBenchmark
    public void putPerformance() {
        Random random = new Random();
        for (int k = 0; k < OPERATIONS_PER_INVOCATION; k++) {
            Integer key = keys[random.nextInt(keys.length)];
            String value = values[k % values.length];
            map.put(key, value);
        }
    }

    @GenerateMicroBenchmark
    public void setPerformance() {
        Random random = new Random();
        for (int k = 0; k < OPERATIONS_PER_INVOCATION; k++) {
            Integer key = keys[random.nextInt(keys.length)];
            String value = values[k % values.length];
            map.set(key, value);
        }
    }
}
