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
    if (!p.isExpectedStartArrayToken) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize bbox: no array found")
    }

    val west = p.nextDoubleOrNull()
    if (west == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize bbox: west not found")
      error("West is null")
    }

    val south = p.nextDoubleOrNull()
    if (south == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize bbox: south not found")
      error("south is null")
    }

    val east = p.nextDoubleOrNull()
    if (east == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize bbox: east not found")
      error("east is null")
    }

    val north = p.nextDoubleOrNull()
    if (north == null) {
      ctx.handleUnexpectedToken(valueType, p.currentToken(), p, "Unable to deserialize bbox: north not found")
      error("north is null")
    }

    // Go to the end of the array ignoring anything after the north coordinate
    while (p.currentToken() != JsonToken.END_ARRAY) {
      p.nextToken()
    }

    return BBox(
      west = west,
      east = east,
      north = north,
      south = south
    )
  }
}

internal class BBoxSerializer : StdSerializer<BBox>(BBox::class.java) {

  override fun serialize(value: BBox, gen: JsonGenerator, ctxt: SerializationContext) {
    gen.writeStartArray()
    gen.writeNumber(value.west)
    gen.writeNumber(value.south)
    gen.writeNumber(value.east)
    gen.writeNumber(value.north)
    gen.writeEndArray()
  }

}
