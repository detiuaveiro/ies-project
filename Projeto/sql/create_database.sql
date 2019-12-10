create table cities(
    id bigint not null, name varchar(32) not null,
    district varchar(32) not null, lat float(25) not null,
    lon float(25) not null,
    co float(25),
    co2 float(25),
    so2 float(25),
    no2 float(25),
    o3 float(25),
    noise float(25),
    rain_ph float(25),
    temperature float(25),
    primary key(id),
    unique(name, district),
    unique(lat, lon),
    unique(name, district, lat, lon)
);

create table stats (id bigint not null,
   name varchar(32) not null,
   district varchar(32) not null,
   lat float(25) not null,
   lon float(25) not null,
   co float(25),
   co2 float(25),
   so2 float(25),
   no2 float(25),
   o3 float(25),
   noise float(25),
   rainPh float(25),
   temperature float(25),
   primary key(id),
   unique(name, district),
   unique(lat, lon),
   unique(name, district, lat, lon)
);

