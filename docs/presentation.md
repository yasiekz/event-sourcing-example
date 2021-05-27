# Presentation :D

### What is event sourcing

Event sourcing is a way to create aggregates where we don't save actual aggregate state. There are built from domain 
events and snapshots stored in event store. (which mostly is no-sql database)

### Benefits

- Events are natural audit log
- Event sourcing does not rely on database's pessimistic locks. Events are immutable, so there is no need to acquire 
  any lock while saving new event to database 

### Disadvantages

- More complicated code
- Generating reports which requires current aggregate state is much more tricky
- More complicated migration

### When use event sourcing?

Event sourcing might be used when we need powerful audit log and accessing current aggregate state is not common thing,
or there is another mechanism which provide such functionality for querying (like CQRS). Good examples might be:
- payments
- account balance

### When not use event sourcing?

In every other case :D If we don't need the audit log, disadvantages probably will blow up entire project
