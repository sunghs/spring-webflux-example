package sunghs.springwebfluxexample.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Dto {

    private long seq;

    private String name;
}
