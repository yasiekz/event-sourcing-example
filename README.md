# Event Sourcing example

## Overview

This project has been created to internal presentation at [ZEN](http://zen.com) company. It shows
one of the approaches how to make event sourced aggregates.

### What problem it resolves?

It resolves the problem with payments in financial institution. Payment can be:
- created, after creation payment is blocked in internal booking system
- resolved by some AML service with statuses IN (REJECT, ACCEPT)
- booked in PSP
- manually corrected by financial institution employee

### How it works

- Events are created inside aggregate root
- They are stored by event store (with MongoDB implementation)
- Current payment state is not stored in event store
- Payments are retrieved only by reading events
- To speed up performance, snapshots are created every one 30 seconds 



