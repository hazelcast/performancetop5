#!/bin/sh
mvn clean install
java -jar target/microbenchmarks.jar -tu s -f 2 -wi 2 -i 5 -v EXTRA "org.sample.LocalMapPutSetBenchmark.*"
#java -jar target/microbenchmarks.jar -tu s -f 2 -wi 2 -i 5  "com.hazelcast.performancetop5.item1.DistributedMapPutSetBenchmark.*"

