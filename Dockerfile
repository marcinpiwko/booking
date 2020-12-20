FROM
ADD target/booking-1.0.jar booking-1.0.jar
EXPOSE 8900
ENTRYPOINT ["java", "-jar", "booking-1.0.jar"]