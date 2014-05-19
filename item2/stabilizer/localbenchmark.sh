#!/bin/sh

echo ===================================== DATA SERIALIZABLE ======================================

cd workdir-dataserializable

for i in {1..5}
do
    ./static-run.sh
done

echo ===================================== IDENTIFIED SERIALIZABLE ======================================

cd workdir-identifieddataserializable

for i in {1..5}
do
    ./static-run.sh
done

echo ===================================== SERIALIZABLE ======================================

cd workdir-identifieddataserializable

for i in {1..5}
do
    ./static-run.sh
done