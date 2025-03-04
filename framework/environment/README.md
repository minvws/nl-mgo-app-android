# Feature - Framework - Copy

This module provides classes that handle environment related things. The app currently supports 
three environments:

- **Test**: Talks to the backend test servers.
- **Acc**: Talks to the backend acceptance servers.
- **Prod**: Talks to the backend production servers.

## Highlighted

- **DefaultEnvironmentRepository**: Use this repository to determine in which environment the app 
  is currently running.
