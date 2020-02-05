import pika
import random
import json
import time

import os

connection = pika.BlockingConnection(pika.ConnectionParameters('deti-engsoft-09.ua.pt'))
channel = connection.channel

QUEUE_NAME = "cities"

class Generator:

    def __init__(self, file):
        __location__ = os.path.realpath(
        os.path.join(os.getcwd(), os.path.dirname(__file__)))
        file = os.path.join(__location__, file)
        f = open(file, "r")
        self.cities = json.loads(f.read())["cities"]
        f.close()
        for i in range(len(self.cities)):
            self.cities[i]["id"] = i + 1

    def __generate(self):
        for city in self.cities:
            city["co"] = float(random.randint(0, 1200))  # bppv
            city["co2"] = float(random.randint(38, 420))  # ppm
            city["so2"] = float(random.randint(5, 35))  # micrg/m^3
            city["no2"] = float(random.randint(0, 80)) #mol/m^2
            city["o3"] = float(random.randint(11, 86)) # ppbv
            city["noise"] = float(random.randint(15, 45))  # db
            city["rainPh"] = float(random.randint(0, 7))
            city["temperature"] = float(random.randint(-10, 40)) # celcius deg

    def clock(self):
        connection = pika.BlockingConnection(pika.ConnectionParameters('deti-engsoft-09.ua.pt'))
        channel = connection.channel()
        channel.queue_declare(queue=QUEUE_NAME, durable=True)
        while True:
            self.__generate()
            for city in self.cities:
                message = json.dumps(city)
                channel.basic_publish(exchange='',
                      routing_key=QUEUE_NAME,
                      body=message)
                print("[generator] sent " + message)
                time.sleep(20)
        connection.close()


def main():
    generator = Generator("cities.json")
    generator.clock()


if __name__ == "__main__":
    main()
