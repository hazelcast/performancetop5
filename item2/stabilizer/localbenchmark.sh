#!/bin/sh

ITERATIONS=5

echo ===================================== DATA SERIALIZABLE ======================================

cd workdir-dataserializable

for i in {1..$ITERATIONS}
do
    ./static-run.sh
done

cd ..

echo ===================================== IDENTIFIED SERIALIZABLE ======================================

cd workdir-identifieddataserializable

for i in {1..$ITERATIONS}
do
    ./static-run.sh
done

cd ..

echo ===================================== SERIALIZABLE ======================================

cd workdir-serializable

for i in {1..$ITERATIONS}
do
    ./static-run.sh
done

cd ..