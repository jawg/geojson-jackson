package io.jawg.geojson.serializer

import tools.jackson.core.JsonParser
import tools.jackson.core.JsonToken

internal fun JsonParser.nextDoubleOrNull(): Double? {
  return when (this.nextToken()) {
    JsonToken.VALUE_NUMBER_INT -> this.longValue.toDouble()
    JsonToken.VALUE_NUMBER_FLOAT -> this.doubleValue
    JsonToken.VALUE_STRING -> this.valueAsDouble
    JsonToken.END_ARRAY -> null
    else -> {
      null
    }
  }
}
