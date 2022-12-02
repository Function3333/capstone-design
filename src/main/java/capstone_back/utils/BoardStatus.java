package capstone_back.utils;

public enum BoardStatus {
    Wait("0"),
    Proceeding("1"),
    Complete("2");

    private String value;

    BoardStatus(String status) {
        this.value = status;
    }

    public String getValue() {
        return this.value;
    }
}