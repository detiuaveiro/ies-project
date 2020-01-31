#!/bin/bash

while getopts i option
do
  case "${option}" in
  i) mvn clean install;;
esac
done

sudo docker-compose down;
sudo docker image rm citystats_citystats;
sudo docker-compose up -d;
