package ie.ianduffy.carcloud.web.munic.dto;

public abstract class FieldDTOValue<T> {

    private T value;

    FieldDTOValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
