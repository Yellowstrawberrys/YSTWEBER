package cf.yellowstrawberry.ystweber.api.WebAPI.Annotations;

import cf.yellowstrawberry.ystweber.api.Enums.METHOD;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface RequestPath {
    String path();
    METHOD[] method() default {METHOD.GET};
}