# Pastry Shop – Client/Server Desktop Application

A desktop application for managing a pastry shop, made as a seminar project for the course *Software Design* (Projektovanje softvera) at FON.

Pastry chefs log in, manage customers, pastries and shifts, and create and process customer orders. The application is built as a client/server system with a shared module.

## Modules

| Folder | Description |
|---|---|
| `SEMINARSKI_KLIJENT` | Client application: Swing user interface, controllers and communication with the server |
| `SEMINARSKI_SERVER` | Server application: handles client requests in threads, system operations, database access |
| `SEMINARSKI_ZAJEDNICKI` | Shared module: domain classes and the request/response objects used by both sides |

## Features

- Login of a pastry chef
- Customers: add, view, edit and delete; add a new place (city)
- Pastries: add, view, edit and delete
- Shifts: add a shift and view all shifts
- Orders: create an order with multiple items, search and view orders, view order items, edit an order (mark it as processed)
- Edit the personal data of the logged-in pastry chef
- Server window with start/stop of the server and a database configuration form

## Domain model

`Poslasticar` (pastry chef), `Kupac` (customer), `Mesto` (place), `Poslastica` (pastry), `Porudzbina` (order), `StavkaPorudzbina` (order item), `Smena` (shift), `PoslasticarSmena` (pastry chef's shift).

## Architecture

- **Client:** forms, controllers and a coordinator (`Cordinator`) that opens the forms; communication with the server through `Komunikacija`.
- **Communication:** the client sends a `Zahtev` (request) with an `Operacija`, the server returns an `Odgovor` (response), over a TCP socket (default port `9000`).
- **Server:** every client is served by its own thread; each use case is a system operation (`...SO`) built on an abstract generic operation; a generic repository does the database work through JDBC.
- **Shared module:** all domain objects implement a common interface `ApstraktniDomenskiObjekat`, which lets the generic repository build SQL for any of them.

## Tech stack

- Java 21, Java Swing (NetBeans GUI builder)
- JDBC, MySQL 8 (MySQL Connector/J 8.0.19)
- Sockets, multithreading
- Apache Ant (NetBeans projects)

## How to run

1. Create a MySQL database named `seminarski` and import the SQL script.
2. Open all three projects in NetBeans. In the client and server projects, make sure the libraries are set correctly: `SEMINARSKI_ZAJEDNICKI.jar` (build the shared project first) and `mysql-connector-java-8.0.19.jar`.
3. Check the database settings in `SEMINARSKI_SERVER/config/config.properties` (url, username, password, port).
4. Run the server and click **pokreni server**.
5. Run the client and log in with a pastry chef account from the database.

## Author

Katarina Urošević
