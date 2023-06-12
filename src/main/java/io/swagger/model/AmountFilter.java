package io.swagger.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class AmountFilter {
    private Double lower;
    private Double higher;
    private Double equal;

    public boolean allNull(){
        return lower == null && higher == null && equal == null;
    }

}
