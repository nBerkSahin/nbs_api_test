package models;

public enum PetStatus {
    available,
    pending,
    sold;

    @Override
    public String toString() {
        return name();
    }
}
