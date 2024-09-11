package dev.ipsych0.myrinnia.shops;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Stock implements Serializable {

    private static final long serialVersionUID = 6349466499289524304L;
    private int id;
    private int amount;

}
