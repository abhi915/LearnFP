/*
Reactive stream is Service provider interface
1. Publisher - emits data
2. Subscriber - consumes data
3.processor - transforms data
4. async
5. backpressure


Akka stream
- source > publisher
- sink > subscriber
- flow > processor

Akka stream is based on reactive stream and actor.


Materialization:
--> A graph is blue print of stream
--> Materialization is the process of creating a running stream from a graph
   --> resource - actors, threadpool,

materializing graph means materializing all component.
the graph produces single materialized value.
our job which one to pick.



 */