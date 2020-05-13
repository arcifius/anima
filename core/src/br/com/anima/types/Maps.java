package br.com.anima.types;

public enum Maps {
    MAPA("mapa"),
    DEBAIN("debain");

    private final String map;

    Maps(String map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return map;
    }
}
