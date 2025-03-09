# Hexagonal Architecture Example

This is an example project showcasing a simple Java Spring Boot project with and Hexagonal Architecture.

It will use ArchUnit tests to check the validity of the architecture instead of maven modules.

It is separated in these packages:

- **application**: the "entrypoint" of the application.
- **domain**: the domain logic with Api interfaces for the application package, and Spi interfaces for the
  infrastructure package.
- **infrastructure**: adapters.
- **runtime**: additional common configuration and the project runner.

## Domain particularity

The domain package should not contain any technical logic, such as Spring's transactions or bean annotations.

Custom annotations were created to replicate its functionality:

- DomainService
- DomainTransactional

