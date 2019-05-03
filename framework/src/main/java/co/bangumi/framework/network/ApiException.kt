package co.bangumi.framework.network

import java.io.IOException

class ApiException(override val message: String) : IOException(message)