import pika
import random
import json
import time

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel

QUEUE_NAME = "cities"

class Generator:

    def __init__(self):
        self.cities = []
        self.cities.append({"name": "Aveiro", "district": "Aveiro", "lat": 40.6412, "lon": -8.65362})
        self.cities.append({"name": "Arouca", "district": "Aveiro", "lat": 40.9289, "lon": -8.24364})
        self.cities.append({"name": "Sintra", "district": "Lisboa", "lat": 38.7984, "lon": -9.38811})
        for i in range(len(self.cities)):
            self.cities[i]["id"] = i + 1

    def __generate(self):
        for city in self.cities:
            city["co"] = float(random.randint(0, 1200))  # bppv
            city["co2"] = float(random.randint(38, 420))  # ppm
            city["so2"] = float(random.randint(5, 35))  # micrg/m^3
            city["noise"] = float(random.randint(15, 45))  # db
            city["rainPh"] = float(random.randint(0, 7))

    def clock(self):
        connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
        channel = connection.channel()
        channel.queue_declare(queue=QUEUE_NAME)
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
    generator = Generator()
    generator.clock()


if __name__ == "__main__":
    main()
