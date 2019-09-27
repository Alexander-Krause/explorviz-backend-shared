package net.explorviz.shared.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class RoleJwtDeserializer extends StdDeserializer<List<String>> {

  public RoleJwtDeserializer() {
    super(List.class);
  }

  @Override
  public List<String> deserialize(final JsonParser p, final DeserializationContext ctxt)
      throws IOException {

    final List<String> roleList = new ArrayList<>();
    final JsonNode node = p.getCodec().readTree(p);

    node.forEach((nodeElem) -> {
      final String role = new String(nodeElem.get("descriptor").asText());
      roleList.add(role);
    });

    return roleList;
  }

}
