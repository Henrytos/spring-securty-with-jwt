# spring-securty-with-jwt

## generate private key

``bash
openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
``
## generate public key

``bash
 openssl rsa -pubout -in private.pem -out public.pem
 ``
