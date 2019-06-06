import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "test", description = "test响应")
public class TestResp {
  @ApiModelProperty(value = "欢迎语", dataType = "string")
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
