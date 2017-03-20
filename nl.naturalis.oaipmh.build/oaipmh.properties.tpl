# Configuration file for the OAI-PMH REST service

# The base URL under which the REST service runs.
# If you do not set this property, the base URL
# will be retrieved from the Wildfly container.
# However, when using a load balancer, Wildfly
# will report another base URL to the application
# than the client uses and sees. Since the base
# URL is also displayed within the OAI-PMH XML
# document, this discrepancy causes the XML to
# be invalid. So when using a load balancer, you
# MUST set this property. You must include the
# context root in the base URL (the invariable path
# segment after the host/port).
# Example: http://example.com/oaipmh

baseUrl=
