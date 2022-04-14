package cf.yellowstrawberry.ystweber.api.WebAPI.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
public @interface RequestPostObject {
    String name();
}
