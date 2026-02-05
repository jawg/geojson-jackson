package io.jawg.geojson.serializer

import io.jawg.geojson.Position
import tools.jackson.core.JsonGenerator
import tools.jackson.core.JsonParser
import tools.jackson.core.JsonToken
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.SerializationContext
import tools.jackson.databind.deser.std.StdDeserializer
import tools.jackson.databind.ser.std.StdSerializer

internal class PositionDeserializer : StdDeserializer<Position>(Position::class.java) {

  override fun deserialize(p: JsonParser, ctx: DeserializationContext): Position {
    val targetType = ctx.constructType(Position::class.java)
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize position: no array found")
    }

    val lng = nextDouble(p, ctx)
    if (lng == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize position: Lng not found")
      throw IllegalStateException("Lng is null")
    }

    val lat = nextDouble(p, ctx)
    if (lat == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize position: Lat not found")
      throw IllegalStateException("Lat is null")
    }

    val alt = nextDouble(p, ctx)

    // Go to the end of the array ignoring anything after the third coordinate
    while (p.currentToken() != JsonToken.END_ARRAY) { p.nextToken() }

    return Position(
      lng = lng,
      lat = lat,
      alt = alt
    )
  }

  private fun nextDouble(parser: JsonParser, ctx: DeserializationContext): Double? {
    val targetType = ctx.constructType(Position::class.java)
    return when (val token = parser.nextToken()) {
      JsonToken.VALUE_NUMBER_INT -> parser.longValue.toDouble()
      JsonToken.VALUE_NUMBER_FLOAT -> parser.doubleValue
      JsonToken.VALUE_STRING -> parser.valueAsDouble
      JsonToken.END_ARRAY -> null
      null -> {
        ctx.handleUnexpectedToken(targetType, token, parser, "Unexpected null token")
        null
      }
      else -> {
        ctx.handleUnexpectedToken(targetType, token, parser, "Unexpected token ${token.name}")
        null
      }
    }
  }
}

internal class PositionSerializer : StdSerializer<Position>(Position::class.java) {

  override fun serialize(
    value: Position,
    gen: JsonGenerator,
    provider: SerializationContext,
  ) {
    gen.writeStartArray()
    gen.writeNumber(value.lng)
    gen.writeNumber(value.lat)
    if (value.alt != null) {
      gen.writeNumber(value.alt)
    }
    gen.writeEndArray()
  }

}