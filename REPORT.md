### Password Storage
hash(username, password)

### Password Transport
We assume the client / server communicate over a TLS (or TCP) network, 
and we therefore do not need to take into account confidentiality.

### Password Verification
Since the server receives the username and password in the exact same format as they are stored, 
a simple "equal" comparison is enough to determine the authentication of the client. 
(Also since we assume that the hash function is non-determinable)