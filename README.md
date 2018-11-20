# kafkastreams

## Initial Setup

### build kafka docker image
Download kafka docker image 
```
git clone https://github.com/wurstmeister/kafka-docker.git
```

### Update docker compose yml file

docker-compose-single-broker.yml
```
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    build: .
    ports:
      - "9092:9092"
    environment:
      KAFKA_LISTENERS: PLAINTEXT://:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      KAFKA_CREATE_TOPICS: "test:1:1"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

```

### Update /etc/hosts
This required to run kafka in docker env with single broker on mac
```
127.0.0.1 kafka
127.0.0.1 zookeeper
```

### Build image
```
docker-compose -f docker-compose-single-broker.yml up -d
```

## Application

### TextProducer
Generate text contains words separated by spaces and send it to kafka topic
### TextConsumer
Reads kafka topic
### WordCount
Count words on the fly and produce output to the kafka topic