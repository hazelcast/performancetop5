#!/bin/bash

provisioner --scale 4

coordinator     --workerVmOptions "-ea -server -Xms2G -Xmx2G -XX:+PrintGC -XX:+HeapDumpOnOutOfMemoryError" \
                --clientHzFile      client-hazelcast.xml \
                --hzFile            hazelcast.xml \
                --clientWorkerCount 0 \
                --memberWorkerCount 4 \
                --workerClassPath   '../target/*.jar' \
                --duration          5m \
                --monitorPerformance   \
                test.properties

provisioner --download

provisioner --terminate

