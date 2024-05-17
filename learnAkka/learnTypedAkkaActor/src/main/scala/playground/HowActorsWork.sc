/*
Akka has thread pools for actors, and the default thread pool is fork-join pool.
The fork-join pool is a special thread pool that is designed to work with the fork-join framework.

The fork-join framework is a framework that is designed to work with divide-and-conquer algorithms.
It is a framework that is designed to work with algorithms that can be broken down into smaller tasks that can be executed concurrently.

Actor has
  - state
  - behavior
  - mailbox
  - dispatcher

Akka essentials
1. Introduction to actor
2. How actor works
3. Managing actor state
4. Child actor


Actor organization
   1. Actor has  tree like hierarchy
   2. actor paths
   3. root guardian, system gurardian and user guardian


Watching actors -
  if watching actors dies then watcher actor will receive Terminated message
   actorRef is pointer to Actor



 */