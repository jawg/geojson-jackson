package io.jawg.geojson.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.jawg.geojson.BBox

internal class BBoxDeserializer : JsonDeserializer<BBox>() {

  override fun deserialize(p: JsonParser, ctx: DeserializationContext): BBox {
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(BBox::class.java, p.currentToken, p, "Unable to deserialize bbox: no array found")
    }

    val west = nextDouble(p, ctx)
    if (west == null) {
      ctx.handleUnexpectedToken(BBox::class.java, p.currentToken, p, "Unable to deserialize bbox: west not found")
      throw IllegalStateException("West is null")
    }

    val south = nextDouble(p, ctx)
    if (south == null) {
      ctx.handleUnexpectedToken(BBox::class.java, p.currentToken, p, "Unable to deserialize bbox: south not found")
      throw IllegalStateException("south is null")
    }

    val east = nextDouble(p, ctx)
    if (east == null) {
      ctx.handleUnexpectedToken(BBox::class.java, p.currentToken, p, "Unable to deserialize bbox: east not found")
      throw IllegalStateException("east is null")
    }

    val north = nextDouble(p, ctx)
    if (north == null) {
      ctx.handleUnexpectedToken(BBox::class.java, p.currentToken, p, "Unable to deserialize bbox: north not found")
      throw IllegalStateException("north is null")
    }

    // Go to the end of the array ignoring anything after the north coordinate
    while (p.currentToken != JsonToken.END_ARRAY) { p.nextToken() }

    return BBox(
      west = west,
      east = east,
      north = north,
      south = south
    )
  }

  private fun nextDouble(parser: JsonParser, ctx: DeserializationContext): Double? {
    return when (val token = parser.nextToken()) {
      JsonToken.VALUE_NUMBER_INT -> parser.longValue.toDouble()
      JsonToken.VALUE_NUMBER_FLOAT -> parser.doubleValue
      JsonToken.VALUE_STRING -> parser.valueAsDouble
      JsonToken.END_ARRAY -> null
      null -> {
        ctx.handleUnexpectedToken(BBox::class.java, token, parser, "Unexpected null token")
        null
      }
      else -> {
        ctx.handleUnexpectedToken(BBox::class.java, token, parser, "Unexpected token ${token.name}")
        null
      }
    }
  }
}

internal class BBoxSerializer : JsonSerializer<BBox>() {

  override fun serialize(value: BBox, gen: JsonGenerator, serializers: SerializerProvider) {
    gen.writeStartArray()
    gen.writeNumber(value.west)
    gen.writeNumber(value.south)
    gen.writeNumber(value.east)
    gen.writeNumber(value.north)
    gen.writeEndArray()
  }

}
