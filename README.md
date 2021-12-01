# akka-actors-spring

A small sample project that demonstrates how to use Spring dependency injection with the Akka actor model. By doing so
one is able to leverage inversion of control (IOC) for their test driven design with a _proper_ injection mechanism
that acts as a deterrent against constructor "poisoning" across actor hierarchies (either by means of plain parameters,
implicits or other elaborate constructs such as the cake (anti)pattern).
