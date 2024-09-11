package dev.ipsych0.myrinnia.utils;

import java.io.Serializable;

@FunctionalInterface
public interface Action extends Serializable {
    void onComplete();
}
