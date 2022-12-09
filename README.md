# Explore common image matching algorithm in opencv

## Setup 

Gradle project
[Openpnp opencv java binding](https://github.com/openpnp/opencv)
To add this dependency in gradle build in kotlin ds: 
`implementation("org.openpnp:opencv:4.5.5-1")`

## Requirements
- Average accuracy 
- Scale invariant 
- Able to match under different lighting conditions, and different angles
- Relatively fast

## Aim 
- An engine for later project with AR Core in Android 

## Algorithm 

1. Compute, normalize, and then compare Histogram (both BGR and Grayscale after conversion)
2. Feature detector (eg: SIFT, ORB) + FLANN matcher
3. Dense vector representation

## Current result
- Histogram comparison is simple and fast yet the accuracy is low under different lightings thus grayscale conversion definitely improves this. 
- SIFT feature detector + FLANN matcher offers sufficient accuracy and speed. We can use the number of good matches as a threshold to determine if two images match. 
It may be better to edit the image to get important objects, features first depending on the purposes. 


