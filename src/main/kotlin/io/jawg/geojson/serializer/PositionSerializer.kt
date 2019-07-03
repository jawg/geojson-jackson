package io.jawg.geojson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.jawg.geojson.Position
import java.text.DecimalFormat

internal class PositionDeserializer : JsonDeserializer<Position>() {

  override fun deserialize(p: JsonParser, ctx: DeserializationContext): Position {
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(Position::class.java, p.currentToken, p, "Unable to deserialize position: no array found")
    }

    val lng = nextDouble(p, ctx)
    if (lng == null) {
      ctx.handleUnexpectedToken(Position::class.java, p.currentToken, p, "Unable to deserialize position: Lng not found")
      throw IllegalStateException("Lng is null")
    }

    val lat = nextDouble(p, ctx)
    if (lat == null) {
      ctx.handleUnexpectedToken(Position::class.java, p.currentToken, p, "Unable to deserialize position: Lat not found")
      throw IllegalStateException("Lat is null")
    }

    val alt = nextDouble(p, ctx)

    return Position(
        lng = lng,
        lat = lat,
        alt = alt
    )
  }

  private fun nextDouble(parser: JsonParser, ctx: DeserializationContext): Double? {
    val token = parser.nextToken()
    return when (token) {
      JsonToken.VALUE_NUMBER_INT -> parser.longValue.toDouble()
      JsonToken.VALUE_NUMBER_FLOAT -> parser.doubleValue
      JsonToken.VALUE_STRING -> parser.valueAsDouble
      JsonToken.END_ARRAY -> null
      null -> {
        ctx.handleUnexpectedToken(Position::class.java, token, parser, "Unexpected null token")
        null
      }
      else -> {
        ctx.handleUnexpectedToken(Position::class.java, token, parser, "Unexpected token ${token.name}")
        null
      }
    }
  }
}

internal class PositionSerializer : JsonSerializer<Position>() {

  private val latLngFormatter = DecimalFormat().apply {
    // Round to centimeter precision which is around 7 digit for lat lng, this is what OSM does
    maximumFractionDigits = 7
    minimumFractionDigits = 1
  }
  private val altFormatter = DecimalFormat().apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 1
  }


  override fun serialize(value: Position, gen: JsonGenerator, serializers: SerializerProvider) {
    gen.writeStartArray()
    gen.writeRawValue(latLngFormatter.format(value.lng))
    gen.writeRawValue(latLngFormatter.format(value.lat))
    if (value.alt != null) {
      gen.writeRawValue(altFormatter.format(value.alt))
    }
    gen.writeEndArray()
  }

}