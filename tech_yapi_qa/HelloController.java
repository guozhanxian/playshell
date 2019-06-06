import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "hello相关接口", description = "hello")
public class HelloController {
  @ApiOperation(value = "test接口", notes = "test接口", response = TestResp.class)
  @RequestMapping(value = "/test", method = RequestMethod.POST)
  public TestResp test(@RequestBody TestParam testParam) {
    return new TestResp();
  }
}
