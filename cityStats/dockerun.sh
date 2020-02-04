#!/bin/bash
if [[ $(/usr/bin/id -u) -ne 0 ]]; then
    echo "Not running as root"
    exit
fi

clean_install=false;
daemon=false;
invalid_option=false;

while getopts id option
do
  case "${option}" in
  i) clean_install=true;;
  d) daemon=true;;
  *) invalid_option=true;;
esac
done

if $invalid_option ; then
  exit
fi
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

