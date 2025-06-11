package ai.bytefabrik.streampipes.mcp.tool;

import org.apache.streampipes.client.api.IStreamPipesClient;
import org.apache.streampipes.model.connect.adapter.AdapterDescription;

import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class AdapterTool {

  private final IStreamPipesClient client;

  public AdapterTool(IStreamPipesClient client) {
    this.client = client;
  }

  @Tool(
      name = "get_all_adapters",
      description = "Get all available StreamPipes adapters"
  )
  public List<AdapterDescription> getAllAdapters() {
    return client.adapters().all();
  }

  @Tool(
      name = "get_adapter_by_id",
      description = "Given the elementId of an adapter, return a detailed description of the adapter"
  )
  public AdapterDescription getAdapterById(String elementId) {
    return client.adapters()
        .get(elementId)
        .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find adapter with id %s", elementId)));
  }

  @Tool(
      name = "start_adapter",
      description = "Start the adapter with the given elementId"
  )
  public void startAdapter(String id) {
    var adapter = getAdapterById(id);
    client.adapters().start(adapter);
  }

  @Tool(
      name = "stop_adapter",
      description = "Stop the adapter with the given elementId"
  )
  public void stopAdapter(String id) {
    var adapter = getAdapterById(id);
    client.adapters().stop(adapter);
  }
}
