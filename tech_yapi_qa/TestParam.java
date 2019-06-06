import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "test", description = "test参数")
public class TestParam {

  @ApiModelProperty(value = "名字", required = true, dataType = "string")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
