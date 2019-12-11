import pika
import random
import json
import time

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel


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
            city["co"] = random.randint(0, 1200)  # bppv
            city["co2"] = random.randint(38, 420)  # ppm
            city["so2"] = random.randint(5, 35)  # micrg/m^3
            city["noise"] = random.randint(15, 45)  # db
            city["rainPh"] = random.randint(0, 7)

    def clock(self):
        while True:
            self.__generate()
            for city in self.cities:
                connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
                channel = connection.channel()
                QUEUE_NAME = str(city["id"])
                channel.queue_declare(queue = QUEUE_NAME)
                message = json.dumps(city)
                channel.basic_publish(exchange='',
                      routing_key=QUEUE_NAME,
                      body=message)
                print("[generator] sent " + message)
                connection.close()
            time.sleep(60)


def main():
    generator = Generator()
    generator.clock()


if __name__ == "__main__":
    main()
