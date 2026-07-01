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
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize position: no array found")
    }

    val lng = p.nextDoubleOrNull()
    if (lng == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize position: Lng not found")
      error("Lng is null")
    }

    val lat = p.nextDoubleOrNull()
    if (lat == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize position: Lat not found")
      error("Lat is null")
    }

    val alt = p.nextDoubleOrNull()

    // Go to the end of the array ignoring anything after the third coordinate
    while (p.currentToken() != JsonToken.END_ARRAY) {
      p.nextToken()
    }

    return Position(
      lng = lng,
      lat = lat,
      alt = alt
    )
  }

}

internal class PositionSerializer : StdSerializer<Position>(Position::class.java) {

  override fun serialize(value: Position, gen: JsonGenerator, ctxt: SerializationContext) {
    gen.writeStartArray()
    gen.writeNumber(value.lng)
    gen.writeNumber(value.lat)
    if (value.alt != null) {
      gen.writeNumber(value.alt)
    }
    gen.writeEndArray()
  }

}
