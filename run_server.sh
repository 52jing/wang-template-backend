#!/usr/bin/env bash

java -jar app.jar --spring.profiles.active=$PROFILE $*
