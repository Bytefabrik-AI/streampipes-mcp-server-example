package ai.bytefabrik.streampipes.mcp;

import ai.bytefabrik.streampipes.mcp.tool.AdapterTool;
import ai.bytefabrik.streampipes.mcp.tool.PipelineTool;

import org.apache.streampipes.client.StreamPipesClient;
import org.apache.streampipes.client.StreamPipesCredentials;
import org.apache.streampipes.client.api.IStreamPipesClient;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class StreamPipesMcpExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamPipesMcpExampleApplication.class, args);
  }

  @Bean
  public List<ToolCallback> toolCallbacks() {
    var client = makeClient();

    return List.of(ToolCallbacks.from(
        new PipelineTool(client),
        new AdapterTool(client)
        )
    );
  }

  private IStreamPipesClient makeClient() {
    var host = System.getenv("STREAMPIPES_HOST");
    var port = Integer.parseInt(System.getenv("STREAMPIPES_PORT"));
    var username = System.getenv("STREAMPIPES_USERNAME");
    var apiKey = System.getenv("STREAMPIPES_API_KEY");
    var httpsDisabled = Boolean.parseBoolean(
        Optional.ofNullable(System.getenv("STREAMPIPES_HTTPS_DISABLED")).orElse("false")
    );

    return StreamPipesClient.create(host, port,
        StreamPipesCredentials.withApiKey(username, apiKey),
        httpsDisabled);
  }
}
