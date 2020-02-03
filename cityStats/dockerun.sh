#!/bin/bash

clean_install=false;
daemon=false;

while getopts id option
do
  case "${option}" in
  i) clean_install=true;;
  d) daemon=true;;
  *) echo Invalid option!;;
esac
done

if $clean_install ; then
  mvn clean install;
fi
sudo docker-compose down;
sudo docker image rm citystats_citystats;
if $daemon ; then
  sudo docker-compose up -d;
else
  sudo docker-compose up;
fi

