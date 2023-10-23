package it.finanze.entrate.coopint.dboard.dpi.common.enumeration;

import lombok.Getter;

@Getter
public enum Context {

    DPI("DPI");

    private final String description;

    Context(String description) {
        this.description = description;
    }
}
