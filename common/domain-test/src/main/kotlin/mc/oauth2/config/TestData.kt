package mc.oauth2.config

import mc.oauth2.Credentials
import mc.oauth2.Principal


const val TEST_USERNAME = "my-principal"
const val TEST_PASSWORD = "my-secret"

val TEST_PRINCIPAL = Principal.valueOf(TEST_USERNAME)
val TEST_CREDENTIALS = Credentials.valueOf(TEST_PASSWORD)


class TestData
