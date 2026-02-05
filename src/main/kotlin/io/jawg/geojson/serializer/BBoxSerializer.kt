package io.jawg.geojson.serializer

import io.jawg.geojson.BBox
import tools.jackson.core.JsonGenerator
import tools.jackson.core.JsonParser
import tools.jackson.core.JsonToken
import tools.jackson.databind.DeserializationContext
import tools.jackson.databind.SerializationContext
import tools.jackson.databind.deser.std.StdDeserializer
import tools.jackson.databind.ser.std.StdSerializer

internal class BBoxDeserializer : StdDeserializer<BBox>(BBox::class.java) {

  override fun deserialize(p: JsonParser, ctx: DeserializationContext): BBox {
    val targetType = ctx.constructType(BBox::class.java)
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize bbox: no array found")
    }

    val west = nextDouble(p, ctx)
    if (west == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize bbox: west not found")
      throw IllegalStateException("West is null")
    }

    val south = nextDouble(p, ctx)
    if (south == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize bbox: south not found")
      throw IllegalStateException("south is null")
    }

    val east = nextDouble(p, ctx)
    if (east == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize bbox: east not found")
      throw IllegalStateException("east is null")
    }

    val north = nextDouble(p, ctx)
    if (north == null) {
      ctx.handleUnexpectedToken(targetType, p.currentToken(), p, "Unable to deserialize bbox: north not found")
      throw IllegalStateException("north is null")
    }

    // Go to the end of the array ignoring anything after the north coordinate
    while (p.currentToken() != JsonToken.END_ARRAY) { p.nextToken() }

    return BBox(
      west = west,
      east = east,
      north = north,
      south = south
    )
  }

  private fun nextDouble(parser: JsonParser, ctx: DeserializationContext): Double? {
    val targetType = ctx.constructType(BBox::class.java)
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

internal class BBoxSerializer : StdSerializer<BBox>(BBox::class.java) {

  override fun serialize(
    value: BBox,
    gen: JsonGenerator,
    provider: SerializationContext
  ) {
    gen.writeStartArray()
    gen.writeNumber(value.west)
    gen.writeNumber(value.south)
    gen.writeNumber(value.east)
    gen.writeNumber(value.north)
    gen.writeEndArray()
  }

}
