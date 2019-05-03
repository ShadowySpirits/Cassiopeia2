package co.bangumi.framework.network

import java.io.IOException

class EmptyException(override val message: String) : IOException(message)