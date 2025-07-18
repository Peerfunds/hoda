# hoda


Trigger via Unqork
The process begins with a user action in the Unqork platform, which triggers a request. This request contains key parameters like proxy ID, correlation ID, and member trace ID to initiate downstream processing.

Initial ADR Invocation
ADR (Automated Data Retrieval) microservice receives the Unqork call and starts the sequence by invoking the EPIC system, which is responsible for member encounter data.

Crosswalk System Involvement
Before querying EPIC, the ADR service uses the CROSSWALK system to translate or validate the proxy ID, ensuring the correct identity mapping between systems.

Proxy ID Lookup via Crosswalk
The CROSSWALK system performs a proxy resolution task, mapping and returning the proper EPIC identity reference needed for secure and accurate data retrieval.

Data Preparation in ADR
Once the correct identity is fetched, ADR formats the request payload accordingly and prepares the final data structure to be used by the EPIC system.

EPIC Data Retrieval
The ADR service sends a formatted call to EPIC, which fetches clinical encounter data based on the validated identity and the trace number from the ADR database.

Database Checkpoint
EPIC checks if the data for the given trace number is already present in the ADR database and whether it has been successfully fetched in the past or not.

Database to Unqork Response Loop
After retrieving data, EPIC sends it back to ADR, which then updates the status in the ADR database and sends the data back to Unqork in a structured response format.

Error and Retry Management
If EPIC fails to find encounter data, ADR manages the state via a retry mechanism or logs the error, updating the status for manual review or fallback logic.

Seamless Integration via Spring Boot
The entire architecture is built using Spring Boot microservices. It ensures modular integration with external APIs, handles JSON-based request/response formats, and performs secure communication between systems.
