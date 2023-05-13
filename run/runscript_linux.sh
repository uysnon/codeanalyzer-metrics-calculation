#!/bin/sh
terminal --noclose -e java -jar codeanalyzer-auth/target/codeanalyzer-auth-exec.jar
terminal --noclose -e java -jar online-calculation/target/metrics-online-calculation-exec.jar