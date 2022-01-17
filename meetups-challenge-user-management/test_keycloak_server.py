import unittest
from keycloak import KeycloakOpenID
from keycloak.exceptions import KeycloakAuthenticationError, KeycloakConnectionError

class TestKeycloakServer(unittest.TestCase):

    def __init__(self, *args, **kwargs):
        super(TestKeycloakServer, self).__init__(*args, **kwargs)
        self.__client = KeycloakOpenID(
            server_url="http://localhost:8082/auth/",
            client_id="java-meetups-challenge-api-public", 
            realm_name="MeetupsChallenge", 
            verify=False)

        self.checkConnection()

    def checkConnection(self):
        try:
            self.__client.well_know()
            self.__conn_successful = True
        except KeycloakConnectionError:
            self.__conn_successful = False

    def setUp(self):
        if not self.__conn_successful:
            print('\n[TEST SKIPPED] Failed to establish a new connection. Make sure that the keycloak server is running on http://localhost:8082. Check out README.md instructions')
            self.skipTest(None)

    def test_get_token_ok(self):
        token = self.__client.token("arturo.chari", "1234567890")
        self.assertIn("access_token", token)

    def test_get_token_failed(self):
        with self.assertRaises(KeycloakAuthenticationError):
            self.__client.token("unknown-username", "123456")

if __name__ == '__main__':
    unittest.main(warnings='ignore', verbosity=0)