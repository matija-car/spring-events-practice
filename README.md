# Spring Events Practice

A hands-on playground for understanding how Spring's application event system actually works, not just how to wire it up, but *why* it behaves the way it does under the hood (thread behavior, listener ordering, error propagation, and the sharp edges that only show up once you've broken something on purpose).

This is a companion project to [`spring-actuator-practice`](#), built the same way: small, focused commits, one concept at a time, with a real HTTP flow you can hit with curl/Postman and actually observe in the logs.

## Why this exists

Spring Events are one of those topics that look trivial in a tutorial and then quietly cause production bugs (silent async exceptions, listeners firing before a transaction commits, non-deterministic ordering). The goal here was to deliberately trigger those problems in a safe sandbox, see them in the logs, understand *why* they happen, then fix them properly.

## What's inside

The project simulates two simple domains, **orders** and **user registration** — and uses events to decouple the "main" action from side effects that shouldn't block the response.

```
com/springeventspractice
├── config/
│   └── AsyncConfig.java              # enables @Async + custom exception handling for async listeners
├── email/
│   └── EmailService.java             # simulated email sending, kept separate from the listener
├── event/
│   ├── OrderCreatedEvent.java
│   └── UserRegisteredEvent.java
├── listener/
│   ├── InventoryReservationListener.java   # sync, @Order(1)
│   ├── OrderConfirmationListener.java      # sync, @Order(2)
│   ├── AnalyticsListener.java              # async, @Order(3)
│   └── WelcomeEmailListener.java           # async
├── order/
│   ├── Order.java
│   ├── OrderService.java             # publishes OrderCreatedEvent
│   └── OrderController.java
└── user/
    ├── User.java
    ├── UserService.java              # publishes UserRegisteredEvent
    └── UserController.java
```

## Concepts covered

- **Publishing events** with `ApplicationEventPublisher`, using plain POJO events (no need to extend `ApplicationEvent`)
- **Listening to events** with `@EventListener`, and controlling execution order across multiple listeners with `@Order`
- **Synchronous vs. asynchronous listeners**, enabling `@Async` with `@EnableAsync`, and actually proving the difference by logging thread names instead of just trusting the docs
- **Exception behavior**: how a sync listener throwing an exception halts the rest of the chain and bubbles up to the caller, versus an async listener where the exception disappears silently unless you configure an `AsyncUncaughtExceptionHandler`
- **Testing events** with `@RecordApplicationEvents` / `ApplicationEvents`, plus isolated unit tests for listener logic that don't need a full Spring context

## Try it yourself

Start the app, then:

```bash
# create an order — watch the log for listener execution order and thread names
curl -X POST "http://localhost:8080/api/orders?email=test@test.com&amount=150.0"

# trigger the sync exception path (amount > 1000 fails inventory check)
curl -X POST "http://localhost:8080/api/orders?email=test@test.com&amount=1500"

# register a user — triggers an async welcome email listener
curl -X POST "http://localhost:8080/api/users?email=jakov@test.com&username=jakov"
```

The most useful thing to actually watch is the **timestamps and thread names** in the console output — that's what makes the sync/async difference click instead of just being a sentence you memorized.

## Notable things learned along the way

- `@EventListener` methods run **on the same thread** that published the event, in registration/`@Order` sequence, and **block** the caller until they finish.
- `@Async` listeners run on a separate thread pool and the publishing method does **not** wait for them — which also means their exceptions never reach the original caller. That silent failure mode is arguably the most important (and most dangerous) thing to internalize here.
- Without an explicit `AsyncUncaughtExceptionHandler`, a failing async listener just logs a stack trace and moves on — nothing tells you it failed unless you're watching the logs.
- The default `SimpleAsyncTaskExecutor` Spring uses out of the box creates a new thread per call and isn't meant for production — a real app needs a properly configured `ThreadPoolTaskExecutor`.

## Possible next steps

- `@TransactionalEventListener` with a real database (H2/JPA) — listening only after a transaction actually commits, and seeing what happens on rollback
- A configured `ThreadPoolTaskExecutor` instead of the default executor
- Retry / dead-letter handling for failed async listeners

## Stack

Java 17 · Spring Boot · JUnit 5 · AssertJ