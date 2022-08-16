# Notes
1. Two types of Synchronization issues are
   1. _Thread interference_: Issues caused by interleaving of operations between two or more threads
   2. _Memory consistency errors_: Issues cased due to happens-before relationship which is simply a guarantee that memory writes by one specific statement are visible to another specific statement. 
2. All the **READ** and **WRITE** Access to mutable object **_MUST_** be synchronized, if that object is shared between multiple threads
3. Acquiring `Intrinsic` lock on object takes care of both the issues mentioned in 1
4. **_Volatile_** variables reduces the risk of memory consistency errors, because any write to a volatile variable establishes a happens-before relationship with subsequent reads of that same variable.
   1. This means that changes to a volatile variable are always visible to other threads.
   2. It also means that when a thread reads a volatile variable, it sees not just the latest change to the volatile, but also the side effects of the code that led up the change.

### Starvation
- This happens when shared resources are made unavailable for long periods by "greedy" threads. 
- For example, suppose an object provides a synchronized method that often takes a long time to return. If one thread invokes this method frequently, other threads that also need frequent synchronized access to the same object will often be blocked.

### Livelock 
- A thread often acts in response to the action of another thread. If the other thread's action is also a response to the action of another thread, then livelock may result. 
- As with deadlock, livelocked threads are unable to make further progress. However, the threads are not blocked — they are simply too busy responding to each other to resume work.

### Guarded Blocks
- Always invoke `wait()` inside a loop that tests for the condition being waited for. Don't assume that the interrupt was for the particular condition you were waiting for, or that the condition is still true

### Producer / Consumer
- [Example](https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html)

# Resource
- [Oracle Docs](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)