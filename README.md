# network-algorithms-cmmdc
university/network-algorithms/cmmdc

## In order to build you need to have installed

  * Java 8
  * DockerToolbox
    
## To build and run    
   * './gradlew clean build' - to compile everything.
   
   * 'docker build --tag cmmdc/tower-control:0.1.0 ./tower-control --rm=true' 
   -to build the docker images for tower-controll 
   
   * 'docker build --tag cmmdc/worker-node:0.1.0 ./worker-node --rm=true' 
   -to build the docker images for worker-node
     It is not required to publish the docker image.
   
   * 'docker-compose up' - to run the cluster

Check the results http://{dockerip}:8080/   usually http://192.168.99.100:8080/

## some screenshots

![console](./results/docker-compose.png)

![web](./results/web.png)

As you can observe the GCD for [72, 114, 60, 96, 84, 102] is 6.


