#!/usr/bin/env bash
echo "------Start Parse------"

url=$1

java -jar StyleParser-1.0-SNAPSHOT-jar-with-dependencies.jar ${url}

echo "------Parse Complete------"
