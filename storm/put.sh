#!/bin/sh
storm_path=~/apache-storm-0.9.6/
jar_path=target/
${storm_path}bin/storm jar ${jar_path}kobetest-0.0.1.jar TopologyMain config.ini
