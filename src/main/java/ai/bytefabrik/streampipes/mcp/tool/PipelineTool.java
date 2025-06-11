package ai.bytefabrik.streampipes.mcp.tool;

import org.apache.streampipes.client.api.IStreamPipesClient;
import org.apache.streampipes.model.pipeline.Pipeline;

import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class PipelineTool {

  private final IStreamPipesClient client;

  public PipelineTool(IStreamPipesClient client) {
    this.client = client;
  }

  @Tool(
      name = "get_all_pipelines",
      description = "Get all available StreamPipes pipelines"
  )
  public List<Pipeline> getPipelines() {
    return client.pipelines().all();
  }

  @Tool(
      name = "get_pipeline_by_id",
      description = "Given the ID of a pipeline, return a detailed description of the pipeline"
  )
  public Pipeline getPipelineById(String id) {
    return client.pipelines()
        .get(id)
        .orElseThrow(() -> new IllegalArgumentException(String.format("Could not find pipeline with id %s", id)));
  }

  @Tool(
      name = "start_pipeline",
      description = "Start the pipeline with the given ID"
  )
  public void startPipeline(String id) {
    var pipeline = getPipelineById(id);
    client.pipelines().start(pipeline);
  }

  @Tool(
      name = "stop_pipeline",
      description = "Stop the pipeline with the given ID"
  )
  public void stopPipeline(String id) {
    var pipeline = getPipelineById(id);
    client.pipelines().stop(pipeline);
  }
}
